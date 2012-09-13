package uk.ac.ebi.pride.widgets.client.protein.handlers;

import com.google.gwt.event.shared.EventHandler;
import uk.ac.ebi.pride.widgets.client.protein.events.ProteinRegionSelectionEvent;

public interface ProteinRegionSelectedHandler extends EventHandler {

    public void onProteinRegionSelectionChanged(ProteinRegionSelectionEvent e);
}
