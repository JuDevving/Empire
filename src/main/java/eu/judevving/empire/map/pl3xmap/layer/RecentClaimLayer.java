package eu.judevving.empire.map.pl3xmap.layer;

import eu.judevving.empire.main.Main;
import eu.judevving.empire.map.MapFinals;
import net.pl3x.map.core.markers.layer.WorldLayer;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.markers.marker.MultiPolygon;
import net.pl3x.map.core.markers.option.Options;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RecentClaimLayer  extends WorldLayer {

    public RecentClaimLayer(World world) {
        super(MapFinals.LAYER_RECENT_CLAIM_KEY, world, () -> MapFinals.LAYER_RECENT_CLAIM_NAME);

        setUpdateInterval(MapFinals.LAYER_RECENT_CLAIM_UPDATE_INTERVAL);
        setLiveUpdate(true);
        setDefaultHidden(MapFinals.LAYER_RECENT_CLAIM_DEFAULT_HIDDEN);
        setPriority(MapFinals.LAYER_RECENT_CLAIM_PRIORITY);
        setZIndex(MapFinals.LAYER_RECENT_CLAIM_Z_INDEX);

        world.getLayerRegistry().register(this);
    }

    @Override
    public @NotNull Collection<@NotNull Marker<?>> getMarkers() {
        Set<Marker<?>> markers = new HashSet<>();
        MultiPolygon multiPolygon = Main.getPlugin().getEarth().getClaimTimeManager().getMultipolygon();
        if (multiPolygon != null) {
            multiPolygon.setOptions(Options.builder()
                    .strokeColor(MapFinals.COLOR_RECENT_CLAIM_EDGE)
                    .fillColor(MapFinals.COLOR_RECENT_CLAIM_FILL)
                    .build());
            markers.add(multiPolygon);
        }
        return markers;
    }
}
