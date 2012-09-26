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
public class PositionBlock implements Drawable, DrawablePosition, DrawableSelection, Clickable {
    private static final int SPACE_WITHIN_BLOCK = 10;
    private SequenceType sequenceType;
    private Protein protein;
    private int start;
    private int end;
    private int length;

    private List<Position> positionList = new LinkedList<Position>();

    public PositionBlock(SequenceType sequenceType,int start, int end, Protein protein) {
        this.sequenceType = sequenceType;
        this.protein = protein;
        this.start = start;
        this.end = end;
        this.length = end - start;

        for(int i=this.start; i<this.end; ++i){
            positionList.add(new Position(protein, i));
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
    public void drawSelection(Context2d ctx) {
        for (Position position : positionList) {
            position.drawSelection(ctx);
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
