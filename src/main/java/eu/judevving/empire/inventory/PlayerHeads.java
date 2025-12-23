package eu.judevving.empire.inventory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import eu.judevving.empire.file.FileSaver;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.main.GlobalFinals;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class PlayerHeads {

    private final HashMap<UUID, ItemStack> heads;
    private final HashMap<UUID, String> values;
    private final HashMap<UUID, Long> lastDownloads;
    private final FileSaver fileSaver;

    public void dropAllHeads(Location location) {
        for (UUID uuid : values.keySet()) {
            Item item = (Item) location.getWorld().spawnEntity(location, EntityType.ITEM);
            item.setItemStack(getHead(uuid));
        }
    }

    public ItemStack getHead(Player p) {
        return getHead(p.getUniqueId(), "§e"+p.getName());
    }

    public ItemStack getHead(UUID uuid, String name) {
        ItemStack head = getHead(uuid);
        if (head == null) return null;
        ItemEditor.setName(head, name);
        return head;
    }

    public ItemStack getHead(UUID uuid) {
        if (buildHead(uuid)) return heads.get(uuid).clone();
        return null;
    }

    private boolean buildHead(UUID uuid) {
        return buildHead(uuid, null);
    }

    private boolean buildHead(UUID uuid, String name) {
        if (heads.containsKey(uuid)) return true;
        if (!values.containsKey(uuid)) return false;
        ItemStack head = getHeadFromValue(values.get(uuid), uuid);
        if (head == null) return false;
        ItemEditor.setName(head, name);
        heads.put(uuid, head);
        return true;
    }

    public void refreshHead(UUID uuid) {
        if (lastDownloads.containsKey(uuid)) {
            if (Main.getPlugin().getClock().getTick() - lastDownloads.get(uuid) < GlobalFinals.DELAY_HEAD_DOWNLOAD) return;
            lastDownloads.put(uuid, Main.getPlugin().getClock().getTick());
        }
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), new Runnable() {
            @Override
            public void run() {
                String value = downloadHeadValue(uuid);
                if (value == null) return;
                if (values.get(uuid) != null) {
                    if (values.get(uuid).equals(value)) return;
                }
                values.put(uuid, value);
                heads.remove(uuid);
            }
        });
    }

    public PlayerHeads() {
        this.heads = new HashMap<>();
        this.values = new HashMap<>();
        this.lastDownloads = new HashMap<>();
        fileSaver = new FileSaver(GlobalFinals.FILES_FILE_HEADS);
        loadSaved();
        loadCustom();
    }

    public void save() {
        HashSet<UUID> nonCustom = new HashSet<>(values.keySet());
        for (CustomHead customHead : CustomHead.values()) {
            nonCustom.remove(customHead.getUuid());
        }
        for (UUID uuid : nonCustom) {
            fileSaver.put(uuid.toString(), values.get(uuid));
        }
        fileSaver.save();
    }

    private void loadSaved() {
        fileSaver.load();
        for (String key : fileSaver.getKeys()) {
            UUID uuid = UUID.fromString(key);
            values.put(uuid,fileSaver.getString(key));
        }
    }

    private void loadCustom() {
        for (CustomHead customHead : CustomHead.values()) {
            addCustomHead(customHead.getUuid(), customHead.getValue());
            buildHead(customHead.getUuid(), customHead.getName());
        }
        Main.getPlugin().getLogger().info("Loaded " + CustomHead.values().length + " custom head(s)");
    }

    public void addCustomHead(UUID uuid, String value) {
        if (uuid == null || value == null) return;
        values.put(uuid, value);
    }

    private ItemStack getHeadFromValue(String value, UUID uuid) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();

        try {
            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            GameProfile gameProfile = new GameProfile(uuid, "Player");
            gameProfile.getProperties().put("textures", new Property("textures", value));

            Class<?> resolvableProfileClass = Class.forName("net.minecraft.world.item.component.ResolvableProfile");  // Change depending on your version
            Object nmsProfile = resolvableProfileClass.getConstructor(gameProfile.getClass()).newInstance(gameProfile);

            profileField.set(skullMeta, nmsProfile);
        } catch (Exception e) {
            Main.getPlugin().getLogger().info("Error trying to create player head");
            return null;
        }
        head.setItemMeta(skullMeta);
        return head;

        /*UUID hashAsId = new UUID(value.hashCode(), value.hashCode());
        Main.getPlugin().getLogger().info(skull.toString());
        return Bukkit.getUnsafe().modifyItemStack(skull,
                "{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + value + "\"}]}}}"
        );*/
    }

    // https://www.spigotmc.org/threads/tutorial-get-player-heads-without-lag.396186/

    private String downloadHeadValue(UUID uuid) {
        try {
            JsonObject obj;
            Gson g = new Gson();
            /*String result = getURLContent("https://api.mojang.com/users/profiles/minecraft/" + name);
            obj = g.fromJson(result, JsonObject.class);
            String uid = obj.get("id").toString().replace("\"","");*/
            String uid = uuid.toString().replace("-", "");
            String signature = getURLContent("https://sessionserver.mojang.com/session/minecraft/profile/" + uid);
            obj = g.fromJson(signature, JsonObject.class);
            String value = obj.getAsJsonArray("properties").get(0).getAsJsonObject().get("value").getAsString();
            String decoded = new String(Base64.getDecoder().decode(value));
            obj = g.fromJson(decoded, JsonObject.class);
            String skinURL = obj.getAsJsonObject("textures").getAsJsonObject("SKIN").get("url").getAsString();
            byte[] skinByte = ("{\"textures\":{\"SKIN\":{\"url\":\"" + skinURL + "\"}}}").getBytes();
            return new String(Base64.getEncoder().encode(skinByte));
        } catch (Exception ignored) {
            Main.getPlugin().getLogger().info("Failed to download head texture of " + uuid.toString());
        }
        return null;
    }

    private String getURLContent(String urlStr) {
        URL url;
        BufferedReader in = null;
        StringBuilder sb = new StringBuilder();
        try {
            url = new URL(urlStr);
            in = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            String str;
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
        } catch (Exception ignored) {
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ignored) {
            }
        }
        return sb.toString();
    }

}
