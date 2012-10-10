package uk.ac.ebi.pride.widgets.client.sequence.events;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.pride.widgets.client.sequence.handlers.ProteinRegionSelectedHandler;
import uk.ac.ebi.pride.widgets.client.sequence.utils.CanvasSelection;


public class ProteinRegionSelectionEvent extends GwtEvent<ProteinRegionSelectedHandler> {
    public static Type<ProteinRegionSelectedHandler> TYPE = new Type<ProteinRegionSelectedHandler>();

    private Integer start;
    private Integer end;

    public ProteinRegionSelectionEvent(CanvasSelection canvasSelection) {
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
    public Type<ProteinRegionSelectedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ProteinRegionSelectedHandler handler) {
        handler.onProteinRegionSelectionChanged(this);
    }

    @Override
    public String toString() {
        return "ProteinRegionSelectionEvent{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
