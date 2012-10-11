package uk.ac.ebi.pride.widgets.client.table.handlers;

import com.google.gwt.event.shared.EventHandler;
import uk.ac.ebi.pride.widgets.client.table.events.SingleSelectionChangeEvent;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public interface SingleSelectionChangeHandler<T> extends EventHandler {

    public void onSelectionChange(SingleSelectionChangeEvent<T> event);

}
