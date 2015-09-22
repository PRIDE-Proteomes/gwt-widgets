package uk.ac.ebi.pride.widgets.client.protein.events;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.pride.widgets.client.protein.handlers.ProteinRegionSelectedHandler;

import java.util.logging.Level;
import java.util.logging.Logger;


public class ProteinRegionSelectionEvent extends GwtEvent<ProteinRegionSelectedHandler> {

    Logger logger = Logger.getLogger(ProteinRegionSelectionEvent.class.getName());

    public static Type<ProteinRegionSelectedHandler> TYPE = new Type<ProteinRegionSelectedHandler>();

    private Integer start;
    private Integer length;
    private Integer value;

    public ProteinRegionSelectionEvent(Integer start, Integer length, Integer value) {
        this.start = start;
        this.length = length;
        this.value = value;
    }

    public Integer getStart() {
        return start;
    }

    public Integer getLength() {
        return length;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public Type<ProteinRegionSelectedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ProteinRegionSelectedHandler handler) {
        logger.log(Level.INFO, "ProteinRegionSelectionChanged has been dispatch");
        handler.onProteinRegionSelectionChanged(this);
    }

    @Override
    public String toString() {
        return "ProteinRegionSelectionEvent{" +
                "start=" + start +
                ", length=" + length +
                ", value=" + value +
                '}';
    }
}
