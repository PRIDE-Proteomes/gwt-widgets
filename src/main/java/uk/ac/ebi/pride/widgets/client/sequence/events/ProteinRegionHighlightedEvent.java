package uk.ac.ebi.pride.widgets.client.sequence.events;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.pride.widgets.client.sequence.handlers.ProteinRegionHighlightedHandler;
import uk.ac.ebi.pride.widgets.client.sequence.utils.CanvasSelection;

public class ProteinRegionHighlightedEvent extends GwtEvent<ProteinRegionHighlightedHandler> {
    public static Type<ProteinRegionHighlightedHandler> TYPE = new Type<ProteinRegionHighlightedHandler>();

    private Integer start;
    private Integer end;

    public ProteinRegionHighlightedEvent(CanvasSelection canvasSelection) {
        this.start = canvasSelection.getRegionStart();
        this.end = canvasSelection.getRegionEnd();
    }

    public Integer getStart() {
        return start;
    }

    public Integer getEnd() {
        return end;
    }

    @Override
    public Type<ProteinRegionHighlightedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ProteinRegionHighlightedHandler handler) {
        handler.onProteinRegionHighlighted(this);
    }

    @Override
    public String toString() {
        return "ProteinRegionHighlightedEvent{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
