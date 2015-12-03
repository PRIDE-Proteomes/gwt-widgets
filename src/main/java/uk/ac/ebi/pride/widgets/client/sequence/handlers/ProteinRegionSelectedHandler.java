package uk.ac.ebi.pride.widgets.client.sequence.handlers;

import com.google.gwt.event.shared.EventHandler;
import uk.ac.ebi.pride.widgets.client.sequence.events.ProteinRegionSelectionEvent;

public interface ProteinRegionSelectedHandler extends EventHandler {

    void onProteinRegionSelectionChanged(ProteinRegionSelectionEvent e);
}
