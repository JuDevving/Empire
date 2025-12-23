package eu.judevving.empire.inventory.menu;

import eu.judevving.empire.earth.Human;
import eu.judevving.empire.earth.setting.Toggle;
import eu.judevving.empire.inventory.CustomHead;
import eu.judevving.empire.inventory.ItemEditor;
import eu.judevving.empire.quest.Quest;
import eu.judevving.empire.earth.State;
import eu.judevving.empire.language.Language;
import eu.judevving.empire.language.Text;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.main.GlobalFinals;
import eu.judevving.empire.earth.storage.StateLevel;
import eu.judevving.empire.quest.QuestCategory;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class MenuItems {

    private Human h;

    public ItemStack getStateItem(String stateId) {
        State state = Main.getPlugin().getEarth().getState(stateId);
        if (state == null) return null;
        ItemStack itemStack = state.getFlag();
        ItemEditor.setNameAndLore(itemStack, state.getColoredName(),
                Text.MENU_LABEL_DESCRIPTION.get(h) + state.getDescription(),
                Text.MENU_LABEL_RELATION.get(h) + h.getState().getRelationManager().getRelation(state).getName().get(h),
                Text.MENU_LABEL_ID.get(h) + state.getStateId(),
                Text.MENU_LABEL_TAG.get(h) + state.getColoredTag(),
                Text.MENU_LABEL_RANK.get(h) + state.getRank(),
                Text.MENU_LABEL_POWER.get(h) + state.getPower(),
                Text.MENU_LABEL_SIZE.get(h) + state.getTerritorySize() + GlobalFinals.STRING_OF + state.getMaxTerritorySize(),
                Text.MENU_LABEL_LEVEL.get(h) + state.getLevel() + (state.getStateLevel() == StateLevel.MAX_LEGITIMATE ? GlobalFinals.STRING_HINT_MAX : ""),
                Text.MENU_LABEL_ONLINE.get(h) + state.getOnlineMemberCount() + GlobalFinals.STRING_OF + state.getMemberCount(),
                Text.MENU_LABEL_CAPITAL.get(h) + (state.getCapital() == null ?
                        Text.MENU_LABEL_CAPITAL_NONE.get(h) : state.getCapital().getCenterBlockString()));
        return itemStack;
    }

    public ItemStack getPlayerItem(UUID uuid) {
        Human human = Main.getPlugin().getEarth().getHuman(uuid);
        ItemStack itemStack = Main.getPlugin().getPlayerHeads().getHead(human.getUniqueId());
        if (human.isOnline()) {
            ItemEditor.setNameAndLore(itemStack, human.getNameColor() + human.getName(),
                    Text.MENU_LABEL_STATE.get(h) + human.getColoredStateNameAndId(),
                    Text.MENU_LABEL_STATUS.get(h) + Text.MENU_LABEL_STATUS_ONLINE.get(h),
                    Text.MENU_LABEL_DEATHS.get(h) + human.getDeaths());
        } else {
            ItemEditor.setNameAndLore(itemStack, human.getNameColor() + human.getName(),
                    Text.MENU_LABEL_STATE.get(h) + human.getColoredStateNameAndId(),
                    Text.MENU_LABEL_STATUS.get(h) + Text.MENU_LABEL_STATUS_OFFLINE.get(h),
                    Text.MENU_LABEL_LAST_ONLINE.get(h) + human.getLastOnlineString(),
                    Text.MENU_LABEL_DEATHS.get(h) + human.getDeaths());
        }
        return itemStack;
    }

    public ItemStack getLanguageItem(String name) {
        Language language = Main.getPlugin().getLanguages().fromString(name);
        if (language == null) return null;
        CustomHead customHead;
        switch (language.getName()) {
            case "brainrot" -> customHead = CustomHead.MEME_SKIBIDI_TOILET;
            case "english" -> customHead = CustomHead.FLAG_USA;
            case "french" -> customHead = CustomHead.FLAG_FRANCE;
            case "german" -> customHead = CustomHead.FLAG_GERMANY;
            case "japanese" -> customHead = CustomHead.FLAG_JAPAN;
            default -> customHead = CustomHead.SPEECH_BUBBLE;
        }
        ItemStack itemStack = customHead.get();
        ItemEditor.setName(itemStack, Text.MENU_NAME_SELECT_LANGUAGE_LANGUAGE_PREFIX.get(h)
                + language.getTranslation(Text.LANGUAGE_NAME_NATIVE).get());
        return itemStack;
    }

    public ItemStack getSelectLanguage() {
        return ItemEditor.setName(CustomHead.SPEECH_BUBBLE.get(), Text.MENU_NAME_SELECT_LANGUAGE.get(h));
    }

    public MenuItems(Human h) {
        this.h = h;
    }

    public ItemStack getDailyReward() {
        if (h.dailyRewardTaken())
            return ItemEditor.setNameAndLore(new ItemStack(Material.GOLD_NUGGET),
                    Text.MENU_NAME_DAILY_REWARD_TAKEN.get(h),
                    Text.MENU_LABEL_DAILY_REWARD_STREAK.get(h) + h.getDailyRewardStreak(),
                    Text.MENU_LABEL_DAILY_REWARD_TAKEN_HEADER.get(h),
                    Text.MENU_LABEL_DAILY_REWARD_POWER.get(h) + h.getNextDailyRewardPower(),
                    Text.MENU_LABEL_DAILY_REWARD_FLAGS.get(h) + h.getNextDailyRewardFlags());
        return ItemEditor.setNameAndLore(new ItemStack(Material.GOLD_INGOT),
                Text.MENU_NAME_DAILY_REWARD.get(h),
                Text.MENU_LABEL_DAILY_REWARD_POWER.get(h) + h.getNextDailyRewardPower(),
                Text.MENU_LABEL_DAILY_REWARD_FLAGS.get(h) + h.getNextDailyRewardFlags(),
                Text.MENU_LABEL_DAILY_REWARD_STREAK.get(h) + h.getDailyRewardStreak());
    }

    public ItemStack getHome() {
        return ItemEditor.setName(CustomHead.MENU_HOME.get(), Text.MENU_NAME_HOME.get(h));
    }

    public ItemStack getLeveling() {
        return ItemEditor.setName(new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Text.MENU_NAME_LEVELING.get(h));
    }

    public ItemStack getMoreInfo() {
        return ItemEditor.setNameAndLore(new ItemStack(Material.BAMBOO_SIGN),
                Text.MENU_NAME_MORE_INFO.get(h),
                Text.MENU_LABEL_AVERAGE_SQUARE_COST.get(h) + GlobalFinals.getAverageSquareCost(h.getState().getTerritorySize()),
                Text.MENU_LABEL_WELCOME.get(h) + h.getState().getWelcome(),
                Text.MENU_LABEL_ENEMY_WELCOME.get(h) + h.getState().getEnemyWelcome(),
                Text.MENU_LABEL_QUEST_COMPLETED_COUNT.get(h) + h.getState().getQuestManager().getCompletedQuests());
    }

    public ItemStack getPlayers() {
        return ItemEditor.setName(new ItemStack(Material.PLAYER_HEAD), Text.MENU_NAME_PLAYERS.get(h));
    }

    public ItemStack getQuests(State state) {
        if (state.getQuestManager().allInactive())
            return ItemEditor.setName(new ItemStack(Material.MAP), Text.MENU_NAME_QUESTS.get(h));
        return ItemEditor.setName(new ItemStack(Material.FILLED_MAP), Text.MENU_NAME_QUESTS.get(h));
    }

    public ItemStack getQuest(Quest quest) {
        if (quest == null) return null;
        ItemStack itemStack;
        if (quest.isCompleted()) {
            itemStack = ItemEditor.setNameAndLore(
                    new ItemStack(Material.LIME_STAINED_GLASS_PANE),
                    quest.getType().getTask(h.getLanguage()), Text.MENU_LABEL_PROGRESS.get(h) + quest.getProgress() + GlobalFinals.STRING_OF + quest.getType().getGoal(),
                    Text.MENU_LABEL_POWER.get(h) + quest.getType().getPower(),
                    Text.MENU_FOOTER_QUEST_NEW_TOMORROW.get(h));
            ItemEditor.makeShiny(itemStack);
        } else if (quest.isSkipped()) {
            itemStack = ItemEditor.setNameAndLore(new ItemStack(Material.WHITE_STAINED_GLASS_PANE),
                    Text.MENU_NAME_QUEST_SKIPPED.get(h),
                    Text.MENU_FOOTER_QUEST_NEW_TOMORROW.get(h));
        } else {
            if (quest.getType().getCategory() == QuestCategory.PAY) {
                itemStack = ItemEditor.setNameAndLore(
                        quest.getType().getItem(),
                        quest.getType().getTask(h.getLanguage()), Text.MENU_LABEL_PROGRESS.get(h) + quest.getProgress() + GlobalFinals.STRING_OF + quest.getType().getGoal(),
                        Text.MENU_LABEL_POWER.get(h) + quest.getType().getPower(),
                        Text.MENU_FOOTER_CLICK_TO_PAY.get(h));
            } else {
                itemStack = ItemEditor.setNameAndLore(
                        quest.getType().getItem(),
                        quest.getType().getTask(h.getLanguage()), Text.MENU_LABEL_PROGRESS.get(h) + quest.getProgress() + GlobalFinals.STRING_OF + quest.getType().getGoal(),
                        Text.MENU_LABEL_POWER.get(h) + quest.getType().getPower());
            }
        }
        return itemStack;
    }

    public ItemStack getQuestSkipButton() {
        return ItemEditor.setNameAndLore(CustomHead.MENU_X.get(), Text.MENU_NAME_QUEST_SKIP_BUTTON.get(h),
                Text.MENU_FOOTER_QUEST_NEW_TOMORROW.get(h));
    }

    public ItemStack getQuestSkipInfo() {
        return ItemEditor.setNameAndLore(CustomHead.MENU_INFO.get(), Text.MENU_NAME_QUEST_SKIP_INFO.get(h),
                Text.MENU_FOOTER_QUEST_NEW_TOMORROW.get(h));
    }

    public ItemStack getQuestLocked() {
        return ItemEditor.setName(new ItemStack(Material.BARRIER), Text.MENU_NAME_QUEST_LOCKED.get(h));
    }

    public ItemStack getSettings() {
        return ItemEditor.setName(new ItemStack(Material.COMPARATOR), Text.MENU_NAME_SETTINGS.get(h));
    }

    public ItemStack getSettingsAllies() {
        return ItemEditor.setName(new ItemStack(Material.LIME_BANNER), Text.MENU_NAME_SETTINGS_RELATION_ALLY.get(h));
    }

    public ItemStack getSettingsNeutrals() {
        return ItemEditor.setName(new ItemStack(Material.WHITE_BANNER), Text.MENU_NAME_SETTINGS_RELATION_NEUTRAL.get(h));
    }

    public ItemStack getSettingsEnemies() {
        return ItemEditor.setName(new ItemStack(Material.RED_BANNER), Text.MENU_NAME_SETTINGS_RELATION_ENEMY.get(h));
    }

    public ItemStack getAccessLevelDefault() {
        return ItemEditor.setNameAndLore(new ItemStack(Material.COMMAND_BLOCK), Text.MENU_NAME_SETTINGS_LEVEL_DEFAULT.get(h),
                Text.MENU_NAME_SETTINGS_LEVEL_DEFAULT_DESCRIPTION.get(h));
    }

    public ItemStack getAccessLevelNone() {
        return ItemEditor.setNameAndLore(new ItemStack(Material.BARRIER), Text.MENU_NAME_SETTINGS_LEVEL_NONE.get(h),
                Text.MENU_NAME_SETTINGS_LEVEL_NONE_DESCRIPTION.get(h));
    }

    public ItemStack getAccessLevelInteract() {
        return ItemEditor.setNameAndLore(new ItemStack(Material.SPRUCE_DOOR), Text.MENU_NAME_SETTINGS_LEVEL_INTERACT.get(h),
                Text.MENU_NAME_SETTINGS_LEVEL_INTERACT_DESCRIPTION.get(h));
    }

    public ItemStack getAccessLevelUse() {
        return ItemEditor.setNameAndLore(new ItemStack(Material.ANVIL), Text.MENU_NAME_SETTINGS_LEVEL_USE.get(h),
                Text.MENU_NAME_SETTINGS_LEVEL_USE_DESCRIPTION.get(h));
    }

    public ItemStack getAccessLevelLoot() {
        return ItemEditor.setNameAndLore(new ItemStack(Material.CHEST), Text.MENU_NAME_SETTINGS_LEVEL_LOOT.get(h),
                Text.MENU_NAME_SETTINGS_LEVEL_LOOT_DESCRIPTION.get(h));
    }

    public ItemStack getAccessLevelBuild() {
        return ItemEditor.setNameAndLore(new ItemStack(Material.IRON_PICKAXE), Text.MENU_NAME_SETTINGS_LEVEL_BUILD.get(h),
                Text.MENU_NAME_SETTINGS_LEVEL_BUILD_DESCRIPTION.get(h));
    }

    public ItemStack getToggle(Toggle toggle) {
        return ItemEditor.setName(new ItemStack(toggle.getMaterial()), toggle.getItemName().get(h));
    }

    public ItemStack getOnOffButton(boolean on) {
        return on ? getOnButton() : getOffButton();
    }

    public ItemStack getOffButton() {
        return ItemEditor.setName(new ItemStack(Material.GRAY_DYE), Text.MENU_NAME_OFF.get(h));
    }

    public ItemStack getOnButton() {
        return ItemEditor.setName(new ItemStack(Material.LIME_DYE), Text.MENU_NAME_ON.get(h));
    }

    public ItemStack getBackToState(State state) {
        return ItemEditor.setName(CustomHead.MENU_BACK.get(), Text.MENU_NAME_BACK_TO.get(h, state.getColoredName()));
    }

    public ItemStack getBackToStateList() {
        return ItemEditor.setName(CustomHead.MENU_BACK.get(), Text.MENU_NAME_BACK_TO_STATE_LIST.get(h));
    }

    public ItemStack getPageNext() {
        return ItemEditor.setName(CustomHead.MENU_ARROW_RIGHT.get(), Text.MENU_NAME_PAGE_NEXT.get(h));
    }

    public ItemStack getPagePrevious() {
        return ItemEditor.setName(CustomHead.MENU_ARROW_LEFT.get(), Text.MENU_NAME_PAGE_PREVIOUS.get(h));
    }

    public ItemStack getStates() {
        return ItemEditor.setName(CustomHead.EARTH.get(), Text.MENU_NAME_STATES.get(h));
    }

    public ItemStack getListAllies(State state) {
        return ItemEditor.setName(new ItemStack(Material.LIME_BANNER),
                Text.MENU_NAME_LIST_ALLIES.get(h, state.getRelationManager().getAllyCount() + ""));
    }

    public ItemStack getListEnemies(State state) {
        return ItemEditor.setName(new ItemStack(Material.RED_BANNER),
                Text.MENU_NAME_LIST_ENEMIES.get(h, state.getRelationManager().getEnemyCount() + ""));
    }

    public ItemStack getListMembers(State state) {
        return ItemEditor.setName(new ItemStack(Material.PLAYER_HEAD),
                Text.MENU_NAME_LIST_MEMBERS.get(h, state.getMemberCount() + ""));
    }

    public ItemStack getListAlliesInfo(State state) {
        return ItemEditor.setName(CustomHead.MENU_INFO.get(),
                Text.MENU_NAME_LIST_ALLIES_INFO.get(h, state.getColoredName()));
    }

    public ItemStack getListEnemiesInfo(State state) {
        return ItemEditor.setName(CustomHead.MENU_INFO.get(),
                Text.MENU_NAME_LIST_ENEMIES_INFO.get(h, state.getColoredName()));
    }

    public ItemStack getListMembersInfo(State state) {
        return ItemEditor.setName(CustomHead.MENU_INFO.get(),
                Text.MENU_NAME_LIST_MEMBERS_INFO.get(h, state.getColoredName()));
    }


    public ItemStack getLevel(StateLevel stateLevel, int progress, boolean next) {
        boolean purchasable = progress < stateLevel.getPriceAmount();
        Text name = purchasable ? Text.MENU_NAME_LEVELING_LEVEL_NEXT : Text.MENU_NAME_LEVELING_LEVEL;
        ItemStack itemStack;
        List<Component> lore = stateLevel.getChange(h.getLanguage());
        lore.addFirst(Component.text(Text.MENU_LABEL_PROGRESS.get(h) + progress + GlobalFinals.STRING_OF + stateLevel.getPriceAmount()));
        if (stateLevel.isAdvanced()) {
            lore.addLast(Component.text(stateLevel.getItems().getPaymentDescription().get(h)));
            if (next) lore.addLast(Component.text(Text.MENU_FOOTER_ONLY_PAID_IF_COMPLETE.get(h)));
        }
        if (next) lore.addLast(Component.text(Text.MENU_FOOTER_CLICK_TO_PAY.get(h)));
        itemStack = ItemEditor.setNameAndLore(stateLevel.getItem(), name.get(h, stateLevel.getLevel() + ""), lore);
        if (!purchasable) ItemEditor.makeShiny(itemStack);
        return itemStack;
    }

    public ItemStack getUnknownLevel(StateLevel stateLevel) {
        return ItemEditor.setNameAndLore(new ItemStack(Material.BARRIER), Text.MENU_NAME_LEVELING_LEVEL.get(h,
                stateLevel.getLevel() + ""), (stateLevel.getBonus() == null ?
                Text.MENU_LABEL_LEVELING_UNKNOWN_DESCRIPTION : Text.MENU_LABEL_LEVELING_UNKNOWN_DESCRIPTION_SPECIAL).get(h));
    }

    public ItemStack getLockedToggle() {
        return ItemEditor.setName(new ItemStack(Material.BARRIER), Text.MENU_NAME_SETTINGS_TOGGLE_LOCKED.get(h));
    }

    public ItemStack getAllianceOffer() {
        return ItemEditor.setName(new ItemStack(Material.LIME_BANNER), Text.MENU_NAME_RELATION_ALLIANCE_OFFER.get(h));
    }

    public ItemStack getAllianceBreak() {
        return ItemEditor.setName(new ItemStack(Material.WHITE_BANNER), Text.MENU_NAME_RELATION_ALLIANCE_BREAK.get(h));
    }

    public ItemStack getAllianceAccept() {
        return ItemEditor.setName(new ItemStack(Material.LIME_BANNER), Text.MENU_NAME_RELATION_ALLIANCE_ACCEPT.get(h));
    }

    public ItemStack getAllianceReject() {
        return ItemEditor.setName(new ItemStack(Material.WHITE_BANNER), Text.MENU_NAME_RELATION_ALLIANCE_REJECT.get(h));
    }

    public ItemStack getEnemyDeclare() {
        return ItemEditor.setName(new ItemStack(Material.RED_BANNER), Text.MENU_NAME_RELATION_ENEMY_DECLARE.get(h));
    }

    public ItemStack getNeutralDeclare() {
        return ItemEditor.setName(new ItemStack(Material.WHITE_BANNER), Text.MENU_NAME_RELATION_NEUTRAL_DECLARE.get(h));
    }

    public ItemStack getNoActionAlly() {
        return ItemEditor.setName(new ItemStack(Material.LIME_STAINED_GLASS_PANE), GlobalFinals.STRING_BLANK);
    }

    public ItemStack getNoActionNeutral() {
        return ItemEditor.setName(new ItemStack(Material.WHITE_STAINED_GLASS_PANE), GlobalFinals.STRING_BLANK);
    }

    public ItemStack getNoActionEnemy() {
        return ItemEditor.setName(new ItemStack(Material.RED_STAINED_GLASS_PANE), GlobalFinals.STRING_BLANK);
    }
}
