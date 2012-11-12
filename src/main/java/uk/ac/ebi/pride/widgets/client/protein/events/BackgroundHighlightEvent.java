package uk.ac.ebi.pride.widgets.client.protein.events;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.pride.widgets.client.protein.handlers.BackgroundHighlightedHandler;
import uk.ac.ebi.pride.widgets.client.protein.model.Background;

public class BackgroundHighlightEvent extends GwtEvent<BackgroundHighlightedHandler> {
    @SuppressWarnings("Convert2Diamond")
    public static Type<BackgroundHighlightedHandler> TYPE = new Type<BackgroundHighlightedHandler>();

    private Integer start;
    private Integer end;

    public BackgroundHighlightEvent(Background background) {
        this.start = background.getStart();
        this.end = background.getEnd();
    }

    public Integer getStart() {
        return start;
    }

    public Integer getEnd() {
        return end;
    }

    @Override
    public Type<BackgroundHighlightedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(BackgroundHighlightedHandler handler) {
        handler.onBackgroundHighlighted(this);
    }

    @Override
    public String toString() {
        return "BackgroundHighlightEvent{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
