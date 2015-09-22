package uk.ac.ebi.pride.widgets.client.feature.handlers;

import com.google.gwt.event.shared.EventHandler;
import uk.ac.ebi.pride.widgets.client.feature.events.FeatureRegionSelectionEvent;

public interface FeatureRegionSelectedHandler extends EventHandler {

    public void onFeatureRegionSelectionChanged(FeatureRegionSelectionEvent e);
}
