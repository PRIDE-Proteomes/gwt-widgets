package uk.ac.ebi.pride.widgets.client.feature.events;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.pride.widgets.client.feature.handlers.FeatureRegionSelectedHandler;

import java.util.logging.Level;
import java.util.logging.Logger;


public class FeatureRegionSelectionEvent extends GwtEvent<FeatureRegionSelectedHandler> {

    Logger logger = Logger.getLogger(FeatureRegionSelectionEvent.class.getName());

    public static Type<FeatureRegionSelectedHandler> TYPE = new Type<FeatureRegionSelectedHandler>();

    private Integer start;
    private Integer length;
    private Integer value;

    public FeatureRegionSelectionEvent(Integer start, Integer length) {
        this.start = start;
        this.length = length;
    }

    public Integer getStart() {
        return start;
    }

    public Integer getLength() {
        return length;
    }

    @Override
    public Type<FeatureRegionSelectedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(FeatureRegionSelectedHandler handler) {
        logger.log(Level.INFO, "FeatureRegionSelectionChanged has been dispatch");
        handler.onFeatureRegionSelectionChanged(this);
    }

    @Override
    public String toString() {
        return "FeatureRegionSelectionEvent{" +
                "start=" + start +
                ", length=" + length +
                ", value=" + value +
                '}';
    }
}
