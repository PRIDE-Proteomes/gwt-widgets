package uk.ac.ebi.pride.widgets.client.protein.model;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Drawable;
import uk.ac.ebi.pride.widgets.client.protein.utils.CanvasProperties;

public class ProteinAxis implements Drawable {
    private static final double SEGMENT_WIDTH = 1;
    public static final double SEGMENT_Y = 25;
    private static final double SEGMENT_TICK_HEIGHT = 5;

    private boolean proteinBorder;
    private double segmentY;
    private double xMin, xMax, width;

    public ProteinAxis(CanvasProperties canvasProperties, boolean proteinBorder) {
        this.proteinBorder = proteinBorder;
        int length = canvasProperties.getProteinLength();
        this.segmentY = SEGMENT_Y + CanvasProperties.Y_OFFSET ;
        this.xMin = canvasProperties.getPixelFromPosition(1);
        this.xMax = canvasProperties.getPixelFromPosition(length);
        this.width = xMax - xMin;
    }

    @Override
    public void setMousePosition(int mouseX, int mouseY) {
        //Nothing here
    }

    @Override
    public void draw(Context2d ctx) {
        ctx.setFillStyle(CssColor.make("rgba(255,255,255, 1)"));
        ctx.fillRect(xMin, CanvasProperties.Y_OFFSET, width, CoveredSequenceRegion.BOXES_HEIGHT);

        ctx.setStrokeStyle(CssColor.make("rgba(89,89,89, 1)"));
        ctx.setLineWidth(SEGMENT_WIDTH);

        ctx.beginPath();
        ctx.moveTo(xMin, segmentY);
        ctx.lineTo(xMax, segmentY);
        ctx.closePath();
        ctx.stroke();

        if(!this.proteinBorder){
            ctx.beginPath();
            ctx.moveTo(xMin, segmentY - SEGMENT_TICK_HEIGHT);
            ctx.lineTo(xMin, segmentY + SEGMENT_TICK_HEIGHT);
            ctx.closePath();
            ctx.stroke();

            ctx.beginPath();
            ctx.moveTo(xMax, segmentY - SEGMENT_TICK_HEIGHT);
            ctx.lineTo(xMax, segmentY + SEGMENT_TICK_HEIGHT);
            ctx.closePath();
            ctx.stroke();
        }
    }
}
