package uk.ac.ebi.pride.widgets.client.sequence.handlers;

import com.google.gwt.event.shared.EventHandler;
import uk.ac.ebi.pride.widgets.client.sequence.events.ProteinRegionResetEvent;

public interface ProteinRegionResetHandler extends EventHandler {

    void onProteinRegionReset(ProteinRegionResetEvent e);

}
