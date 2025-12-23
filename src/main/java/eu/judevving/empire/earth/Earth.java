package eu.judevving.empire.earth;

import eu.judevving.empire.earth.railway.RailwayManager;
import eu.judevving.empire.earth.storage.ClaimResult;
import eu.judevving.empire.file.*;
import eu.toastlawine.empire.file.*;
import eu.judevving.empire.language.Text;
import eu.judevving.empire.main.GlobalFinals;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.quest.QuestCategory;
import eu.judevving.empire.clock.Day;
import eu.judevving.empire.sidefeature.crate.CrateManager;
import eu.judevving.empire.sidefeature.custompoi.CustomPOIManager;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.*;

public class Earth {

    private HashMap<UUID, Human> humans;
    private ArrayList<Human> humansSorted;
    private HashMap<String, State> states;
    private ArrayList<State> statesSorted;
    private TreeMap<Square, String> squares;
    private CustomPOIManager customPOIManager;
    private RailwayManager railwayManager;
    private CrateManager crateManager;
    private ClaimTimeManager claimTimeManager;
    private World world, nether;
    private Day lastOnlineDay;
    private MiniatureEarth miniatureEarth;
    private EarthSaver earthSaver;

    public Earth() {
        this.lastOnlineDay = Day.ZERO;
        statesSorted = new ArrayList<>();
        humansSorted = new ArrayList<>();
        miniatureEarth = new MiniatureEarth();
    }

    public void init() {
        loadStates();
        loadSquares();
        loadEarth();
        loadHumans();
        validateStates();
        updateMaxPlayers();
    }

    public void postLoadInit() {
        initWorld();
        putAllHumans();
        for (Human h : humans.values()) {
            h.initTeam();
        }
    }

    private void initWorld() {
        miniatureEarth.generate(world);
        world.getWorldBorder().setCenter((GlobalFinals.EARTH_BLOCK_MIN_X + GlobalFinals.EARTH_BLOCK_MAX_X) >> 1,
                (GlobalFinals.EARTH_BLOCK_MIN_Z + GlobalFinals.EARTH_BLOCK_MAX_Z) >> 1);
        int size = GlobalFinals.EARTH_SIZE_X > GlobalFinals.EARTH_SIZE_Z ? GlobalFinals.EARTH_SIZE_X : GlobalFinals.EARTH_SIZE_Z;
        world.getWorldBorder().setSize(size + 2 * GlobalFinals.EARTH_TELEPORT_WIDTH);

        crateManager = new CrateManager();
        crateManager.loadCrates(earthSaver.loadCrateLocations(world));
    }

    public void save() {
        EarthSaver earthSaver = new EarthSaver();
        earthSaver.save(crateManager.getCrates(), customPOIManager.getCustomPOIs(), railwayManager);
        SquareSaver squareSaver = new SquareSaver();
        squareSaver.save(squares);
        claimTimeManager.save();
        StateSaver stateSaver = new StateSaver();
        stateSaver.saveStates(states.values());
        HumanSaver humanSaver = new HumanSaver();
        humanSaver.saveHumans(humans.values());
        FlagSaver flagSaver = new FlagSaver();
        flagSaver.save(states.values());
    }

    private void loadEarth() {
        earthSaver = new EarthSaver();
        earthSaver.load();
        customPOIManager = new CustomPOIManager();
        customPOIManager.setCustomPOIs(earthSaver.loadCustomPOIs());
        railwayManager = new RailwayManager();
        earthSaver.addRailways(railwayManager);
    }

    private void loadSquares() {
        squares = new TreeMap<>();
        claimTimeManager = new ClaimTimeManager();
        SquareSaver squareSaver = new SquareSaver();
        squareSaver.load();
        claimTimeManager.load();
    }

