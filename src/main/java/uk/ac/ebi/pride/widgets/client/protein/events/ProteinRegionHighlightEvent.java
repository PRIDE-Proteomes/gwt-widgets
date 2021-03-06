package uk.ac.ebi.pride.widgets.client.protein.events;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.pride.widgets.client.protein.handlers.ProteinRegionHighlightedHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ProteinRegionHighlightEvent extends GwtEvent<ProteinRegionHighlightedHandler> {

    Logger logger = Logger.getLogger(ProteinRegionHighlightEvent.class.getName());

    public static Type<ProteinRegionHighlightedHandler> TYPE = new Type<ProteinRegionHighlightedHandler>();

    private Integer start;
    private Integer length;
    private Integer value;

    public ProteinRegionHighlightEvent(Integer start, Integer length, Integer value) {
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
    public Type<ProteinRegionHighlightedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ProteinRegionHighlightedHandler handler) {
        logger.log(Level.INFO, "ProteinRegionHighlighted has been dispatch");
        handler.onProteinRegionHighlighted(this);
    }

    @Override
    public String toString() {
        return "ProteinRegionHighlightedEvent{" +
                "start=" + start +
                ", length=" + length +
                ", value=" + value +
                '}';
    }
}
