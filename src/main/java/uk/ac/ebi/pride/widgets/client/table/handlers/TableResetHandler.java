package uk.ac.ebi.pride.widgets.client.table.handlers;

import com.google.gwt.event.shared.EventHandler;
import uk.ac.ebi.pride.widgets.client.table.events.TableResetEvent;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public interface TableResetHandler extends EventHandler {

    void onTableReset(TableResetEvent eventTable);

}
