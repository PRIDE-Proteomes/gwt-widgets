package uk.ac.ebi.pride.widgets.client.protein.handlers;

import com.google.gwt.event.shared.EventHandler;
import uk.ac.ebi.pride.widgets.client.protein.events.ProteinRegionHighlightEvent;

public interface ProteinRegionHighlightedHandler extends EventHandler {

    public void onProteinRegionHighlighted(ProteinRegionHighlightEvent e);

}
