package eu.judevving.empire.earth.storage;

public enum WelcomeMessage {

    CHAT("chat"),
    OFF("off"),
    TITLE("title");

    private String name;

    WelcomeMessage(String name) {
        this.name = name;
    }

    public static WelcomeMessage fromNameIgnoreCase(String name) {
        for (WelcomeMessage welcomeMessage : values()) {
            if (welcomeMessage.name.equalsIgnoreCase(name)) return welcomeMessage;
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
