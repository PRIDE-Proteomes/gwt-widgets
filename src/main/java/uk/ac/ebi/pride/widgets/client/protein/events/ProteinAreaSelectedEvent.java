package uk.ac.ebi.pride.widgets.client.protein.events;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.pride.widgets.client.protein.handlers.ProteinAreaSelectedHandler;
import uk.ac.ebi.pride.widgets.client.protein.model.ProteinAreaSelection;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("UnusedDeclaration")
public class ProteinAreaSelectedEvent extends GwtEvent<ProteinAreaSelectedHandler> {
    @SuppressWarnings("Convert2Diamond")
    public static Type<ProteinAreaSelectedHandler> TYPE = new Type<ProteinAreaSelectedHandler>();

    private boolean resetObjectSelection;
    private boolean selectedAreaClicked;
    private int start;
    private int end;

    public ProteinAreaSelectedEvent(boolean resetObjectSelection, ProteinAreaSelection proteinSelection) {
        this.resetObjectSelection = resetObjectSelection;
        this.selectedAreaClicked = proteinSelection.isSelected();
        this.start = proteinSelection.getStart();
        this.end = proteinSelection.getEnd();
    }

    public boolean isResetObjectSelection() {
        return resetObjectSelection;
    }

    public boolean isSelectedAreaClicked() {
        return selectedAreaClicked;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public Type<ProteinAreaSelectedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ProteinAreaSelectedHandler handler) {
        handler.onProteinAreaSelected(this);
    }

    @Override
    public String toString() {
        return "ProteinAreaSelectedEvent{" +
                "resetObjectSelection=" + resetObjectSelection +
                ", selectedAreaClicked=" + selectedAreaClicked +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
