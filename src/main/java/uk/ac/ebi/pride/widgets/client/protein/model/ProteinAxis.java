package uk.ac.ebi.pride.widgets.client.protein.model;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.canvas.dom.client.FillStrokeStyle;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Drawable;
import uk.ac.ebi.pride.widgets.client.protein.utils.CanvasProperties;

public class ProteinAxis implements Drawable {

    private static final double SEGMENT_WIDTH = 1;
    public static final double SEGMENT_Y = 25;
    private static final double SEGMENT_TICK_HEIGHT = 5;
    public static final int BOXES_HEIGHT = 50;

    public static final int X_OFFSET = 4;
    public static final int Y_OFFSET = 20;

    private double segmentY;
    private double xMin, xMax;

    public ProteinAxis(CanvasProperties canvasProperties) {
        int length = canvasProperties.getProteinLength();
        this.segmentY = SEGMENT_Y + Y_OFFSET ;
        this.xMin = canvasProperties.getPixelFromValue(0);
        this.xMax = canvasProperties.getPixelFromValue(length);
    }

    @Override
    public void setMousePosition(int mouseX, int mouseY) {
        //Nothing here
    }

    @Override
    public void draw(Context2d ctx) {
        FillStrokeStyle s =  ctx.getFillStyle();

        ctx.setStrokeStyle(CssColor.make("rgba(0,0,0, 1)"));
        ctx.setLineWidth(SEGMENT_WIDTH);

        ctx.beginPath();
        ctx.moveTo(xMin, segmentY);
        ctx.lineTo(xMax, segmentY);
        ctx.closePath();
        ctx.stroke();

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

        ctx.setFillStyle(s);
    }
}
