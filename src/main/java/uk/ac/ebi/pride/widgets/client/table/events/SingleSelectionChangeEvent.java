package uk.ac.ebi.pride.widgets.client.table.events;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.pride.widgets.client.table.handlers.SingleSelectionChangeHandler;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class SingleSelectionChangeEvent<T> extends GwtEvent<SingleSelectionChangeHandler> {
    @SuppressWarnings("Convert2Diamond")
    public static Type<SingleSelectionChangeHandler> TYPE = new GwtEvent.Type<SingleSelectionChangeHandler>();

    private T item;


    public SingleSelectionChangeEvent(T item) {
        this.item = item;
    }

    @SuppressWarnings("UnusedDeclaration")
    public T getItem() {
        return item;
    }

    @Override
    public Type<SingleSelectionChangeHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SingleSelectionChangeHandler handler) {
        handler.onSelectionChange(this);
    }
}
