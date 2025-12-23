package eu.judevving.empire.map.pl3xmap;

import eu.judevving.empire.main.Main;
import eu.judevving.empire.main.GlobalFinals;
import eu.judevving.empire.map.MapFinals;
import eu.judevving.empire.map.marker.MarkerType;
import eu.judevving.empire.map.pl3xmap.layer.*;
import eu.toastlawine.empire.map.pl3xmap.layer.*;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.image.IconImage;
import net.pl3x.map.core.markers.area.Area;
import net.pl3x.map.core.util.FileUtil;
import net.pl3x.map.core.world.World;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Pl3xmapConnection {

    private ClaimRadiusLayer claimRadiusLayer;
    private MarkerLayer markerLayer;
    private PoiLayer poiLayer;
    private RailwayLayer railwayLayer;
    private RecentClaimLayer recentClaimLayer;
    private StateLayer stateLayer;

    public Pl3xmapConnection() {
        Pl3xMap.api().getEventRegistry().register(new WorldLoadListener());
    }

    public boolean isInitialized() {
        return poiLayer != null;
    }

    public void initOnEnable(World world) {
        registerLayers(world);
        setVisibleArea(world);
        registerIcons();
    }

    private void setVisibleArea(World world) {
        List<Area> visibleAreas = world.getConfig().VISIBLE_AREAS;
        visibleAreas.clear();
        visibleAreas.add(new VisibleArea(GlobalFinals.EARTH_BLOCK_MIN_X, GlobalFinals.EARTH_BLOCK_MIN_Z,
                GlobalFinals.EARTH_BLOCK_MAX_X - 1, GlobalFinals.EARTH_BLOCK_MAX_Z - 1));
        /*visibleAreas.add(new VisibleArea(MiniatureEarth.MIN_X, MiniatureEarth.MIN_Z,
                MiniatureEarth.MAX_X - 1, MiniatureEarth.MAX_Z - 1));*/
    }

    private void registerLayers(World world) {
        claimRadiusLayer = new ClaimRadiusLayer(world);
        markerLayer = new MarkerLayer(world);
        poiLayer = new PoiLayer(world);
        railwayLayer = new RailwayLayer(world);
        recentClaimLayer = new RecentClaimLayer(world);
        stateLayer = new StateLayer(world);
    }

    private void registerIcons() {
        registerIcon(MapFinals.ICON_CAPITAL_FILE, MapFinals.ICON_CAPITAL_KEY);
        registerIcon(MapFinals.ICON_CRATE_FILE, MapFinals.ICON_CRATE_KEY);
        registerIcon(MapFinals.ICON_POI_CUSTOM_FILE, MapFinals.ICON_POI_CUSTOM_KEY);
        for (MarkerType markerType : MarkerType.values()) {
            registerIcon(markerType.getPath(), markerType.getKey());
        }
    }

    private void registerIcon(String path, String key) {
        try {
            Path path1 = FileUtil.getWebDir().resolve(path);
            IconImage iconImage = new IconImage(key, ImageIO.read(path1.toFile()), "png");
            Pl3xMap.api().getIconRegistry().register(iconImage);
        } catch (IOException e) {
            Main.getPlugin().getLogger().info("Failed to register icon " + key);
        }
    }

    public PoiLayer getPoiLayer() {
        return poiLayer;
    }
}
