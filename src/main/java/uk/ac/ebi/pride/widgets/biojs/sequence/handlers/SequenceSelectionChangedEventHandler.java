package uk.ac.ebi.pride.widgets.biojs.sequence.handlers;

import com.google.gwt.event.shared.EventHandler;
import uk.ac.ebi.pride.widgets.biojs.sequence.events.SequenceSelectionEvent;

public interface SequenceSelectionChangedEventHandler extends EventHandler {

    public void onSelectionChanged(SequenceSelectionEvent e);

}