package uk.ac.ebi.pride.widgets.client.protein.model;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.canvas.dom.client.FillStrokeStyle;

public class CoveredSequenceBorder extends SequenceRegion {
    public static final int BORDER = 1;

    private double xMin, xMax;
    private int yMin, yMax;

    public CoveredSequenceBorder(int start, int length, ProteinAxis pa) {
        super(start, pa);
        this.length = length;
        setBounds();
        yMin = pa.Y_OFFSET;
        yMax = pa.Y_OFFSET + pa.BOXES_HEIGHT;
    }

    public void increaseLength(int length) {
        this.length += length;
        setBounds();
    }

    private void setBounds(){
        xMin = getPixelFromValue(getStart());
        xMax = getPixelFromValue(getStart() + getLength());
    }

    @Override
    public boolean isMouseOver(){
        return (xMin<=mouseX && xMax>=mouseX) && (mouseY<=yMax && mouseY>=yMin);
    }

    @Override
    public void draw(Context2d ctx) {
        FillStrokeStyle s =  ctx.getFillStyle();

        if(isMouseOver())
            ctx.setStrokeStyle(CssColor.make("rgba(0,0,255, 1)"));
        else
            ctx.setStrokeStyle(CssColor.make("rgba(0,0,0, 1)"));
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

        ctx.setFillStyle(s);
    }

    @Override
    public String toString() {
        return "CoveredSequenceBorder{" +
                "start=" + getStart() +
                ", length=" + length +
                '}';
    }
}
