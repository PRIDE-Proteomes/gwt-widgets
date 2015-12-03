package uk.ac.ebi.pride.widgets.client.protein.handlers;

import com.google.gwt.event.shared.EventHandler;
import uk.ac.ebi.pride.widgets.client.protein.events.ProteinAreaSelectedEvent;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public interface ProteinAreaSelectedHandler extends EventHandler {

    void onProteinAreaSelected(ProteinAreaSelectedEvent e);

}
