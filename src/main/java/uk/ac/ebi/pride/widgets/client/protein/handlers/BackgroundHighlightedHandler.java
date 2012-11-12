package uk.ac.ebi.pride.widgets.client.protein.handlers;

import com.google.gwt.event.shared.EventHandler;
import uk.ac.ebi.pride.widgets.client.protein.events.BackgroundHighlightEvent;

public interface BackgroundHighlightedHandler extends EventHandler {

    public void onBackgroundHighlighted(BackgroundHighlightEvent e);

}
