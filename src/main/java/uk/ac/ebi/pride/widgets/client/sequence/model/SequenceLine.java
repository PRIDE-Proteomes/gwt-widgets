package uk.ac.ebi.pride.widgets.client.sequence.model;

import com.google.gwt.canvas.dom.client.Context2d;
import uk.ac.ebi.pride.widgets.client.common.Clickable;
import uk.ac.ebi.pride.widgets.client.common.Drawable;
import uk.ac.ebi.pride.widgets.client.sequence.data.Protein;
import uk.ac.ebi.pride.widgets.client.sequence.type.SequenceType;
import uk.ac.ebi.pride.widgets.client.sequence.utils.CanvasProperties;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class SequenceLine implements Drawable, DrawablePosition, DrawableSelection, Clickable {
    private List<PositionIdentification> positionIdentificationList = new LinkedList<PositionIdentification>();
    private List<PositionBlock> positionBlockList = new LinkedList<PositionBlock>();

    public SequenceLine(SequenceType sequenceType, int start, int end, Protein protein) {
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
            this.positionBlockList.add(new PositionBlock(sequenceType, blockStart, endAux, protein));
            blockStart = endAux;
        }

        if(sequenceType.isPositionShown()){
            canvasProperties.reserveHorizontalSpace(CanvasProperties.X_SPACE);
            positionIdentificationList.add(new PositionIdentification(sequenceType, blockStart));
        }
        canvasProperties.reserveHorizontalSpace(CanvasProperties.X_OFFSET);
    }

    public List<PositionIdentification> getPositionIdentificationList() {
        return positionIdentificationList;
    }

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
        //System.out.println("\t(" + start + ", " + end + ") -> " + protein.getSequence().substring(start, end));

        for (PositionIdentification positionIdentification : positionIdentificationList) {
            positionIdentification.draw(ctx);
        }

        for (PositionBlock positionBlock : positionBlockList) {
            positionBlock.draw(ctx);
        }
    }

    @Override
    public void drawSelection(Context2d ctx) {
        for (PositionBlock positionBlock : positionBlockList) {
            positionBlock.drawSelection(ctx);
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
