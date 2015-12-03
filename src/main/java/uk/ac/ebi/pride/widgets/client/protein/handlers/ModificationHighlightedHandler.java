package uk.ac.ebi.pride.widgets.client.protein.handlers;

import com.google.gwt.event.shared.EventHandler;
import uk.ac.ebi.pride.widgets.client.protein.events.ModificationHighlightedEvent;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public interface ModificationHighlightedHandler extends EventHandler {

    void onModificationHighlighted(ModificationHighlightedEvent e);

}
