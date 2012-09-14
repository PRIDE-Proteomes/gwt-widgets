package uk.ac.ebi.pride.widgets.client.biojs.sequence.events;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.pride.widgets.client.biojs.sequence.handlers.SequenceAnnotationClickedEventHandler;

public class SequenceAnnotationClickedEvent extends GwtEvent<SequenceAnnotationClickedEventHandler> {
    public static Type<SequenceAnnotationClickedEventHandler> TYPE = new Type<SequenceAnnotationClickedEventHandler>();

    private String name;
    private int position;

    public SequenceAnnotationClickedEvent(String name, int position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public Type<SequenceAnnotationClickedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SequenceAnnotationClickedEventHandler handler) {
        handler.onAnnotationClicked(this);
    }
}
