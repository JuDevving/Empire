package eu.judevving.empire.map.pl3xmap.layer;

import eu.judevving.empire.main.Main;
import eu.judevving.empire.map.MapFinals;
import net.pl3x.map.core.markers.layer.WorldLayer;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RailwayLayer extends WorldLayer {

    public RailwayLayer(World world) {
        super(MapFinals.LAYER_RAILWAY_KEY, world, () -> MapFinals.LAYER_RAILWAY_NAME);

        setUpdateInterval(MapFinals.LAYER_RAILWAY_UPDATE_INTERVAL);
        setLiveUpdate(true);
        setDefaultHidden(MapFinals.LAYER_RAILWAY_DEFAULT_HIDDEN);
        setPriority(MapFinals.LAYER_RAILWAY_PRIORITY);
        setZIndex(MapFinals.LAYER_RAILWAY_Z_INDEX);

        world.getLayerRegistry().register(this);
    }

    @Override
    public @NotNull Collection<@NotNull Marker<?>> getMarkers() {
        Set<Marker<?>> markers = new HashSet<>();
        markers.add(Main.getPlugin().getEarth().getRailwayManager().getMultiPolyline());
        return markers;
    }
}
