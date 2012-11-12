package uk.ac.ebi.pride.widgets.client.sequence.events;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.pride.widgets.client.sequence.handlers.ProteinRegionSelectedHandler;
import uk.ac.ebi.pride.widgets.client.sequence.utils.CanvasSelection;


public class ProteinRegionSelectionEvent extends GwtEvent<ProteinRegionSelectedHandler> {
    @SuppressWarnings("Convert2Diamond")
    public static Type<ProteinRegionSelectedHandler> TYPE = new Type<ProteinRegionSelectedHandler>();

    private Integer start;
    private Integer end;

    public ProteinRegionSelectionEvent(CanvasSelection canvasSelection) {
        int regionStart = canvasSelection.getRegionStart();
        int regionEnd = canvasSelection.getRegionEnd();
        if(regionEnd<regionStart){
            int aux = regionStart;
            regionStart = regionEnd;
            regionEnd = aux;
        }
        this.start = regionStart;
        this.end = regionEnd;

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
