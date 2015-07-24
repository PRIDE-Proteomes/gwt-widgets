package uk.ac.ebi.pride.widgets.client.feature.events;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.pride.widgets.client.feature.handlers.FeatureRegionHighlightedHandler;

/**
 * @author ntoro
 * @since 21/07/15 16:59
 */
public class FeatureRegionHighlightedEvent extends GwtEvent<FeatureRegionHighlightedHandler> {

    public static Type<FeatureRegionHighlightedHandler> TYPE = new Type<FeatureRegionHighlightedHandler>();

    private Integer start;
    private Integer length;

    public FeatureRegionHighlightedEvent(Integer start, Integer length) {
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
        return null;
    }

    @Override
    protected void dispatch(FeatureRegionHighlightedHandler handler) {

    }
}
