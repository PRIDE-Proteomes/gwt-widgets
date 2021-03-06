package uk.ac.ebi.pride.widgets.client.feature.model;

import com.google.gwt.canvas.dom.client.Context2d;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Animated;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Drawable;
import uk.ac.ebi.pride.widgets.client.common.utils.AnimationUtils;
import uk.ac.ebi.pride.widgets.client.feature.constants.Colors;
import uk.ac.ebi.pride.widgets.client.feature.utils.FeatureCanvasProperties;

public class FeatureAxis implements Drawable, Animated {

    private static final double SEGMENT_WIDTH = 1;
    private static final double SEGMENT_TICK_HEIGHT = 5;

    private boolean featureBorder;
    private double segmentY;
    private double xMin, xMax;

    //related to the white area under the protein coverage
    private double areaXMin, areaWidth;

    public FeatureAxis(FeatureCanvasProperties featureCanvasProperties, boolean featureBorder) {
        this.featureBorder = featureBorder;
        int length = featureCanvasProperties.getProteinLength();
        this.segmentY = CoveredFeatureRegion.BOXES_HEIGHT/2 + FeatureCanvasProperties.Y_OFFSET ;
        this.xMin = featureCanvasProperties.getPixelFromPosition(0);
        this.xMax = featureCanvasProperties.getPixelFromPosition(length);
        this.areaXMin = featureCanvasProperties.getPixelFromPosition(0);
        double borderXMax = featureCanvasProperties.getPixelFromPosition(length);
        this.areaWidth = borderXMax - this.areaXMin ;
    }

    @Override
    public void setMousePosition(int mouseX, int mouseY) {
        //Nothing here
    }

    @Override
    public void draw(Context2d ctx) {
        ctx.setFillStyle(Colors.FEATURE_REGION_BACKGROUND_COLOR);
        ctx.fillRect(this.areaXMin, FeatureCanvasProperties.Y_OFFSET, this.areaWidth, CoveredFeatureRegion.BOXES_HEIGHT);

        ctx.setStrokeStyle(Colors.FEATURE_LINE_COLOR);
        ctx.setLineWidth(SEGMENT_WIDTH);

        ctx.beginPath();
        ctx.moveTo(xMin, segmentY);
        ctx.lineTo(xMax, segmentY);
        ctx.closePath();
        ctx.stroke();

        if(!this.featureBorder){
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

        ctx.setFillStyle(Colors.FEATURE_REGION_BACKGROUND_COLOR);
        double aux = FeatureCanvasProperties.Y_OFFSET + (CoveredFeatureRegion.BOXES_HEIGHT / 2) * (1-progress);
        ctx.fillRect(this.areaXMin, aux, this.areaWidth, CoveredFeatureRegion.BOXES_HEIGHT * progress);

        ctx.setStrokeStyle(Colors.FEATURE_LINE_COLOR);
        ctx.setLineWidth(SEGMENT_WIDTH);

        ctx.beginPath();
        ctx.moveTo(xMin, segmentY);
        ctx.lineTo(xMax * progress, segmentY);
        ctx.closePath();
        ctx.stroke();
    }
}
