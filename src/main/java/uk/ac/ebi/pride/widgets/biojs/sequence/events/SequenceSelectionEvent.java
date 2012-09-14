package uk.ac.ebi.pride.widgets.biojs.sequence.events;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.pride.widgets.biojs.sequence.handlers.SequenceSelectionChangedEventHandler;

public class SequenceSelectionEvent extends GwtEvent<SequenceSelectionChangedEventHandler> {
    public static Type<SequenceSelectionChangedEventHandler> TYPE = new Type<SequenceSelectionChangedEventHandler>();

    private int start;
    private int end;

    public SequenceSelectionEvent(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public Type<SequenceSelectionChangedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SequenceSelectionChangedEventHandler handlerSequence) {
        handlerSequence.onSelectionChanged(this);
    }
}
