package uk.ac.ebi.pride.widgets.client.sequence.handlers;

import com.google.gwt.event.shared.EventHandler;
import uk.ac.ebi.pride.widgets.client.sequence.events.ProteinPositionHighlightedEvent;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public interface ProteinPositionHighlightedHandler extends EventHandler {

    public void onProteinPositionHighlighted(ProteinPositionHighlightedEvent e);

}
