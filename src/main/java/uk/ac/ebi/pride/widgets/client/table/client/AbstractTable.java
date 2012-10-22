package uk.ac.ebi.pride.widgets.client.table.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Redrawable;
import uk.ac.ebi.pride.widgets.client.table.events.TableResetEvent;
import uk.ac.ebi.pride.widgets.client.table.handlers.TableResetHandler;
import uk.ac.ebi.pride.widgets.client.table.style.TableResources;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("UnusedDeclaration")
public abstract class AbstractTable<T> extends DataGrid<T> implements Redrawable, SelectionChangeEvent.Handler {
    private final ListDataProvider<T> dataProvider;

    public AbstractTable() {
        super(1, (TableResources) GWT.create(TableResources.class));
        //noinspection Convert2Diamond
        this.dataProvider = new ListDataProvider<T>();
        this.dataProvider.addDataDisplay(this);
        //this.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);
        this.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
    }

    public HandlerRegistration addTableResetHandler(TableResetHandler handler){
        return addHandler(handler, TableResetEvent.TYPE);
    }

    public void clearData(){
        if(this.dataProvider!=null){
            this.dataProvider.getList().clear();
            //TODO fireEvent
        }
    }

    public void resetSelection(){
        resetSelection(true);
    }

    public abstract void resetSelection(boolean fireEvent);

    public void setList(List<T> list){
        if(this.dataProvider!=null){
            //TODO: Comment
            //noinspection Convert2Diamond
            List<T> aux = new LinkedList<T>();
            for (T t : list) { aux.add(t); }

            this.dataProvider.setList(aux);
            this.setPageSize(list.size());
        }
    }

}
