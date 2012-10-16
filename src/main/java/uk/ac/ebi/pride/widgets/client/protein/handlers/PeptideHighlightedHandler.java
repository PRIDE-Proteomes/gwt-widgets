package uk.ac.ebi.pride.widgets.client.protein.handlers;

import com.google.gwt.event.shared.EventHandler;
import uk.ac.ebi.pride.widgets.client.protein.events.PeptideHighlightedEvent;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public interface PeptideHighlightedHandler extends EventHandler {

    public void onPeptideHighlightChanged(PeptideHighlightedEvent e);
}
