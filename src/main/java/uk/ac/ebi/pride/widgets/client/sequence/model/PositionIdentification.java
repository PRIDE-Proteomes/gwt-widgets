package uk.ac.ebi.pride.widgets.client.sequence.model;

import com.google.gwt.canvas.dom.client.Context2d;
import uk.ac.ebi.pride.widgets.client.common.handler.PrideModificationHandler;
import uk.ac.ebi.pride.widgets.client.sequence.type.SequenceType;
import uk.ac.ebi.pride.widgets.client.sequence.utils.CanvasProperties;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 *
 * //TODO The width is an issue that needs to be fixed in this class
 */
public class PositionIdentification implements DrawableLayers {
    //private SequenceType sequenceType;
    //private int position;
    private String text;
    private int x, y, width;

    public PositionIdentification(SequenceType sequenceType, int position) {
        //this.sequenceType = sequenceType;
        //this.position = position;
        this.text = sequenceType.getFormattedPositionNumber(position);
        this.width = text.length() * 6;

        CanvasProperties canvasProperties = CanvasProperties.getCanvasProperties();
        this.x = canvasProperties.getNextPosX();
        this.y = canvasProperties.getNextPosY();
        canvasProperties.reserveHorizontalSpace(width);
    }

    @Override
    public void setMousePosition(int mouseX, int mouseY) {
        //Nothing here
    }

    @Override
    public void draw(Context2d ctx) {
        ctx.fillText(text, x, y-2);
    }


    @Override
    public void drawPeptides(Context2d ctx) {
        //Nothing here
    }

    @Override
    public void drawSelection(Context2d ctx) {
        ctx.clearRect(x, y-50, width + 10, 50);
    }

    @Override
    public void drawModification(Context2d ctx, PrideModificationHandler prideModification) {
        //Nothing here
    }

    @Override
    public void drawPosition(Context2d ctx) {
        //Nothing here
    }
}
