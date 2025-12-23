package eu.judevving.empire.quest;

import eu.judevving.empire.earth.State;
import eu.judevving.empire.language.Language;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;

public class QuestManager {

    private Quest[] quests;
    private State state;
    private int completedQuests;

    public void progress(QuestCategory category, Object subject) {
        progress(category, subject, 1);
    }

    public void progress(QuestCategory category, Object subject, int amount) {
        for (int i = 0; i < quests.length && i < state.getQuests(); i++) {
            if (quests[i] == null) continue;
            if (quests[i].isInactive()) continue;
            amount = quests[i].progress(category, subject, amount);
            if (amount <= 0) return;
        }
    }

    public QuestManager(State state) {
        this.state = state;
        quests = new Quest[Quest.MAX_QUESTS];
    }

    public void fillFromStringList(List<String> list) {
        if (list != null) {
            for (int i = 0; i < list.size() && i < quests.length; i++) {
                quests[i] = Quest.fromString(state, list.get(i));
            }
        }
        replaceNull();
    }

    public LinkedList<String> toStringList() {
        LinkedList<String> list = new LinkedList<>();
        for (int i = 0; i < quests.length; i++) {
            if (quests[i] == null) continue;
            list.addLast(quests[i].toString());
        }
        return list;
    }

    public void replaceInactive() {
        for (int i = 0; i < quests.length; i++) {
            if (quests[i] == null) continue;
            if (quests[i].isActive()) continue;
            newRandom(i);
        }
    }

    public void replaceNull() {
        for (int i = 0; i < quests.length; i++) {
            if (quests[i] != null) continue;
            newRandom(i);
        }
    }

    public boolean allInactive() {
        for (int i = 0; i < state.getQuests(); i++) {
            if (quests[i] == null) continue;
            if (quests[i].isActive()) return false;
        }
        return true;
    }

    public ItemStack getItem(Language language, int i) {
        if (quests[i] == null) return null;
        return quests[i].getItem(language);
    }

    public void allNewRandom() {
        for (int i = 0; i < state.getQuests(); i++) {
            newRandom(i);
        }
    }

    public void newRandom(int i) {
        quests[i] = new Quest(state, QuestType.random());
    }

    public Quest getQuest(int i) {
        return quests[i];
    }

    public int getSize() {
        return quests.length;
    }

    public int getCompletedQuests() {
        return completedQuests;
    }

    public void setCompletedQuests(int completedQuests) {
        this.completedQuests = completedQuests;
    }

    void addCompletedQuest() {
        this.completedQuests++;
    }
}
