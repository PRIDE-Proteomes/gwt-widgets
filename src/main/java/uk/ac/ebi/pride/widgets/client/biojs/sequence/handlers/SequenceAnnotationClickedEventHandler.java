package uk.ac.ebi.pride.widgets.client.biojs.sequence.handlers;

import com.google.gwt.event.shared.EventHandler;
import uk.ac.ebi.pride.widgets.client.biojs.sequence.events.SequenceAnnotationClickedEvent;

public interface SequenceAnnotationClickedEventHandler extends EventHandler {

    public void onAnnotationClicked(SequenceAnnotationClickedEvent e);

}
