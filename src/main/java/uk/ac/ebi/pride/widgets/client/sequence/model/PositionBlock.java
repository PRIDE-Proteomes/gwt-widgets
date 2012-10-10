package uk.ac.ebi.pride.widgets.client.sequence.model;

import com.google.gwt.canvas.dom.client.Context2d;
import uk.ac.ebi.pride.widgets.client.common.handler.PrideModificationHandler;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Clickable;
import uk.ac.ebi.pride.widgets.client.sequence.utils.CanvasProperties;
import uk.ac.ebi.pride.widgets.client.sequence.utils.CanvasSelection;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class PositionBlock implements DrawableLayers, Clickable {
    @SuppressWarnings("Convert2Diamond")
    private List<Position> positionList = new LinkedList<Position>();

    public PositionBlock(CanvasSelection canvasSelection, int start, int end, ProteinSummary proteinSummary) {
        for(int i=start; i<end; ++i){
            positionList.add(new Position(proteinSummary, canvasSelection, i));
        }
        CanvasProperties.getCanvasProperties().reserveHorizontalSpace(CanvasProperties.X_SPACE);
    }

    @Override
    public void setMousePosition(int mouseX, int mouseY) {
        for (Position position : positionList) {
            position.setMousePosition(mouseX,mouseY);
        }
    }

    @Override
    public void draw(Context2d ctx) {
        for (Position position : positionList) {
            position.draw(ctx);
        }
    }

    @Override
    public void drawPeptides(Context2d ctx) {
        for (Position position : positionList) {
            position.drawPeptides(ctx);
        }
    }

    @Override
    public void drawSelection(Context2d ctx) {
        for (Position position : positionList) {
            position.drawSelection(ctx);
        }
    }

    @Override
    public void drawModification(Context2d ctx, PrideModificationHandler prideModification) {
        for (Position position : positionList) {
            position.drawModification(ctx, prideModification);
        }
    }

    @Override
    public void drawPosition(Context2d ctx) {
        for (Position position : positionList) {
            position.drawPosition(ctx);
        }
    }

    @Override
    public void onMouseUp(int mouseX, int mouseY) {
        for(Position position : positionList){
            position.onMouseUp(mouseX, mouseY);
        }
    }

    @Override
    public void onMouseDown(int mouseX, int mouseY) {
        for(Position position : positionList){
            position.onMouseDown(mouseX, mouseY);
        }
    }
}
