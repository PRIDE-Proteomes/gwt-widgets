package uk.ac.ebi.pride.widgets.client.feature.handlers;

import com.google.gwt.event.shared.EventHandler;
import uk.ac.ebi.pride.widgets.client.feature.events.FeatureAreaSelectionEvent;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public interface FeatureAreaSelectedHandler extends EventHandler {

    public void onFeatureAreaSelected(FeatureAreaSelectionEvent e);

}
