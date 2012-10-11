package uk.ac.ebi.pride.widgets.client.table.events;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.pride.widgets.client.table.handlers.TableResetHandler;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class TableResetEvent extends GwtEvent<TableResetHandler> {
    @SuppressWarnings("Convert2Diamond")
    public static Type<TableResetHandler> TYPE = new GwtEvent.Type<TableResetHandler>();

    @Override
    public Type<TableResetHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(TableResetHandler handler) {
        handler.onPrideTableReset(this);
    }
}