    private void loadStates() {
        StateSaver stateSaver = new StateSaver();
        states = stateSaver.loadStates();
        FlagSaver flagSaver = new FlagSaver();
        flagSaver.load();
        for (State state : states.values()) {
            state.setFlag(flagSaver.getFlag(state.getStateId()));
        }
    }

    public void updateMaxPlayers() {
        if (Bukkit.getOnlinePlayers().size() >= humans.size()) {
            Bukkit.getServer().setMaxPlayers(Bukkit.getOnlinePlayers().size() + 1);
            return;
        }
        Bukkit.getServer().setMaxPlayers(humans.size());
    }

    public void validateStates() {
        for (State state : states.values()) {
            state.validate();
        }
    }

    private void loadHumans() {
        HumanSaver humanSaver = new HumanSaver();
        humans = humanSaver.loadHumans();
    }

    private void sortStates() {
        statesSorted = new ArrayList<>(states.values());
        Collections.sort(statesSorted);
        for (int i = 0; i < statesSorted.size(); i++) {
            statesSorted.get(i).setRank(statesSorted.size() - i);
        }
    }

    private void sortHumans() {
        //if (humans.size() == humansSorted.size()) return;
        humansSorted = new ArrayList<>(humans.values());
        Collections.sort(humansSorted);
    }

    private void newDay() {
        Main.getPlugin().getLogger().info("New day started");
        for (State state : states.values()) {
            state.getQuestManager().replaceInactive();
        }
    }

    public void synchronousTick() {
        if ((Main.getPlugin().getClock().getTick() - GlobalFinals.DELAY_CRATE_SPAWN) % GlobalFinals.PERIOD_CRATE_SPAWN == 0)
            crateManager.spawnCrate(false);
        for (Human h : humans.values()) {
            h.synchronousTick();
        }
    }

    public void asynchronousTick() {
        if (Main.getPlugin().getClock().getTick() % GlobalFinals.PERIOD_STATE_SORT == 0) sortStates();
        if (Main.getPlugin().getClock().getTick() % GlobalFinals.PERIOD_HUMAN_SORT == 0) sortHumans();
        if (Main.getPlugin().getClock().getTick() % GlobalFinals.PERIOD_RECENT_CLAIM_UPDATE == 0)
            claimTimeManager.updateRecent();
        if (Main.getPlugin().getClock().getTick() % GlobalFinals.PERIOD_MARKER_UPDATE == 0) {
            for (State state : states.values()) {
                state.getMarkerManager().removeInvalidMarkers();
                state.setCapitalIcon(null);
            }
        }
    }

    public void progressQuests(Player p, QuestCategory category, Object subject) {
        if (p.getGameMode() == GameMode.CREATIVE) return;
        State state = getState(p);
        if (state == null) return;
        state.getQuestManager().progress(category, subject);
    }

    public boolean isGlobeTeleportationArea(Location location) {
        if (location == null) return false;
        if (!isWorld(location.getWorld())) return false;
        if (location.getX() < GlobalFinals.EARTH_BLOCK_MIN_X) {
            return (location.getX() > GlobalFinals.EARTH_BLOCK_MIN_X - GlobalFinals.EARTH_TELEPORT_WIDTH);
        }
        if (location.getZ() < GlobalFinals.EARTH_BLOCK_MIN_Z) {
            return (location.getZ() > GlobalFinals.EARTH_BLOCK_MIN_Z - GlobalFinals.EARTH_TELEPORT_WIDTH);
        }
        if (location.getX() > GlobalFinals.EARTH_BLOCK_MAX_X) {
            return (location.getX() < GlobalFinals.EARTH_BLOCK_MAX_X + GlobalFinals.EARTH_TELEPORT_WIDTH);
        }
        if (location.getZ() > GlobalFinals.EARTH_BLOCK_MAX_Z) {
            return (location.getZ() < GlobalFinals.EARTH_BLOCK_MAX_Z + GlobalFinals.EARTH_TELEPORT_WIDTH);
        }
        return false;
    }

