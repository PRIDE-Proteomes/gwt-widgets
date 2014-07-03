package uk.ac.ebi.pride.widgets.client.protein.events;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.pride.widgets.client.protein.handlers.ProteinAreaHighlightedHandler;
import uk.ac.ebi.pride.widgets.client.protein.model.ProteinAreaSelection;

public class ProteinAreaHighlightEvent extends GwtEvent<ProteinAreaHighlightedHandler> {
    @SuppressWarnings("Convert2Diamond")
    public static Type<ProteinAreaHighlightedHandler> TYPE = new Type<ProteinAreaHighlightedHandler>();

    private Integer start;
    private Integer end;

    public ProteinAreaHighlightEvent(ProteinAreaSelection proteinSelection) {
        this.start = proteinSelection.getStart();
        this.end = proteinSelection.getEnd();
    }

    public Integer getStart() {
        return start;
    }

    public Integer getEnd() {
        return end;
    }

    @Override
    public Type<ProteinAreaHighlightedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ProteinAreaHighlightedHandler handler) {
        handler.onProteinAreaHighlighted(this);
    }

    @Override
    public String toString() {
        return "ProteinAreaHighlightEvent{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
