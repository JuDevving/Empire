package eu.judevving.empire.inventory.menu;

import eu.judevving.empire.earth.Earth;
import eu.judevving.empire.earth.Human;
import eu.judevving.empire.earth.State;
import eu.toastlawine.empire.earth.*;
import eu.judevving.empire.earth.setting.AccessLevel;
import eu.judevving.empire.earth.relation.Relation;
import eu.judevving.empire.earth.setting.Toggle;
import eu.judevving.empire.earth.storage.StateLevel;
import eu.judevving.empire.language.Language;
import eu.judevving.empire.language.Languages;
import eu.judevving.empire.language.Text;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.quest.Quest;
import eu.judevving.empire.quest.QuestCategory;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemMenu {

    private Inventory inventory;
    private final MenuItems menuItems;
    private Runnable[] tasks;
    private Human h;
    private final int listSize = MenuFinals.SIZE - 9;
    private boolean languageMenu;
    private boolean sendWelcomeAfterLanguageSelection = false;

    private void open(Human h, Text name) {
        this.h = h;
        inventory = Bukkit.createInventory(null, MenuFinals.SIZE, Component.text(name.get(h)));
        h.getPlayer().openInventory(inventory);
    }

    public void openMainMenu(Human h) {
        languageMenu = false;
        open(h, Text.MENU_INVENTORY_NAME_MAIN);
        setMainMenu();
    }

    public void openLanguageMenu(Human h, boolean sendWelcomeAfterSelection) {
        open(h, Text.MENU_INVENTORY_NAME_LANGUAGES);
        setLanguageMenu(false);
        languageMenu = true;
        this.sendWelcomeAfterLanguageSelection = sendWelcomeAfterSelection;
    }

    public void openLanguageMenu(Human h) {
        openLanguageMenu(h, false);
    }

    public void click(int slot) {
        if (h == null) return;
        if (!languageMenu) {
            if (h.getState() == null) return;
        }
        if (tasks == null) return;
        Runnable task = tasks[slot];
        if (task == null) return;
        task.run();
        h.playSound(MenuFinals.SOUND_CLICK, MenuFinals.SOUND_VOLUME, MenuFinals.SOUND_PITCH);
    }

    private void setMainMenu() {
        clearInventory();
        setItem(MenuFinals.SLOT_DAILY_REWARD, menuItems.getDailyReward());
        setItem(MenuFinals.SLOT_LEVELING, menuItems.getLeveling());
        setItem(MenuFinals.SLOT_MORE_INFO, menuItems.getMoreInfo());
        setItem(MenuFinals.SLOT_PLAYERS, menuItems.getPlayers());
        setItem(MenuFinals.SLOT_QUESTS, menuItems.getQuests(h.getState()));
        setItem(MenuFinals.SLOT_SELECT_LANGUAGE, menuItems.getSelectLanguage());
        setItem(MenuFinals.SLOT_SETTINGS, menuItems.getSettings());
        setItem(MenuFinals.SLOT_STATE, menuItems.getStateItem(h.getState().getStateId()));
        setItem(MenuFinals.SLOT_STATES, menuItems.getStates());

        if (!h.dailyRewardTaken()) {
            tasks[MenuFinals.SLOT_DAILY_REWARD] = () -> {
                h.takeDailyReward();
                setItem(MenuFinals.SLOT_DAILY_REWARD, menuItems.getDailyReward());
                setItem(MenuFinals.SLOT_STATE, menuItems.getStateItem(h.getState().getStateId()));
                tasks[MenuFinals.SLOT_DAILY_REWARD] = null;
            };
        }
        tasks[MenuFinals.SLOT_LEVELING] = () -> setLevelingMenu(0);
        tasks[MenuFinals.SLOT_PLAYERS] = () -> setPlayersMenu(0);
        tasks[MenuFinals.SLOT_QUESTS] = this::setQuestsMenu;
        tasks[MenuFinals.SLOT_SELECT_LANGUAGE] = () -> setLanguageMenu(true);
        tasks[MenuFinals.SLOT_SETTINGS] = this::setSettingsMenu;
        tasks[MenuFinals.SLOT_STATE] = () -> setStateMenu(h.getState().getStateId());
        tasks[MenuFinals.SLOT_STATES] = () -> setStatesMenu(0);
    }

    private void setSettingsMenu() {
        clearInventory();
        addHomeButton();
        setItem(MenuFinals.SLOT_SETTINGS_LEVEL_NONE, menuItems.getAccessLevelNone());
        setItem(MenuFinals.SLOT_SETTINGS_LEVEL_NONE + 1, menuItems.getAccessLevelInteract());
        setItem(MenuFinals.SLOT_SETTINGS_LEVEL_NONE + 2, menuItems.getAccessLevelUse());
        setItem(MenuFinals.SLOT_SETTINGS_LEVEL_NONE + 3, menuItems.getAccessLevelLoot());
        setItem(MenuFinals.SLOT_SETTINGS_LEVEL_NONE + 4, menuItems.getAccessLevelBuild());
        setItem(MenuFinals.SLOT_SETTINGS_RELATION_ALLY, menuItems.getSettingsAllies());
        setItem(MenuFinals.SLOT_SETTINGS_RELATION_ENEMY, menuItems.getSettingsEnemies());
        setItem(MenuFinals.SLOT_SETTINGS_RELATION_NEUTRAL, menuItems.getSettingsNeutrals());
        fillSettingsButtons(MenuFinals.SLOT_SETTINGS_RELATION_ALLY + 1, h.getState().getSettingsManager().getAccessLevelAllies(), Relation.ALLY);
        fillSettingsButtons(MenuFinals.SLOT_SETTINGS_RELATION_ENEMY + 1, h.getState().getSettingsManager().getAccessLevelEnemies(), Relation.ENEMY);
        fillSettingsButtons(MenuFinals.SLOT_SETTINGS_RELATION_NEUTRAL + 1, h.getState().getSettingsManager().getAccessLevelNeutrals(), Relation.NEUTRAL);
        setItem(MenuFinals.SLOT_PAGE_NEXT, menuItems.getPageNext());
        tasks[MenuFinals.SLOT_PAGE_NEXT] = this::setTogglesMenu;
    }

    private void setTogglesMenu() {
        clearInventory();
        addHomeButton();
        for (Toggle toggle : Toggle.values()) {
            if (toggle.getMinLevel() > h.getState().getLevel()) {
                setItem(toggle.getSlot(), menuItems.getLockedToggle());
                setItem(toggle.getSlot() + 9, menuItems.getLockedToggle());
                continue;
            }
            setItem(toggle.getSlot(), menuItems.getToggle(toggle));
            setItem(toggle.getSlot() + 9, menuItems.getOnOffButton(h.getState().getSettingsManager().getToggle(toggle)));
            tasks[toggle.getSlot() + 9] = () -> {
                h.getState().getSettingsManager().toggle(toggle);
                setItem(toggle.getSlot() + 9, menuItems.getOnOffButton(h.getState().getSettingsManager().getToggle(toggle)));
            };
        }
        setItem(MenuFinals.SLOT_PAGE_PREVIOUS, menuItems.getPagePrevious());
        tasks[MenuFinals.SLOT_PAGE_PREVIOUS] = this::setSettingsMenu;
    }

    private void fillSettingsButtons(int slot, AccessLevel accessLevel, Relation relation) {
        for (int i = 0; i <= AccessLevel.BUILD.getLevel(); i++) {
            if (i <= accessLevel.getLevel()) {
                setItem(slot + i, menuItems.getOnButton());
                if (accessLevel.getLevel() == i) {
                    tasks[slot + i] = null;
                    continue;
                }
            } else setItem(slot + i, menuItems.getOffButton());
            int finalI = i;
            tasks[slot + i] = () -> {
                h.getState().getSettingsManager().setAccessLevel(relation, AccessLevel.fromLevel(finalI));
                fillSettingsButtons(slot, AccessLevel.fromLevel(finalI), relation);
            };
        }
    }

    private void fillStateAccessLevelButtons(AccessLevel accessLevel, String stateId) {
        boolean own = h.getState().getStateId().equals(stateId);
        if (own) accessLevel = AccessLevel.BUILD;
        int slot = MenuFinals.SLOT_STATE_ACCESS_LEVEL + 9;
        setItem(slot, menuItems.getOnOffButton(accessLevel == AccessLevel.DEFAULT));
        if (accessLevel != AccessLevel.DEFAULT && !own) {
            tasks[slot] = () -> {
                h.getState().getSettingsManager().setStateAccessLevel(stateId, AccessLevel.DEFAULT);
                fillStateAccessLevelButtons(AccessLevel.DEFAULT, stateId);
            };
        }
        for (int i = 0; i <= AccessLevel.BUILD.getLevel(); i++) {
            if (i <= accessLevel.getLevel()) {
                setItem(slot + i + 2, menuItems.getOnButton());
                if (accessLevel.getLevel() == i) {
                    tasks[slot + i + 2] = null;
                    continue;
                }
            } else setItem(slot + i + 2, menuItems.getOffButton());
            int finalI = i;
            if (!own) {
                tasks[slot + i + 2] = () -> {
                    h.getState().getSettingsManager().setStateAccessLevel(stateId, AccessLevel.fromLevel(finalI));
                    fillStateAccessLevelButtons(AccessLevel.fromLevel(finalI), stateId);
                };
            }
        }
    }

    private void setStateMenu(String stateId) {
        clearInventory();
        addHomeButton();
        setItem(MenuFinals.SLOT_EXTRA_LEFT, menuItems.getBackToStateList());
        tasks[MenuFinals.SLOT_EXTRA_LEFT] = () -> setStatesMenu(0);
        State state = Main.getPlugin().getEarth().getState(stateId);
        if (state == null) return;
        setItem(MenuFinals.SLOT_STATE_STATE, menuItems.getStateItem(stateId));
        setItem(MenuFinals.SLOT_STATE_LIST_ALLIES, menuItems.getListAllies(state));
        setItem(MenuFinals.SLOT_STATE_LIST_ENEMIES, menuItems.getListEnemies(state));
        setItem(MenuFinals.SLOT_STATE_LIST_MEMBERS, menuItems.getListMembers(state));
        tasks[MenuFinals.SLOT_STATE_LIST_ALLIES] = () -> setAllyMenu(stateId, 0);
        tasks[MenuFinals.SLOT_STATE_LIST_ENEMIES] = () -> setEnemyMenu(stateId, 0);
        tasks[MenuFinals.SLOT_STATE_LIST_MEMBERS] = () -> setMemberMenu(stateId, 0);
        if (!state.equals(h.getState())) {
            if (h.getState().getRelationManager().isEnemy(state)) {
                setItem(MenuFinals.SLOT_STATE_RELATION_NEUTRAL, menuItems.getNeutralDeclare());
                tasks[MenuFinals.SLOT_STATE_RELATION_NEUTRAL] = () -> {
                    h.getState().getRelationManager().declareNeutral(state);
                    h.closeInventory();
                };
            } else {
                setItem(MenuFinals.SLOT_STATE_RELATION_ENEMY, menuItems.getEnemyDeclare());
                tasks[MenuFinals.SLOT_STATE_RELATION_ENEMY] = () -> {
                    h.getState().getRelationManager().declareEnemy(state);
                    h.closeInventory();
                };
            }
            if (h.getState().getRelationManager().isAlly(state)) {
                setItem(MenuFinals.SLOT_STATE_RELATION_NEUTRAL, menuItems.getAllianceBreak());
                tasks[MenuFinals.SLOT_STATE_RELATION_NEUTRAL] = () -> {
                    h.getState().getRelationManager().breakAlliance(state);
                    h.closeInventory();
                };
            } else {
                if (state.getRelationManager().hasOfferedAllianceTo(h.getState())) {
                    setItem(MenuFinals.SLOT_STATE_RELATION_NEUTRAL, menuItems.getAllianceReject());
                    tasks[MenuFinals.SLOT_STATE_RELATION_NEUTRAL] = () -> {
                        h.getState().getRelationManager().rejectAlliance(state);
                        h.closeInventory();
                    };
                    setItem(MenuFinals.SLOT_STATE_RELATION_ALLY, menuItems.getAllianceAccept());
                    tasks[MenuFinals.SLOT_STATE_RELATION_ALLY] = () -> {
                        h.getState().getRelationManager().acceptAlliance(state);
                        h.closeInventory();
                    };
                } else {
                    if (!state.getRelationManager().isEnemy(h.getState())) {
                        if (!h.getState().getRelationManager().hasOfferedAllianceTo(state)) {
                            setItem(MenuFinals.SLOT_STATE_RELATION_ALLY, menuItems.getAllianceOffer());
                            tasks[MenuFinals.SLOT_STATE_RELATION_ALLY] = () -> {
                                h.getState().getRelationManager().offerAlliance(state);
                                h.closeInventory();
                            };
                        }
                    }
                }
            }
        }
        if (tasks[MenuFinals.SLOT_STATE_RELATION_ALLY] == null)
            setItem(MenuFinals.SLOT_STATE_RELATION_ALLY, menuItems.getNoActionAlly());
        if (tasks[MenuFinals.SLOT_STATE_RELATION_ENEMY] == null)
            setItem(MenuFinals.SLOT_STATE_RELATION_ENEMY, menuItems.getNoActionEnemy());
        if (tasks[MenuFinals.SLOT_STATE_RELATION_NEUTRAL] == null)
            setItem(MenuFinals.SLOT_STATE_RELATION_NEUTRAL, menuItems.getNoActionNeutral());

        setItem(MenuFinals.SLOT_STATE_ACCESS_LEVEL, menuItems.getAccessLevelDefault());
        setItem(MenuFinals.SLOT_STATE_ACCESS_LEVEL + 2, menuItems.getAccessLevelNone());
        setItem(MenuFinals.SLOT_STATE_ACCESS_LEVEL + 3, menuItems.getAccessLevelInteract());
        setItem(MenuFinals.SLOT_STATE_ACCESS_LEVEL + 4, menuItems.getAccessLevelUse());
        setItem(MenuFinals.SLOT_STATE_ACCESS_LEVEL + 5, menuItems.getAccessLevelLoot());
        setItem(MenuFinals.SLOT_STATE_ACCESS_LEVEL + 6, menuItems.getAccessLevelBuild());
        fillStateAccessLevelButtons(h.getState().getSettingsManager().getStateAccessLevel(stateId), stateId);
    }

    private void setAllyMenu(String stateId, int from) {
        clearInventory();
        State state = Main.getPlugin().getEarth().getState(stateId);
        if (state == null) return;
        setListBottomButtons(state);
        setItem(MenuFinals.SLOT_EXTRA_RIGHT, menuItems.getListAlliesInfo(state));
        for (int i = 0; i + from < state.getRelationManager().getAllyCount() && i < listSize; i++) {
            String ally = state.getRelationManager().getAlly(i + from);
            setItem(i, menuItems.getStateItem(ally));
            tasks[i] = () -> setStateMenu(ally);
        }
        if (from + listSize < state.getRelationManager().getAllyCount()) {
            setItem(MenuFinals.SLOT_PAGE_NEXT, menuItems.getPageNext());
            tasks[MenuFinals.SLOT_PAGE_NEXT] = () -> setAllyMenu(stateId, from + listSize);
        }
        if (from > 0) {
            setItem(MenuFinals.SLOT_PAGE_PREVIOUS, menuItems.getPagePrevious());
            tasks[MenuFinals.SLOT_PAGE_PREVIOUS] = () -> setAllyMenu(stateId, from - listSize);
        }
    }

    private void setEnemyMenu(String stateId, int from) {
        clearInventory();
        State state = Main.getPlugin().getEarth().getState(stateId);
        if (state == null) return;
        setListBottomButtons(state);
        setItem(MenuFinals.SLOT_EXTRA_RIGHT, menuItems.getListEnemiesInfo(state));
        for (int i = 0; i + from < state.getRelationManager().getEnemyCount() && i < listSize; i++) {
            String enemy = state.getRelationManager().getEnemy(i + from);
            setItem(i, menuItems.getStateItem(enemy));
            tasks[i] = () -> setStateMenu(enemy);
        }
        if (from + listSize < state.getRelationManager().getEnemyCount()) {
            setItem(MenuFinals.SLOT_PAGE_NEXT, menuItems.getPageNext());
            tasks[MenuFinals.SLOT_PAGE_NEXT] = () -> setEnemyMenu(stateId, from + listSize);
        }
        if (from > 0) {
            setItem(MenuFinals.SLOT_PAGE_PREVIOUS, menuItems.getPagePrevious());
            tasks[MenuFinals.SLOT_PAGE_PREVIOUS] = () -> setEnemyMenu(stateId, from - listSize);
        }
    }

    private void setMemberMenu(String stateId, int from) {
        clearInventory();
        State state = Main.getPlugin().getEarth().getState(stateId);
        if (state == null) return;
        setListBottomButtons(state);
        setItem(MenuFinals.SLOT_EXTRA_RIGHT, menuItems.getListMembersInfo(state));
        for (int i = 0; i + from < state.getMemberCount() && i < listSize; i++) {
            Human member = state.getMember(i + from);
            setItem(i, menuItems.getPlayerItem(member.getUniqueId()));
        }
        if (from + listSize < state.getMemberCount()) {
            setItem(MenuFinals.SLOT_PAGE_NEXT, menuItems.getPageNext());
            tasks[MenuFinals.SLOT_PAGE_NEXT] = () -> setMemberMenu(stateId, from + listSize);
        }
        if (from > 0) {
            setItem(MenuFinals.SLOT_PAGE_PREVIOUS, menuItems.getPagePrevious());
            tasks[MenuFinals.SLOT_PAGE_PREVIOUS] = () -> setMemberMenu(stateId, from - listSize);
        }
    }

    private void setListBottomButtons(State state) {
        addHomeButton();
        setItem(MenuFinals.SLOT_EXTRA_LEFT, menuItems.getBackToState(state));
        tasks[MenuFinals.SLOT_EXTRA_LEFT] = () -> setStateMenu(state.getStateId());
    }

    private void setStatesMenu(int from) {
        clearInventory();
        addHomeButton();
        Earth earth = Main.getPlugin().getEarth();
        for (int i = 0; i + from < earth.getStateCount() && i < listSize; i++) {
            State state = earth.getStateDescending(i + from);
            setItem(i, menuItems.getStateItem(state.getStateId()));
            tasks[i] = () -> setStateMenu(state.getStateId());
        }
        if (from + listSize < earth.getStateCount()) {
            setItem(MenuFinals.SLOT_PAGE_NEXT, menuItems.getPageNext());
            tasks[MenuFinals.SLOT_PAGE_NEXT] = () -> setStatesMenu(from + listSize);
        }
        if (from > 0) {
            setItem(MenuFinals.SLOT_PAGE_PREVIOUS, menuItems.getPagePrevious());
            tasks[MenuFinals.SLOT_PAGE_PREVIOUS] = () -> setStatesMenu(from - listSize);
        }
    }

    private void setPlayersMenu(int from) {
        clearInventory();
        addHomeButton();
        Earth earth = Main.getPlugin().getEarth();
        for (int i = 0; i + from < earth.getHumanCount() && i < listSize; i++) {
            Human human = earth.getHuman(i + from);
            setItem(i, menuItems.getPlayerItem(human.getUniqueId()));
            if (human.getState() == null) continue;
            tasks[i] = () -> setStateMenu(human.getState().getStateId());
        }
        if (from + listSize < earth.getHumanCount()) {
            setItem(MenuFinals.SLOT_PAGE_NEXT, menuItems.getPageNext());
            tasks[MenuFinals.SLOT_PAGE_NEXT] = () -> setPlayersMenu(from + listSize);
        }
        if (from > 0) {
            setItem(MenuFinals.SLOT_PAGE_PREVIOUS, menuItems.getPagePrevious());
            tasks[MenuFinals.SLOT_PAGE_PREVIOUS] = () -> setPlayersMenu(from - listSize);
        }
    }

    private void setQuestsMenu() {
        clearInventory();
        addHomeButton();
        boolean skipButton = false;
        for (int i = 0; i < Quest.MAX_QUESTS && i < listSize; i++) {
            Quest quest = h.getState().getQuestManager().getQuest(i);
            if (i < h.getState().getQuests()) {
                setItem(MenuFinals.QUEST_BUFFER + i, menuItems.getQuest(quest));
            } else setItem(MenuFinals.QUEST_BUFFER + i, menuItems.getQuestLocked());
            if (quest == null) continue;
            if (quest.isActive()) skipButton = true;
        }
        setQuestTasks(false);
        if (skipButton) {
            setItem(MenuFinals.SLOT_EXTRA_RIGHT, menuItems.getQuestSkipButton());
            tasks[MenuFinals.SLOT_EXTRA_RIGHT] = () -> {
                setQuestTasks(true);
                setItem(MenuFinals.SLOT_EXTRA_RIGHT, menuItems.getQuestSkipInfo());
            };
        }
    }

    private void setQuestTasks(boolean skip) {
        for (int i = 0; i < Quest.MAX_QUESTS && i < listSize && i < h.getState().getQuests(); i++) {
            tasks[MenuFinals.QUEST_BUFFER + i] = null;
            Quest quest = h.getState().getQuestManager().getQuest(i);
            if (quest == null) continue;
            if (quest.isInactive()) continue;
            int finalI = i;
            if (skip) {
                if (h.getState().getStateLevel().getQuests() <= i) continue;
                tasks[MenuFinals.QUEST_BUFFER + i] = () -> {
                    quest.skip();
                    setQuestsMenu();
                };
            } else {
                if (quest.getType().getCategory() != QuestCategory.PAY) continue;
                tasks[MenuFinals.QUEST_BUFFER + i] = () -> {
                    Material material = (Material) quest.getType().getSubject();
                    int in = h.purchasePart(material, quest.getStillNeeded());
                    if (in <= 0) return;
                    quest.progress(QuestCategory.PAY, material, in);
                    setItem(MenuFinals.QUEST_BUFFER + finalI, menuItems.getQuest(quest));
                };
            }
        }
    }

    private void setLevelingMenu(int from) {
        clearInventory();
        addHomeButton();
        for (int i = 0; i + from < StateLevel.getMax() && i < listSize; i++) {
            StateLevel stateLevel = StateLevel.values()[i + from];
            if (h.getState().getLevel() >= stateLevel.getLevel()) {
                setItem(i, menuItems.getLevel(stateLevel, stateLevel.getPriceAmount(), false));
                continue;
            }
            if (h.getState().getLevel() + 1 < stateLevel.getLevel()) {
                setItem(i, menuItems.getUnknownLevel(stateLevel));
                continue;
            }
            setItem(i, menuItems.getLevel(stateLevel, h.getState().getLevelProgress(), true));
            if (stateLevel.isAdvanced()) {
                int finalI = i;
                tasks[i] = () -> {
                    if (h.getState().getLevel() != stateLevel.getLevel() - 1) return;
                    int progress = stateLevel.getItems().tryPay(h);
                    h.getState().setLevelProgress(progress);
                    setItem(finalI, menuItems.getLevel(stateLevel, progress, true));
                    if (progress < h.getState().getStateLevel().getNext().getPriceAmount()) return;
                    h.getState().levelUp();
                    setLevelingMenu(from);
                };
            } else {
                tasks[i] = () -> {
                    if (h.getState().getLevel() != stateLevel.getLevel() - 1) return;
                    int in = h.purchasePart(stateLevel.getPriceMaterial(),
                            stateLevel.getPriceAmount() - h.getState().getLevelProgress());
                    h.getState().progressLevel(in);
                    if (in <= 0) return;
                    setLevelingMenu(from);
                };
            }
        }
        if (from + listSize < StateLevel.getMax()) {
            setItem(MenuFinals.SLOT_PAGE_NEXT, menuItems.getPageNext());
            tasks[MenuFinals.SLOT_PAGE_NEXT] = () -> setLevelingMenu(from + listSize);
        }
        if (from > 0) {
            setItem(MenuFinals.SLOT_PAGE_PREVIOUS, menuItems.getPagePrevious());
            tasks[MenuFinals.SLOT_PAGE_PREVIOUS] = () -> setLevelingMenu(from - listSize);
        }
    }

    private void setLanguageMenu(boolean homeButton) {
        clearInventory();
        if (homeButton) addHomeButton();
        Languages languages = Main.getPlugin().getLanguages();
        for (int i = 0; i < languages.getLength(); i++) {
            Language language = languages.get(i);
            setItem(i, menuItems.getLanguageItem(language.getName()));
            tasks[i] = () -> {
                h.setLanguage(language);
                h.sendMessage(Text.COMMAND_LANGUAGE_SET.get(h, language.getTranslation(Text.LANGUAGE_NAME_NATIVE).get()));
                if (homeButton) {
                    setMainMenu();
                } else h.closeInventory();
                if (sendWelcomeAfterLanguageSelection) {
                    h.sendMessage(Text.INFO_FIRST_JOIN_HEADER.get(h));
                    h.sendMessage(Text.INFO_FIRST_JOIN_MESSAGE.get(h));
                    sendWelcomeAfterLanguageSelection = false;
                }
            };
        }
    }

    private void addHomeButton() {
        setItem(MenuFinals.SLOT_HOME, menuItems.getHome());
        tasks[MenuFinals.SLOT_HOME] = this::setMainMenu;
    }

    private void setItem(int slot, ItemStack itemStack) {
        if (slot < 0) return;
        if (slot >= inventory.getSize()) return;
        inventory.setItem(slot, itemStack);
    }

    public ItemMenu(Human h) {
        menuItems = new MenuItems(h);
        this.languageMenu = false;
    }

    private void clearInventory() {
        for (int i = 0; i < inventory.getSize(); i++) setItem(i, null);
        tasks = new Runnable[MenuFinals.SIZE];
    }

    public static int toI(int x, int y) {
        return y * 9 + x;
    }

}
