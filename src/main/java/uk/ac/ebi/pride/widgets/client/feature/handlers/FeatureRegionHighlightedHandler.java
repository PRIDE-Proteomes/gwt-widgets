package uk.ac.ebi.pride.widgets.client.feature.handlers;

import com.google.gwt.event.shared.EventHandler;
import uk.ac.ebi.pride.widgets.client.feature.events.FeatureRegionHighlightEvent;

/**
 * @author ntoro
 * @since 21/07/15 17:00
 */
public interface FeatureRegionHighlightedHandler extends EventHandler {

    public void onFeatureRegionHighlighted(FeatureRegionHighlightEvent e);

}
