package eu.judevving.empire.quest;

import eu.judevving.empire.language.Text;

public enum QuestCategory {

    KILL(Text.QUEST_CATEGORY_KILL),
    MINE(Text.QUEST_CATEGORY_MINE),
    PAY(Text.QUEST_CATEGORY_PAY),
    VISIT(Text.QUEST_CATEGORY_VISIT);

    private final Text task;

    QuestCategory(Text task) {
        this.task = task;
    }

    public Text getTask() {
        return task;
    }
}
