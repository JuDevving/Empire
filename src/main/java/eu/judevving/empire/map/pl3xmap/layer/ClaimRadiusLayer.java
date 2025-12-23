package eu.judevving.empire.map.pl3xmap.layer;

import eu.judevving.empire.main.Main;
import eu.judevving.empire.map.MapFinals;
import net.pl3x.map.core.markers.layer.WorldLayer;
import net.pl3x.map.core.markers.marker.Circle;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.markers.option.Options;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ClaimRadiusLayer extends WorldLayer {

    public ClaimRadiusLayer(World world) {
        super(MapFinals.LAYER_CLAIM_RADIUS_KEY, world, () -> MapFinals.LAYER_CLAIM_RADIUS_NAME);

        setUpdateInterval(MapFinals.LAYER_CLAIM_RADIUS_UPDATE_INTERVAL);
        setLiveUpdate(true);
        setDefaultHidden(MapFinals.LAYER_CLAIM_RADIUS_DEFAULT_HIDDEN);
        setPriority(MapFinals.LAYER_CLAIM_RADIUS_PRIORITY);
        setZIndex(MapFinals.LAYER_CLAIM_RADIUS_Z_INDEX);

        world.getLayerRegistry().register(this);
    }

    @Override
    public @NotNull Collection<@NotNull Marker<?>> getMarkers() {
        Set<Marker<?>> markers = new HashSet<>();
        Main.getPlugin().getEarth().getStates().forEach(state -> {
            Circle circle = state.getClaimRadiusCircle();
            if (circle != null) {
                circle.setOptions(Options.builder()
                        .strokeColor(state.getStateColor().getMapEdgeColor())
                        .fillColor(MapFinals.COLOR_TRANSPARENT.getRGB())
                        .build());
                markers.add(circle);
            }
        });
        return markers;
    }

}
