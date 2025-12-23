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

public class StateLayer extends WorldLayer {

    public StateLayer(World world) {
        super(MapFinals.LAYER_STATE_KEY, world, () -> MapFinals.LAYER_STATE_NAME);

        setUpdateInterval(MapFinals.LAYER_STATE_UPDATE_INTERVAL);
        setLiveUpdate(true);
        setDefaultHidden(MapFinals.LAYER_STATE_DEFAULT_HIDDEN);
        setPriority(MapFinals.LAYER_STATE_PRIORITY);
        setZIndex(MapFinals.LAYER_STATE_Z_INDEX);

        setCss(MapFinals.LAYER_STATES_CSS);

        world.getLayerRegistry().register(this);
    }

    @Override
    public @NotNull Collection<@NotNull Marker<?>> getMarkers() {
        Set<Marker<?>> markers = new HashSet<>();
        Main.getPlugin().getEarth().getStates().forEach(state -> {
            String key = MapFinals.MULTIPOLYGON_STATE_KEY_PREFIX + state.getStateId();
            MultiPolygon multiPolygon = state.getMultiPolygon(key);
            if (multiPolygon != null) {
                multiPolygon.setOptions(Options.builder()
                        .strokeColor(state.getStateColor().getMapEdgeColor())
                        .fillColor(state.getStateColor().getMapFillColor())
                        .popupContent(state.getPopupText())
                        .build());
                markers.add(multiPolygon);
            }
        });
        return markers;
    }

}
