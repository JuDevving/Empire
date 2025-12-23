package eu.judevving.empire.quest;

import eu.judevving.empire.earth.State;
import eu.judevving.empire.language.Language;
import eu.judevving.empire.language.Text;
import eu.judevving.empire.inventory.ItemEditor;
import eu.judevving.empire.main.GlobalFinals;
import eu.judevving.empire.earth.storage.StateLevel;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Quest {

    public static final int MAX_QUESTS = StateLevel.MAX_LEGITIMATE.getQuests();

    private int progress;
    private final QuestType type;
    private final State state;

    public Quest(State state, QuestType type) {
        this(state, type, 0);
    }

    private Quest(State state, QuestType type, int progress) {
        this.state = state;
        this.type = type;
        this.progress = progress;
    }

    private void completed() {
        state.getQuestManager().addCompletedQuest();
        state.sendQuestCompletedMessage(this);
        state.addPower(getType().getPower());
    }

    public int progress(QuestCategory category, Object subject, int amount) {
        if (isInactive()) return amount;
        if (!type.progresses(category, subject)) return amount;
        progress += amount;
        if (isCompleted()) {
            amount = progress - type.getGoal();
            progress = type.getGoal();
            completed();
            return amount;
        }
        return 0;
    }

    public boolean isInactive() {
        return !isActive();
    }

    public boolean isActive() {
        return progress >= 0 && !isCompleted();
    }

    public boolean isSkipped() {
        return progress < 0;
    }

    public void skip() {
        if (isCompleted()) return;
        progress = -1;
    }

    public boolean isCompleted() {
        return progress >= type.getGoal();
    }

    public ItemStack getItem(Language language) {
        if (state == null) return type.getItem();
        ItemStack itemStack = ItemEditor.setNameAndLore(
                isCompleted() ? new ItemStack(Material.LIME_STAINED_GLASS_PANE) : type.getItem(),
                type.getTask(language), Text.MENU_LABEL_PROGRESS.get(language) + progress + GlobalFinals.STRING_OF + type.getGoal(),
                Text.MENU_LABEL_POWER.get(language) + getType().getPower());
        if (isCompleted()) ItemEditor.makeShiny(itemStack);
        return itemStack;
    }

    public static Quest fromString(State state, String string) {
        if (string == null) return null;
        int o = string.indexOf('{');
        int c = string.indexOf('}');
        if (o == -1 || c == -1 || o > c) return null;
        try {
            return new Quest(state, QuestType.valueOf(string.substring(0, o)),
                    Integer.parseInt(string.substring(o + 1, c)));
        } catch (Exception e) {
            return null;
        }
    }

    public int getStillNeeded() {
        if (getProgress() > getType().getGoal()) return 0;
        return getType().getGoal() - getProgress();
    }

    @Override
    public String toString() {
        return type.name() + '{' + progress + '}';
    }

    public int getProgress() {
        return progress;
    }

    public QuestType getType() {
        return type;
    }
}
