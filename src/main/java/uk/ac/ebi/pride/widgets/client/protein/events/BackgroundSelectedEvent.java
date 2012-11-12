package uk.ac.ebi.pride.widgets.client.protein.events;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.pride.widgets.client.protein.handlers.BackgroundSelectedHandler;
import uk.ac.ebi.pride.widgets.client.protein.model.Background;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("UnusedDeclaration")
public class BackgroundSelectedEvent extends GwtEvent<BackgroundSelectedHandler> {
    @SuppressWarnings("Convert2Diamond")
    public static Type<BackgroundSelectedHandler> TYPE = new Type<BackgroundSelectedHandler>();

    private boolean resetObjectSelection;
    private boolean selectedAreaClicked;
    private int start;
    private int end;

    public BackgroundSelectedEvent(boolean resetObjectSelection, Background background) {
        this.resetObjectSelection = resetObjectSelection;
        this.selectedAreaClicked = background.isSelected();
        this.start = background.getStart();
        this.end = background.getEnd();
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
    public Type<BackgroundSelectedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(BackgroundSelectedHandler handler) {
        handler.onBackgroundSelected(this);
    }

    @Override
    public String toString() {
        return "BackgroundSelectedEvent{" +
                "resetObjectSelection=" + resetObjectSelection +
                ", selectedAreaClicked=" + selectedAreaClicked +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
