package uk.ac.ebi.pride.widgets.client.protein.events;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.pride.widgets.client.common.handler.PeptideHandler;
import uk.ac.ebi.pride.widgets.client.protein.handlers.PeptideHighlightedHandler;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class PeptideHighlightedEvent extends GwtEvent<PeptideHighlightedHandler> {
    public static Type<PeptideHighlightedHandler> TYPE = new GwtEvent.Type<PeptideHighlightedHandler>();

    PeptideHandler peptide;

    public PeptideHighlightedEvent(PeptideHandler peptide) {
        this.peptide = peptide;
    }

    public PeptideHandler getPeptide() {
        return peptide;
    }

    @Override
    public Type<PeptideHighlightedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PeptideHighlightedHandler handler) {
        handler.onProteinRegionHighlighted(this);
    }
}
