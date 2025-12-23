package eu.judevving.empire.map.pl3xmap.layer;

import eu.judevving.empire.main.Main;
import eu.judevving.empire.map.MapFinals;
import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.markers.layer.WorldLayer;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.markers.option.Options;
import net.pl3x.map.core.markers.option.Tooltip;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MarkerLayer extends WorldLayer {

    public MarkerLayer(World world) {
        super(MapFinals.LAYER_MARKER_KEY, world, () -> MapFinals.LAYER_MARKER_NAME);

        setUpdateInterval(MapFinals.LAYER_MARKER_UPDATE_INTERVAL);
        setLiveUpdate(true);
        setDefaultHidden(MapFinals.LAYER_MARKER_DEFAULT_HIDDEN);
        setPriority(MapFinals.LAYER_MARKER_PRIORITY);
        setZIndex(MapFinals.LAYER_MARKER_Z_INDEX);
        setCss(MapFinals.LAYER_MARKERS_CSS);

        world.getLayerRegistry().register(this);
    }

    @Override
    public @NotNull Collection<@NotNull Marker<?>> getMarkers() {
        Set<Marker<?>> markers = new HashSet<>();
        Main.getPlugin().getEarth().getStates().forEach(state -> {
            if (state.getCapital() != null) {
                if (state.getCapitalIcon() == null) {
                    state.setCapitalIcon(Marker.icon(MapFinals.ICON_CAPITAL_KEY_PREFIX + state.getStateId(),
                            new Point(state.getCapital().getCenterBlockX(), state.getCapital().getCenterBlockZ()),
                            MapFinals.ICON_CAPITAL_KEY, MapFinals.ICON_SIZE).setOptions(
                            Options.builder()
                                    .tooltipContent(MapFinals.CAPITAL_POPUP_TEXT
                                            .replace("{name}", state.getCapitalName()))
                                    .tooltipSticky(true)
                                    .tooltipDirection(Tooltip.Direction.TOP).popupContent(null).build()));
                }
                markers.add(state.getCapitalIcon());
            }
            state.getMarkerManager().addMarkers(markers);
        });
        return markers;
    }

}
