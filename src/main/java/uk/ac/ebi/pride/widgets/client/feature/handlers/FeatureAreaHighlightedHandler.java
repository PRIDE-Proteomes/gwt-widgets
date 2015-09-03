package uk.ac.ebi.pride.widgets.client.feature.handlers;

import com.google.gwt.event.shared.EventHandler;
import uk.ac.ebi.pride.widgets.client.feature.events.FeatureAreaHighlightEvent;

public interface FeatureAreaHighlightedHandler extends EventHandler {

    public void onFeatureAreaHighlighted(FeatureAreaHighlightEvent e);

}
