package uk.ac.ebi.pride.widgets.client.feature.events;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.pride.widgets.client.feature.handlers.FeatureRegionHighlightedHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ntoro
 * @since 21/07/15 16:59
 */
public class FeatureRegionHighlightEvent extends GwtEvent<FeatureRegionHighlightedHandler> {

    Logger logger = Logger.getLogger(FeatureRegionSelectionEvent.class.getName());

    public static Type<FeatureRegionHighlightedHandler> TYPE = new Type<FeatureRegionHighlightedHandler>();

    private Integer start;
    private Integer length;

    public FeatureRegionHighlightEvent(Integer start, Integer length) {
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
    public Type<FeatureRegionHighlightedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(FeatureRegionHighlightedHandler handler) {
        logger.log(Level.INFO, "FeatureRegionHighlighted has been dispatch");

        handler.onFeatureRegionHighlighted(this);
    }

    @Override
    public String toString() {
        return "FeatureRegionHighlightEvent{" +
                "start=" + start +
                ", length=" + length +
                '}';
    }
}
