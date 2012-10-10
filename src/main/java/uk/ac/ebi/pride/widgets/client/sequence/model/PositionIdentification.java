package uk.ac.ebi.pride.widgets.client.sequence.model;

import com.google.gwt.canvas.dom.client.Context2d;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Drawable;
import uk.ac.ebi.pride.widgets.client.sequence.type.SequenceType;
import uk.ac.ebi.pride.widgets.client.sequence.utils.CanvasProperties;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class PositionIdentification implements Drawable {
    private SequenceType sequenceType;
    private int position;
    private String text;
    private int x, y, width;

    public PositionIdentification(SequenceType sequenceType, int position) {
        this.sequenceType = sequenceType;
        this.position = position;
        this.text = sequenceType.getFormattedPositionNumber(position);
        this.width = text.length() * 6;

        CanvasProperties canvasProperties = CanvasProperties.getCanvasProperties();
        this.x = canvasProperties.getNextPosX();
        this.y = canvasProperties.getNextPosY();
        canvasProperties.reserveHorizontalSpace(width);
    }

    @Override
    public void setMousePosition(int mouseX, int mouseY) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void draw(Context2d ctx) {
        ctx.fillText(text, x, y-2);
    }
}
