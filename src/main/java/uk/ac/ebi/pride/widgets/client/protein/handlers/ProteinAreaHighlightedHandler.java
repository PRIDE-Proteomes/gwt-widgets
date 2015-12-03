package uk.ac.ebi.pride.widgets.client.protein.handlers;

import com.google.gwt.event.shared.EventHandler;
import uk.ac.ebi.pride.widgets.client.protein.events.ProteinAreaHighlightEvent;

public interface ProteinAreaHighlightedHandler extends EventHandler {

    void onProteinAreaHighlighted(ProteinAreaHighlightEvent e);

}
