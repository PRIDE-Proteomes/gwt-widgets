package uk.ac.ebi.pride.widgets.client.feature.events;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.pride.widgets.client.feature.handlers.FeatureAreaSelectedHandler;
import uk.ac.ebi.pride.widgets.client.feature.model.FeatureAreaSelection;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("UnusedDeclaration")
public class FeatureAreaSelectionEvent extends GwtEvent<FeatureAreaSelectedHandler> {
    @SuppressWarnings("Convert2Diamond")
    public static Type<FeatureAreaSelectedHandler> TYPE = new Type<FeatureAreaSelectedHandler>();

    private boolean resetObjectSelection;
    private boolean selectedAreaClicked;
    private int start;
    private int end;

    public FeatureAreaSelectionEvent(boolean resetObjectSelection, FeatureAreaSelection proteinSelection) {
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
    public Type<FeatureAreaSelectedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(FeatureAreaSelectedHandler handler) {
        handler.onFeatureAreaSelected(this);
    }

    @Override
    public String toString() {
        return "FeatureAreaSelectedEvent{" +
                "resetObjectSelection=" + resetObjectSelection +
                ", selectedAreaClicked=" + selectedAreaClicked +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