    public void sendMessage(Text text, String... args) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(text.get(p, args));
        }
    }

    public LinkedList<String> getOnlineStateIds() {
        LinkedList<String> list = new LinkedList<>();
        for (State state : states.values()) {
            if (state.isOnline()) list.add(state.getStateId());
        }
        return list;
    }

    public LinkedList<String> getStateIds(boolean includeUnclaimable) {
        LinkedList<String> list = new LinkedList<>();
        for (State state : states.values()) {
            list.addLast(state.getStateId());
        }
        if (includeUnclaimable) list.addLast(GlobalFinals.EARTH_UNCLAIMABLE_TERRITORY_NAME);
        return list;
    }

    public LinkedList<String> getOnlinePlayerNames(boolean stateless) {
        LinkedList<String> list = new LinkedList<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (stateless) {
                if (getHuman(p).getState() != null) continue;
            }
            list.addLast(p.getName());
        }
        return list;
    }

    public boolean isTakeableByAnyone(State state, Square square) {
        if (state.isCloseCapital(square)) return false;
        return getClaimTimeManager().isRecent(square);
    }

    public ClaimResult tryClaim(State state, Square square, World w) {
        return tryClaim(state, square, w, false);
    }

    public ClaimResult tryClaim(State state, Square square, World w, boolean capital) {
        ClaimResult claimResult = canClaim(state, square, w, capital);
        if (claimResult.isSuccess()) {
            forceClaim(state, square, capital);
            claimTimeManager.set(square, Main.getPlugin().getClock().getTimeInMillis());
        }
        return claimResult;
    }

    public ClaimResult tryUnclaim(State state, Square square, World w) {
        if (!w.getUID().equals(world.getUID())) return ClaimResult.WRONG_WORLD;
        if (state.getCapital().equals(square)) return ClaimResult.CANNOT_UNCLAIM_CAPITAL;
        State owner = getState(square);
        if (owner == null) return ClaimResult.NOT_CLAIMED;
        if (owner != state) {
            if (claimTimeManager.isRecent(square)) {
                if (owner.getCapital() != null) {
                    if (state.getCapital().getBlockDistance(square) >= state.getClaimRadius())
                        return ClaimResult.TOO_FAR;
                }
                int edges = owner.countEdges(square);
                if (edges > GlobalFinals.TAKEOVER_MAX_EDGES) return ClaimResult.TOO_MANY_EDGES;
                if (owner.isCloseCapital(square)) return ClaimResult.TOO_CLOSE;
                if (owner.getCapital().equals(square)) return ClaimResult.ALREADY_CLAIMED;
                forceUnclaim(square);
                return ClaimResult.SUCCESS_UNCLAIM_TAKEOVER;
            } else return ClaimResult.ALREADY_CLAIMED;
        }
        forceUnclaim(square);
        return ClaimResult.SUCCESS_UNCLAIM;
    }

    public void unclaimAll(State state) {
        Iterator<Square> territory = state.getTerritory();
        while (territory.hasNext()) {
            squares.remove(territory.next());
        }
        state.newTerritory();
    }

    public ClaimResult canClaim(State state, Square square, World w, boolean capital) {
        if (!w.getUID().equals(world.getUID())) return ClaimResult.WRONG_WORLD;
        if (square.isOutOfBounds()) return ClaimResult.OUT_OF_BOUNDS;
        if (square.containsPlayerOfDifferentState(state)) return ClaimResult.DIFFERENT_STATE_PLAYER_PRESENT;
        if (isUnclaimable(square)) return ClaimResult.UNCLAIMABLE;
        State owner = getState(square);
        if (state == owner) return ClaimResult.ALREADY_CLAIMED_SELF;
        ClaimResult claimResult = state.canClaim(square, capital);
        if (owner != null) {
            if (claimTimeManager.isRecent(square)) {
                int edges = owner.countEdges(square);
                if (edges > GlobalFinals.TAKEOVER_MAX_EDGES) return ClaimResult.TOO_MANY_EDGES;
                if (owner.isCloseCapital(square)) return ClaimResult.TOO_CLOSE;
                if (owner.getCapital().equals(square)) return ClaimResult.ALREADY_CLAIMED;
                if (claimResult.isSuccess()) return ClaimResult.SUCCESS_TAKEOVER;
            } else return ClaimResult.ALREADY_CLAIMED;
        }
        return claimResult;
    }

    public void forceUnclaim(Square square) {
        if (isUnclaimable(square)) {
            setUnclaimable(square, false);
            return;
        }
        State state = getState(square);
        squares.remove(square);
        claimTimeManager.remove(square);
        if (state == null) return;
        state.forceUnclaim(square);
    }

    private void forceClaim(State state, Square square, boolean capital) {
        forceUnclaim(square);
        squares.put(square, state.getStateId());
        state.forceClaim(square);
        if (capital) state.setCapital(square);
    }

    public boolean forceClaim(Square square, String stateId) {
        forceUnclaim(square);
        if (stateId.equals(GlobalFinals.EARTH_UNCLAIMABLE_TERRITORY_NAME)) {
            setUnclaimable(square, true);
            return true;
        }
        State state = getState(stateId);
        if (state == null) return false;
        squares.put(square, stateId);
        state.forceClaim(square);
        return true;
    }

    public void setUnclaimable(Square square, boolean unclaimable) {
        if (square == null) return;
        if (square.isOutOfBounds()) return;
        if (unclaimable) {
            squares.put(square, GlobalFinals.EARTH_UNCLAIMABLE_TERRITORY_NAME);
        } else {
            if (isUnclaimable(square)) squares.remove(square);
        }
    }

    public boolean isOutOfBounds(Location location) {
        if (location == null) return false;
        if (!isWorld(location.getWorld())) return false;
        if (location.getX() < GlobalFinals.EARTH_BLOCK_MIN_X) return true;
        if (location.getX() > GlobalFinals.EARTH_BLOCK_MAX_X) return true;
        if (location.getZ() < GlobalFinals.EARTH_BLOCK_MIN_Z) return true;
        return location.getZ() > GlobalFinals.EARTH_BLOCK_MAX_Z;
    }

    public State addState(String stateId) {
        State state = State.create(stateId, null);
        states.put(stateId, state);
        return state;
    }

    void removeState(String stateId) {
        states.remove(stateId);
        for (State state : states.values()) {
            state.getRelationManager().removeAlly(stateId);
            state.getRelationManager().removeEnemy(stateId);
            state.getRelationManager().removeOfferedAllianceTo(stateId);
        }
    }

    public Square[] getAdjacentNeighbors(Square square) {
        return new Square[]{square.cloneAdd(1, 0),
                square.cloneAdd(0, 1),
                square.cloneAdd(-1, 0),
                square.cloneAdd(0, -1)};
    }

    public void putAllHumans() {
        Bukkit.getOnlinePlayers().forEach(this::putHuman);
    }

    public void putHuman(Player p) {
        Human h;
        if (humans.containsKey(p.getUniqueId())) {
            h = getHuman(p);
            h.setPlayer(p);
        } else { // first join
            h = Human.create(p);
            humans.put(p.getUniqueId(), h);
            h.teleport(Main.getPlugin().getEarth().getMiniatureEarth().getSpawn());
            h.openLanguageMenu(true);
        }
        if (h.getDeaths() < 0) {
            h.setDeaths(h.getPlayer().getStatistic(Statistic.DEATHS));
        }
        h.setCollides(!miniatureEarth.isInside(p.getLocation()));
    }

    public State getBorderRegionStateIfSole(Location location) {
        Square center = Square.fromLocation(location);
        State re = null;
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                State state = getState(center.cloneAdd(x, z));
                if (state == null) continue;
                if (re == null) re = state;
                if (re == state) continue;
                return null;
            }
        }
        return re;
    }

    public boolean isBorderRegion(Location location) {
        if (!isWorld(location.getWorld())) return false;
        return isBorderRegion(Square.fromLocation(location));
    }

    private boolean isBorderRegion(Square square) {
        State state = getState(square);
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (x == 0 && z == 0) continue;
                if (state != getState(square.cloneAdd(x, z))) return true;
            }
        }
        return false;
    }

    public boolean isUnclaimable(Square square) {
        String stateId = squares.get(square);
        if (stateId == null) return false;
        return stateId.equals(GlobalFinals.EARTH_UNCLAIMABLE_TERRITORY_NAME);
    }

    public int getStateAmount() {
        return states.size();
    }

    public int getMaxRank() {
        return getStateAmount();
    }

    public State getStateDescending(int i) {
        return getState(statesSorted.size() - i - 1);
    }

    public State getState(int i) {
        if (i < 0) return null;
        if (i >= statesSorted.size()) return null;
        return statesSorted.get(i);
    }

    public State getState(Player p) {
        return getHuman(p).getState();
    }

    public State getState(Location location) {
        if (location == null) return null;
        if (!location.getWorld().getUID().equals(world.getUID())) return null;
        return getState(Square.fromLocation(location));
    }

    public State getState(Square square) {
        if (square == null) return null;
        return getState(squares.get(square));
    }

    public State getState(String stateId) {
        return states.get(stateId);
    }

    public boolean isState(String stateId) {
        return states.containsKey(stateId);
    }

    public int getStateCount() {
        return statesSorted.size();
    }

    public boolean isNether(World w) {
        return w.getUID().equals(nether.getUID());
    }

    public boolean isWorld(World w) {
        return w.getUID().equals(world.getUID());
    }

    public Human getHuman(int i) {
        if (i < 0) return null;
        if (i >= getHumanCount()) return null;
        return humansSorted.get(i);
    }

    public int getHumanCount() {
        return humansSorted.size();
    }

    public Collection<State> getStates() {
        return states.values();
    }

    public Collection<Human> getHumans() {
        return humans.values();
    }

    public boolean isNoMansLand(Square square) {
        return !squares.containsKey(square);
    }

    public Human getHuman(String name) {
        return getHuman(Bukkit.getPlayer(name));
    }

    public Human getHuman(Player p) {
        if (p == null) return null;
        return getHuman(p.getUniqueId());
    }

    public Human getHuman(UUID uuid) {
        return humans.get(uuid);
    }

    public World getNether() {
        return nether;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setNether(World nether) {
        this.nether = nether;
    }

    public boolean preservesStreak(Day day) {
        if (day.equals(lastOnlineDay)) return true;
        if (lastOnlineDay.isNext(day)) return true;
        return day.isNext(lastOnlineDay);
    }

    public Day getLastOnlineDay() {
        return lastOnlineDay;
    }

    public void setLastOnlineDay(Day lastOnlineDay, boolean forceNewDay) {
        this.lastOnlineDay = lastOnlineDay;
        if (!forceNewDay) {
            if (lastOnlineDay.equals(Main.getPlugin().getClock().getDay())) return;
        }
        newDay();
    }

    public MiniatureEarth getMiniatureEarth() {
        return miniatureEarth;
    }

    public static void putOnHighestBlock(Location location) {
        location.setY(location.getWorld().getMaxHeight() - 1);
        while (location.getY() > location.getWorld().getMinHeight() && location.getBlock().getType().isAir())
            location.add(0, -1, 0);
        location.add(0, 1, 0);
    }

    public CrateManager getCrateManager() {
        return crateManager;
    }

    public CustomPOIManager getCustomPOIManager() {
        return customPOIManager;
    }

    public RailwayManager getRailwayManager() {
        return railwayManager;
    }

    public ClaimTimeManager getClaimTimeManager() {
        return claimTimeManager;
    }
}
