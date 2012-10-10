package uk.ac.ebi.pride.widgets.client.sequence.events;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.pride.widgets.client.sequence.handlers.ProteinPositionHighlightedHandler;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class ProteinPositionHighlightedEvent extends GwtEvent<ProteinPositionHighlightedHandler> {
    public static Type<ProteinPositionHighlightedHandler> TYPE = new Type<ProteinPositionHighlightedHandler>();

    private Integer position;

    public ProteinPositionHighlightedEvent(Integer position) {
        this.position = position;
    }

    public Integer getPosition() {
        return position;
    }

    @Override
    public Type<ProteinPositionHighlightedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ProteinPositionHighlightedHandler handler) {
        handler.onProteinPositionHighlighted(this);
    }

    @Override
    public String toString() {
        return "ProteinPositionHighlightedEvent{" +
                "position=" + position +
                '}';
    }
}
