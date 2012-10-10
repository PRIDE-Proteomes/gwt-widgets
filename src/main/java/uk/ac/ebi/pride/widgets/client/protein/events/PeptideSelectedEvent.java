package uk.ac.ebi.pride.widgets.client.protein.events;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.pride.widgets.client.common.handler.PeptideHandler;
import uk.ac.ebi.pride.widgets.client.protein.handlers.PeptideSelectedHandler;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class PeptideSelectedEvent extends GwtEvent<PeptideSelectedHandler> {
    public static Type<PeptideSelectedHandler> TYPE = new GwtEvent.Type<PeptideSelectedHandler>();

    PeptideHandler peptide;

    public PeptideSelectedEvent(PeptideHandler peptide) {
        this.peptide = peptide;
    }

    public PeptideHandler getPeptide() {
        return peptide;
    }

    @Override
    public Type<PeptideSelectedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PeptideSelectedHandler handler) {
        handler.onProteinRegionHighlighted(this);
    }
}
