package uk.ac.ebi.pride.widgets.client.sequence.events;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.pride.widgets.client.sequence.handlers.ProteinRegionResetHandler;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class ProteinRegionResetEvent extends GwtEvent<ProteinRegionResetHandler> {
    public static Type<ProteinRegionResetHandler> TYPE = new Type<ProteinRegionResetHandler>();

    public ProteinRegionResetEvent() {
    }

    @Override
    public Type<ProteinRegionResetHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ProteinRegionResetHandler handler) {
        handler.onProteinRegionReset(this);
    }
}