package eu.judevving.empire.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import org.bukkit.entity.Player;

import java.util.Collections;

public class PlayerProtocol {

    public void setPlayerListHidden(Player p, boolean hidden) {
        PacketContainer packet;
        if (hidden) {
            packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO_REMOVE);
            packet.getUUIDLists().write(0, Collections.singletonList(p.getUniqueId()));
        } else {
            packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
            packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
            PlayerInfoData data = new PlayerInfoData(WrappedGameProfile.fromPlayer(p), p.getPing(), EnumWrappers.NativeGameMode.SURVIVAL, WrappedChatComponent.fromText(p.playerListName().toString()));
            packet.getPlayerInfoDataLists().write(0, Collections.singletonList(data));
        }
        ProtocolLibrary.getProtocolManager().sendServerPacket(p, packet);
    }

}
