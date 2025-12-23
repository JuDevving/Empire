package eu.judevving.empire.listener;

import eu.judevving.empire.earth.Human;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.main.GlobalFinals;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AsyncChatListener implements Listener, ChatRenderer {

    @EventHandler
    public void onChat(AsyncChatEvent e) {
        e.renderer(this);
        Human h = Main.getPlugin().getEarth().getHuman(e.getPlayer());
        Component newMessage = h.getState() == null ? net.kyori.adventure.text.Component.text("") : h.getState().getChatPrefix();
        newMessage = newMessage.append(Component.text(h.getNameColor() + e.getPlayer().getName() + GlobalFinals.CHAT_NAME_SUFFIX));
        e.message(newMessage.append(e.message()));
    }

    @Override
    public Component render(Player p, Component sourceDisplayName, Component message, Audience audience) {
        return message;
    }
}
