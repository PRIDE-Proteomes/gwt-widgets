package uk.ac.ebi.pride.widgets.client.biojs.sequence.handlers;

import com.google.gwt.event.shared.EventHandler;
import uk.ac.ebi.pride.widgets.client.biojs.sequence.events.SequenceSelectionEvent;

public interface SequenceSelectionChangeEventHandler  extends EventHandler {

    public void onSelectionChange(SequenceSelectionEvent e);

}
