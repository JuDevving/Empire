package eu.judevving.empire.map.pl3xmap.layer;

import eu.judevving.empire.main.Main;
import eu.judevving.empire.map.MapFinals;
import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.markers.layer.WorldLayer;
import net.pl3x.map.core.markers.marker.Icon;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.markers.option.Options;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PoiLayer extends WorldLayer {

    public PoiLayer(World world) {
        super(MapFinals.LAYER_POI_KEY, world, () -> MapFinals.LAYER_POI_NAME);

        setUpdateInterval(MapFinals.LAYER_POI_UPDATE_INTERVAL);
        setLiveUpdate(true);
        setDefaultHidden(MapFinals.LAYER_POI_DEFAULT_HIDDEN);
        setPriority(MapFinals.LAYER_POI_PRIORITY);
        setZIndex(MapFinals.LAYER_POI_Z_INDEX);

        world.getLayerRegistry().register(this);
    }

    @Override
    public @NotNull Collection<@NotNull Marker<?>> getMarkers() {
        Set<Marker<?>> markers = new HashSet<>();
        Main.getPlugin().getEarth().getCrateManager().addCrateIcons(markers);
        Main.getPlugin().getEarth().getCustomPOIManager().addCustomPOIs(markers);
        return markers;
    }

    public static Icon getPOIIcon(String iconKey, int x, int z) {
        return Marker.icon(MapFinals.LAYER_POI_KEY_PREFIX + Math.random(), new Point(x, z), iconKey, MapFinals.ICON_SIZE)
                .setOptions(Options.builder().tooltipContent(null).popupContent(null).build());
    }

}
