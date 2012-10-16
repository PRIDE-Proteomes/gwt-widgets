package uk.ac.ebi.pride.widgets.client.protein.events;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.pride.widgets.client.common.handler.PeptideHandler;
import uk.ac.ebi.pride.widgets.client.protein.handlers.PeptideSelectedHandler;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("UnusedDeclaration")
public class PeptideSelectedEvent extends GwtEvent<PeptideSelectedHandler> {
    @SuppressWarnings("Convert2Diamond")
    public static Type<PeptideSelectedHandler> TYPE = new GwtEvent.Type<PeptideSelectedHandler>();

    PeptideHandler peptide;

    public PeptideSelectedEvent(PeptideHandler peptide) {
        this.peptide = peptide;
        //System.out.println(getClass() + " --> " + toString());
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
        handler.onPeptideSelected(this);
    }

    @Override
    public String toString() {
        return "PeptideSelectedEvent{" +
                "peptide=" + peptide.getSequence() +
                ", uniqueness=" + peptide.getUniqueness() +
                ", start=" + peptide.getSite() +
                ", end=" + peptide.getEnd() +
                '}';
    }
}
