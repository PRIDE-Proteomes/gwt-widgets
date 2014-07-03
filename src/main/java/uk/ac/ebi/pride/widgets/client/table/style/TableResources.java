package uk.ac.ebi.pride.widgets.client.table.style;

import com.google.gwt.user.cellview.client.DataGrid;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public interface TableResources extends DataGrid.Resources {

    /**
     * The styles applied to the table.
     */
    /*interface TableStyle extends CellTable.Style {
    } */

    @Override
    @Source({ DataGrid.Style.DEFAULT_CSS, "DataGrid.css"})
    DataGrid.Style dataGridStyle();

}
