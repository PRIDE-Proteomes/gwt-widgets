package uk.ac.ebi.pride.widgets.client.sequence.handlers;

import com.google.gwt.event.shared.EventHandler;
import uk.ac.ebi.pride.widgets.client.sequence.events.ProteinRegionHighlightedEvent;

public interface ProteinRegionHighlightedHandler extends EventHandler {

    void onProteinRegionHighlighted(ProteinRegionHighlightedEvent e);

}
