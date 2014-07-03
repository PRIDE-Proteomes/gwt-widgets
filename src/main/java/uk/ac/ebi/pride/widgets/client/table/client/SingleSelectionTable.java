package uk.ac.ebi.pride.widgets.client.table.client;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import uk.ac.ebi.pride.widgets.client.table.events.TableResetEvent;
import uk.ac.ebi.pride.widgets.client.table.events.SingleSelectionChangeEvent;
import uk.ac.ebi.pride.widgets.client.table.handlers.SingleSelectionChangeHandler;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("UnusedDeclaration")
public class SingleSelectionTable<T> extends AbstractTable<T> {
    private T selectedItem;

    public SingleSelectionTable() {
        super();

        @SuppressWarnings("Convert2Diamond")
        SelectionModel<? super T> selectionModel = new SingleSelectionModel<T>();
        this.setSelectionModel(selectionModel);
        selectionModel.addSelectionChangeHandler(this);

        this.selectedItem = null;
    }

    public HandlerRegistration addSingleSelectionChangeHandler(SingleSelectionChangeHandler handler){
        return addHandler(handler, SingleSelectionChangeEvent.TYPE);
    }

    public T getSelectedItem() {
        return selectedItem;
    }

    public void resetSelection(boolean fireEvent){
        if(this.selectedItem != null){
            //this.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
            getSelectionModel().setSelected(this.selectedItem, false);
            //this.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);
            //getKeyboardSelectedElement().blur(); // !!this is causing exceptions and needs to be investigated!!
        }
    }

    public void setSelectedItem(T selectedItem) {
        if(selectedItem!=null && !selectedItem.equals(this.selectedItem)){
            getSelectionModel().setSelected(selectedItem, true);
            if(this.selectedItem!=selectedItem){
                this.selectedItem = selectedItem;
                //noinspection Convert2Diamond
                fireEvent(new SingleSelectionChangeEvent<T>(this.selectedItem));
            }
        }
    }

    public void scrollIntoSelectedItem(){
        if(this.selectedItem!=null){
            getRowElement(getVisibleItems().indexOf(this.selectedItem)).scrollIntoView();
        }
    }

    @Override
    public void onSelectionChange(SelectionChangeEvent event) {
        SingleSelectionModel<T> selectionModel = (SingleSelectionModel<T>) getSelectionModel();

        T lastSelection = this.selectedItem;
        this.selectedItem = selectionModel.getSelectedObject();

        if(this.selectedItem==null){
            fireEvent(new TableResetEvent());
        }else if(this.selectedItem!=lastSelection){
            //noinspection Convert2Diamond
            fireEvent(new SingleSelectionChangeEvent<T>(this.selectedItem));
        }
    }
}
