package eu.judevving.empire.earth.relation;

import eu.judevving.empire.language.Text;

public enum Relation {

    ALLY(Text.MENU_LABEL_RELATION_ALLY),
    ENEMY(Text.MENU_LABEL_RELATION_ENEMY),
    ENEMY_SELF(Text.MENU_LABEL_RELATION_ENEMY_SELF),
    ENEMY_BOTH(Text.MENU_LABEL_RELATION_ENEMY_BOTH),
    NEUTRAL(Text.MENU_LABEL_RELATION_NEUTRAL),
    NONE(Text.MENU_LABEL_RELATION_NONE);

    private final Text name;

    Relation(Text name) {
        this.name = name;
    }

    public Text getName() {
        return name;
    }
}
