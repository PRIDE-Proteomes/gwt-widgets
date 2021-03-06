package uk.ac.ebi.pride.widgets.client.protein.model;

import com.google.gwt.canvas.dom.client.Context2d;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Animated;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Drawable;
import uk.ac.ebi.pride.widgets.client.common.utils.AnimationUtils;
import uk.ac.ebi.pride.widgets.client.protein.constants.Colors;
import uk.ac.ebi.pride.widgets.client.protein.utils.CanvasProperties;

public class ProteinAxis implements Drawable, Animated {

    private static final double SEGMENT_WIDTH = 1;
    public static final double SEGMENT_Y = 25;
    private static final double SEGMENT_TICK_HEIGHT = 5;

    private boolean proteinBorder;
    private double segmentY;
    private double xMin, xMax;

    //related to the white area under the protein coverage
    private double areaXMin, areaWidth;

    public ProteinAxis(CanvasProperties canvasProperties, boolean proteinBorder) {
        this.proteinBorder = proteinBorder;
        int length = canvasProperties.getProteinLength();
        this.segmentY = SEGMENT_Y + CanvasProperties.Y_OFFSET ;
        this.xMin = canvasProperties.getPixelFromPosition(0);
        this.xMax = canvasProperties.getPixelFromPosition(length);
        this.areaXMin = canvasProperties.getPixelFromPosition(0);
        double borderXMax = canvasProperties.getPixelFromPosition(length);
        this.areaWidth = borderXMax - this.areaXMin;
    }

    @Override
    public void setMousePosition(int mouseX, int mouseY) {
        //Nothing here
    }

    @Override
    public void draw(Context2d ctx) {
        ctx.setFillStyle(Colors.PROTEIN_REGION_BACKGROUND_COLOR);
        ctx.fillRect(this.areaXMin, CanvasProperties.Y_OFFSET, this.areaWidth, CoveredSequenceRegion.BOXES_HEIGHT);

        ctx.setStrokeStyle(Colors.PROTEIN_LINE_COLOR);
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

    @Override
    public void drawAnimation(Context2d ctx, double progress) {
        progress = AnimationUtils.getProgress(0, 0.25, progress);

        ctx.setFillStyle(Colors.PROTEIN_REGION_BACKGROUND_COLOR);
        double aux = CanvasProperties.Y_OFFSET + (CoveredSequenceRegion.BOXES_HEIGHT / 2) * (1-progress);
        ctx.fillRect(this.areaXMin, aux, this.areaWidth, CoveredSequenceRegion.BOXES_HEIGHT * progress);

        ctx.setStrokeStyle(Colors.PROTEIN_LINE_COLOR);
        ctx.setLineWidth(SEGMENT_WIDTH);

        ctx.beginPath();
        ctx.moveTo(xMin, segmentY);
        ctx.lineTo(xMax * progress, segmentY);
        ctx.closePath();
        ctx.stroke();
    }
}
