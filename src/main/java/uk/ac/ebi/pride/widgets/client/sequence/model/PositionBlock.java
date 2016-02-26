package uk.ac.ebi.pride.widgets.client.sequence.model;

import com.google.gwt.canvas.dom.client.Context2d;
import uk.ac.ebi.pride.widgets.client.common.handler.PeptideHandler;
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

    public void setVisiblePeptide(PeptideHandler peptide){
        for (Position position : positionList) {
            position.setVisiblePeptide(peptide);
        }
    }

    public void setHighlightedPeptide(PeptideHandler peptide){
        for (Position position : positionList) {
            position.setHighlightedPeptide(peptide);
        }
    }

    public void setHighlightedModification(PrideModificationHandler modification, int regionStart, int regionEnd){
        for (Position position : positionList) {
            position.setHighlightedModification(modification, regionStart, regionEnd);
        }
    }

    public void setHighlightedModification(PrideModificationHandler modification){
        for (Position position : positionList) {
            position.setHighlightedModification(modification);
        }
    }
    public void setHighlightedModification(int modPosition){
        for (Position position : positionList) {
            position.setHighlightedModification(modPosition);
        }
    }

    public void setSelectedModification(int modPosition) {
        for (Position position : positionList) {
            position.setSelectedModification(modPosition);
        }
    }

    public void resetPeptidesFilter(){
        for (Position position : positionList) {
            position.resetPeptidesFilter();
        }
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
    public void drawModifications(Context2d ctx) {
        for (Position position : positionList) {
            position.drawModifications(ctx);
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

    @Override
    public boolean isMouseOver() {
        return false;
    }

    @Override
    public boolean isSelected() {
        boolean isSelected = false;
        for(Position position : positionList){
            isSelected = isSelected || position.isSelected();
        }
        return isSelected;
    }
}
