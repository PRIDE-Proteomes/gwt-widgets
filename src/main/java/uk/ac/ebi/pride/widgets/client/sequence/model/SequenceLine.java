package uk.ac.ebi.pride.widgets.client.sequence.model;

import com.google.gwt.canvas.dom.client.Context2d;
import uk.ac.ebi.pride.widgets.client.common.handler.PrideModificationHandler;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Clickable;
import uk.ac.ebi.pride.widgets.client.sequence.type.SequenceType;
import uk.ac.ebi.pride.widgets.client.sequence.utils.CanvasProperties;
import uk.ac.ebi.pride.widgets.client.sequence.utils.CanvasSelection;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("Convert2Diamond")
public class SequenceLine implements DrawableLayers, Clickable {
    private List<PositionIdentification> positionIdentificationList = new LinkedList<PositionIdentification>();
    private List<PositionBlock> positionBlockList = new LinkedList<PositionBlock>();

    public SequenceLine(SequenceType sequenceType, CanvasSelection canvasSelection, int start, int end, ProteinSummary proteinSummary) {
        CanvasProperties canvasProperties = CanvasProperties.getCanvasProperties();
        int blockStart = start;
        if(sequenceType.isPositionShown()){
            positionIdentificationList.add(new PositionIdentification(sequenceType, blockStart + 1));
            canvasProperties.reserveHorizontalSpace(CanvasProperties.X_SPACE * 2);
        }

        int length = end - start;
        int numberOfBlocks =(int) Math.ceil(length / (double) sequenceType.getBlockSize());
        for(int i=1; i<=numberOfBlocks; ++i){
            int aux = blockStart + sequenceType.getBlockSize();
            int endAux = (aux < end ? aux : end);
            this.positionBlockList.add(new PositionBlock(canvasSelection, blockStart, endAux, proteinSummary));
            blockStart = endAux;
        }

        boolean isFullLine = (length == (sequenceType.getNumOfBlocks() * sequenceType.getBlockSize()));
        if(sequenceType.isPositionShown() && isFullLine){
            canvasProperties.reserveHorizontalSpace(CanvasProperties.X_SPACE);
            positionIdentificationList.add(new PositionIdentification(sequenceType, blockStart));
        }
        canvasProperties.reserveHorizontalSpace(CanvasProperties.X_OFFSET);
    }

    @SuppressWarnings("UnusedDeclaration")
    public List<PositionIdentification> getPositionIdentificationList() {
        return positionIdentificationList;
    }

    @SuppressWarnings("UnusedDeclaration")
    public List<PositionBlock> getPositionBlockList() {
        return positionBlockList;
    }

    @Override
    public void setMousePosition(int mouseX, int mouseY) {
        for (PositionBlock positionBlock : positionBlockList) {
            positionBlock.setMousePosition(mouseX, mouseY);
        }
    }

    @Override
    public void draw(Context2d ctx) {
        ctx.setTextAlign(Context2d.TextAlign.LEFT);
        for (PositionIdentification positionIdentification : positionIdentificationList) {
            positionIdentification.draw(ctx);
        }

        ctx.setTextAlign(Context2d.TextAlign.CENTER);
        for (PositionBlock positionBlock : positionBlockList) {
            positionBlock.draw(ctx);
        }
    }

    @Override
    public void drawPeptides(Context2d ctx) {
        for (PositionBlock positionBlock : positionBlockList) {
            positionBlock.drawPeptides(ctx);
        }
    }

    @Override
    public void drawSelection(Context2d ctx) {
        for (PositionBlock positionBlock : positionBlockList) {
            positionBlock.drawSelection(ctx);
        }
    }

    @Override
    public void drawModification(Context2d ctx, PrideModificationHandler prideModification) {
        for (PositionBlock positionBlock : positionBlockList) {
            positionBlock.drawModification(ctx, prideModification);
        }
    }

    @Override
    public void drawPosition(Context2d ctx) {
        for (PositionBlock positionBlock : positionBlockList) {
            positionBlock.drawPosition(ctx);
        }
    }

    @Override
    public void onMouseUp(int mouseX, int mouseY) {
        for (PositionBlock positionBlock : positionBlockList) {
            positionBlock.onMouseUp(mouseX, mouseY);
        }
    }

    @Override
    public void onMouseDown(int mouseX, int mouseY) {
        for (PositionBlock positionBlock : positionBlockList) {
            positionBlock.onMouseDown(mouseX, mouseY);
        }
    }
}
