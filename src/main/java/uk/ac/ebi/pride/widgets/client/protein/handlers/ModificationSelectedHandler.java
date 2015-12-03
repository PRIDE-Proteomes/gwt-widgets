package uk.ac.ebi.pride.widgets.client.protein.handlers;

import com.google.gwt.event.shared.EventHandler;
import uk.ac.ebi.pride.widgets.client.protein.events.ModificationSelectedEvent;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public interface ModificationSelectedHandler extends EventHandler {

    void onModificationSelected(ModificationSelectedEvent e);

}
