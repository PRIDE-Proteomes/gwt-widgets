package uk.ac.ebi.pride.widgets.client.feature.events;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.pride.widgets.client.feature.handlers.FeatureAreaHighlightedHandler;
import uk.ac.ebi.pride.widgets.client.feature.model.FeatureAreaSelection;

public class FeatureAreaHighlightEvent extends GwtEvent<FeatureAreaHighlightedHandler> {
    @SuppressWarnings("Convert2Diamond")
    public static Type<FeatureAreaHighlightedHandler> TYPE = new Type<FeatureAreaHighlightedHandler>();

    private Integer start;
    private Integer end;

    public FeatureAreaHighlightEvent(FeatureAreaSelection proteinSelection) {
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
    public Type<FeatureAreaHighlightedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(FeatureAreaHighlightedHandler handler) {
        handler.onFeatureAreaHighlighted(this);
    }

    @Override
    public String toString() {
        return "FeatureAreaHighlightEvent{" +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
