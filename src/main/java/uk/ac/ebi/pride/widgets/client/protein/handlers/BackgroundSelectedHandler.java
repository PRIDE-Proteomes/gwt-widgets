package uk.ac.ebi.pride.widgets.client.protein.handlers;

import com.google.gwt.event.shared.EventHandler;
import uk.ac.ebi.pride.widgets.client.protein.events.BackgroundSelectedEvent;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public interface BackgroundSelectedHandler extends EventHandler {

    public void onBackgroundSelected(BackgroundSelectedEvent e);

}
