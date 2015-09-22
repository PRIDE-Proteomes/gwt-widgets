package uk.ac.ebi.pride.widgets.client.protein.model;

import com.google.gwt.canvas.dom.client.Context2d;
import uk.ac.ebi.pride.widgets.client.protein.constants.Colors;
import uk.ac.ebi.pride.widgets.client.protein.utils.CanvasProperties;

@Deprecated
public class CoveredSequenceBorder extends SequenceRegion {

    public static final int BORDER = 1;

    private double xMin, xMax;
    private int yMin, yMax;

    public CoveredSequenceBorder(int start, int length, CanvasProperties canvasProperties) {
        super(start, canvasProperties);
        this.length = length;
        setBounds();
        yMin = CanvasProperties.Y_OFFSET;
        yMax = CanvasProperties.Y_OFFSET + CoveredSequenceRegion.BOXES_HEIGHT;
    }

    public void increaseLength(int length) {
        this.length += length;
        setBounds();
    }

    private void setBounds(){
        xMin = this.canvasProperties.getPixelFromPosition(getStart());
        xMax = this.canvasProperties.getPixelFromPosition(getStart() + getLength() + 1);
    }

    @Override
    public boolean isMouseOver(){
        return (xMin<=mouseX && xMax>=mouseX) && (mouseY<=yMax && mouseY>=yMin);
    }

    @Override
    public void draw(Context2d ctx) {
        if(isMouseOver())
            ctx.setStrokeStyle(Colors.SEQUENCE_BORDER_SELECTED_COLOR);
        else
            ctx.setStrokeStyle(Colors.SEQUENCE_BORDER_COLOR);
        ctx.beginPath();
        ctx.setLineWidth(BORDER);
        ctx.moveTo(xMin, yMin);
        ctx.lineTo(xMin, yMax);
        ctx.lineTo(xMax, yMax);
        ctx.lineTo(xMax, yMin);
        ctx.lineTo(xMin, yMin);
        ctx.closePath();
        ctx.stroke();

        //FLORIAN does not like the idea :(
        /*ctx.beginPath();
        ctx.setStrokeStyle(CssColor.make("rgba(100,100,100, 1)"));
        ctx.setLineWidth(4 * BORDER);
        ctx.moveTo(xMin + BORDER, 50 + 4 * BORDER);
        ctx.lineTo(xMax - BORDER, 50 + 4 * BORDER);
        ctx.closePath();
        ctx.stroke();*/
    }

    @Override
    public String toString() {
        return "CoveredSequenceBorder{" +
                "start=" + getStart() +
                ", length=" + length +
                '}';
    }
}
