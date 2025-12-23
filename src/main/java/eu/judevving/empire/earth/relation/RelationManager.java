package eu.judevving.empire.earth.relation;

import eu.judevving.empire.earth.State;
import eu.judevving.empire.language.Text;
import eu.judevving.empire.main.GlobalFinals;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.map.MapFinals;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RelationManager {

    private State state;

    private HashSet<String> allies;
    private HashSet<String> enemies;
    private HashSet<String> offeredAlliances;
    private ArrayList<String> allyList;
    private ArrayList<String> enemyList;

    public void validate() {
        for (String ally : getAllies()) {
            if (Main.getPlugin().getEarth().isState(ally)) continue;
            removeAlly(ally);
        }
        for (String enemy : getEnemies()) {
            if (Main.getPlugin().getEarth().isState(enemy)) continue;
            removeEnemy(enemy);
        }
    }

    public boolean hasOfferedAllianceTo(State state1) {
        if (state1 == null) return false;
        return offeredAlliances.contains(state1.getStateId());
    }

    public void addOfferedAllianceTo(String stateId) {
        offeredAlliances.add(stateId);
    }

    public void removeOfferedAllianceTo(String stateId) {
        offeredAlliances.remove(stateId);
    }

    public boolean isAlly(@Nullable State state1) {
        if (state1 == null) return false;
        return allies.contains(state1.getStateId());
    }

    public boolean isEnemy(@Nullable State state1) {
        if (state1 == null) return false;
        return enemies.contains(state1.getStateId());
    }

    public List<String> getAllies() {
        return allies.stream().toList();
    }

    public List<String> getEnemies() {
        return enemies.stream().toList();
    }

    public void addAlly(String stateId) {
        enemies.remove(stateId);
        if (allies.add(stateId)) allyList.add(stateId);
    }

    public void removeAlly(String stateId) {
        allies.remove(stateId);
        allyList.remove(stateId);
    }

    public void setNeutral(String stateId) {
        removeAlly(stateId);
        removeEnemy(stateId);
        removeOfferedAllianceTo(stateId);
    }

    public void addEnemy(String stateId) {
        removeAlly(stateId);
        if (enemies.add(stateId)) enemyList.add(stateId);
    }

    public void removeEnemy(String stateId) {
        enemies.remove(stateId);
        enemyList.remove(stateId);
    }

    public Relation getRelation(@Nullable State to) {
        if (to == null) return Relation.NEUTRAL;
        if (to.equals(state)) return Relation.NONE;
        if (isEnemy(to)) {
            if (to.getRelationManager().isEnemy(state)) return Relation.ENEMY_BOTH;
            return Relation.ENEMY;
        }
        if (to.getRelationManager().isEnemy(state)) return Relation.ENEMY_SELF;
        if (isAlly(to)) return Relation.ALLY;
        return Relation.NEUTRAL;
    }

    public void breakAlliance(State state1) {
        if (state1 == null) return;
        removeAlly(state1.getStateId());
        state1.getRelationManager().removeAlly(state.getStateId());
        Main.getPlugin().getEarth().sendMessage(Text.RELATION_ALLIANCE_BROKEN_BROADCAST, state.getColoredName(), state1.getColoredName());
    }

    public void acceptAlliance(State state1) {
        if (state1 == null) return;
        if (!state1.getRelationManager().hasOfferedAllianceTo(state)) return;
        state1.getRelationManager().removeOfferedAllianceTo(state.getStateId());
        addAlly(state1.getStateId());
        state1.getRelationManager().addAlly(state.getStateId());
        Main.getPlugin().getEarth().sendMessage(Text.RELATION_ALLIANCE_FORMED_BROADCAST, state.getColoredName(), state1.getColoredName());
    }

    public void rejectAlliance(State state1) {
        if (state1 == null) return;
        if (!state1.getRelationManager().hasOfferedAllianceTo(state)) return;
        state1.getRelationManager().removeOfferedAllianceTo(state.getStateId());
        state.sendMessage(Text.RELATION_ALLIANCE_OFFER_REJECTION_SENT, state1.getColoredName());
        state1.sendMessage(Text.RELATION_ALLIANCE_OFFER_REJECTION_GOTTEN, state.getColoredName());
    }

    public void offerAlliance(State state1) {
        if (state1 == null) return;
        addOfferedAllianceTo(state1.getStateId());
        state.sendMessage(Text.RELATION_ALLIANCE_OFFER_SENT, state1.getColoredName());
        state1.sendMessage(Text.RELATION_ALLIANCE_OFFER_GOTTEN, state.getColoredName());
    }

    public void declareNeutral(State state1) {
        if (state1 == null) return;
        setNeutral(state1.getStateId());
        Main.getPlugin().getEarth().sendMessage(Text.RELATION_NEUTRAL_DECLARED_BROADCAST, state.getColoredName(), state1.getColoredName());
    }

    public void declareEnemy(State state1) {
        if (state1 == null) return;
        if (isAlly(state1))
            Main.getPlugin().getEarth().sendMessage(Text.RELATION_ALLIANCE_BROKEN_BROADCAST, state.getColoredName(), state1.getColoredName());
        removeAlly(state1.getStateId());
        removeOfferedAllianceTo(state1.getStateId());
        state1.getRelationManager().removeOfferedAllianceTo(state.getStateId());
        addEnemy(state1.getStateId());
        Main.getPlugin().getEarth().sendMessage(Text.RELATION_ENEMY_DECLARED_BROADCAST, state.getColoredName(), state1.getColoredName());
    }

    public RelationManager(State state) {
        this.state = state;
        this.allies = new HashSet<>();
        this.enemies = new HashSet<>();
        this.allyList = new ArrayList<>();
        this.enemyList = new ArrayList<>();
        this.offeredAlliances = new HashSet<>();
    }

    public String getMapList(ArrayList<String> list, int maxLength) {
        StringBuilder stringBuilder = new StringBuilder();
        int length = list.size();
        if (length > maxLength)
            length = maxLength;
        if (length < 1) return GlobalFinals.STRING_BLANK;
        stringBuilder.append(list.getFirst());
        for (int i = 1; i < length; i++) {
            stringBuilder.append(", ").append(list.get(i));
        }
        if (list.size() > maxLength)
            stringBuilder.append(", ").append(maxLength - list.size()).append(" more");
        return stringBuilder.toString();
    }

    public String getMapAllyList() {
        return getMapList(allyList, MapFinals.ALLY_LIST_MAX_LENGTH);
    }

    public String getMapEnemyList() {
        return getMapList(enemyList, MapFinals.ENEMY_LIST_MAX_LENGTH);
    }

    public int getAllyCount() {
        return allies.size();
    }

    public int getEnemyCount() {
        return enemies.size();
    }

    public String getAlly(int i) {
        if (i < 0 || i >= allyList.size()) return null;
        String ally = allyList.get(i);
        if (Main.getPlugin().getEarth().getState(ally) == null) {
            allyList.remove(i);
            return getAlly(i);
        }
        return ally;
    }

    public String getEnemy(int i) {
        if (i < 0 || i >= enemyList.size()) return null;
        String enemy = enemyList.get(i);
        if (Main.getPlugin().getEarth().getState(enemy) == null) {
            enemyList.remove(i);
            return getEnemy(i);
        }
        return enemy;
    }

}
