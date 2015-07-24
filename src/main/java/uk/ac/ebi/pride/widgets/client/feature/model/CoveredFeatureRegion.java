package uk.ac.ebi.pride.widgets.client.feature.model;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Animated;
import uk.ac.ebi.pride.widgets.client.common.utils.AnimationUtils;
import uk.ac.ebi.pride.widgets.client.feature.utils.FeatureCanvasProperties;

/**
 * @author ntoro
 * @since 21/07/15 16:30
 */
public class CoveredFeatureRegion extends FeatureRegion implements Animated {

    public static final int BOXES_HEIGHT = 25;
    public static final CssColor FEATURE_COLOR = CssColor.make("#8a89a6"); // pink
//    public static final CssColor REGION_SELECTED_COLOR = CssColor.make("rgba(255,255,0, .5)");


//    private boolean fireEvent = false;
    private double xMin, xMax, width;
    private int yMin, yMax, height;
    private CssColor regionColor;

    public CoveredFeatureRegion(int start, FeatureCanvasProperties featureCanvasProperties) {
        super(start, featureCanvasProperties);
        setBounds();
        this.yMin = FeatureCanvasProperties.Y_OFFSET;
        this.yMax = FeatureCanvasProperties.Y_OFFSET + BOXES_HEIGHT;
        this.height = yMax - yMin;
        this.regionColor = FEATURE_COLOR;
    }

    @Override
    public void increaseLength() {
        super.increaseLength();
        setBounds();
    }

    private void setBounds(){
        xMin = Math.floor(this.featureCanvasProperties.getPixelFromPosition(getStart()));
        xMax = Math.floor(this.featureCanvasProperties.getPixelFromPosition(getStart() + getLength()));
        width = xMax - xMin;
    }

    @Override
    public boolean isMouseOver(){
        return (xMin<=mouseX && mouseX<xMax) && (yMax>=mouseY && mouseY>=yMin);
    }

    @Override
    public void draw(Context2d context) {
        context.setFillStyle(regionColor);
        context.fillRect(xMin, yMin, width, height);

        if(isMouseOver()){
            showRegionTooltip(context);
        }

    }

    @Override
    public void drawAnimation(Context2d ctx, double progress) {
        progress = AnimationUtils.getProgress(0.25, 0.50, progress);
        if(progress==0) return;
        double aux = yMin + (BOXES_HEIGHT / 2) * (1-progress);
        ctx.setFillStyle(regionColor);
        ctx.fillRect(xMin, aux, width, height * progress);
    }


    private void showRegionTooltip(Context2d ctx){
        int offset = 5;
        String str = "Region";
        int width = str.length() * 5;

        int posX;
        int boxWidth = width + 2 * offset;
        if(mouseX > (ctx.getCanvas().getWidth() - boxWidth - FeatureCanvasProperties.X_OFFSET)){
            posX = ctx.getCanvas().getWidth() - boxWidth - FeatureCanvasProperties.X_OFFSET;
        }else if(mouseX < boxWidth / 2 + FeatureCanvasProperties.X_OFFSET){
            posX = FeatureCanvasProperties.X_OFFSET;
        }else{
            posX = mouseX - (width / 2);
        }

        roundRect(ctx, posX, 0, boxWidth, 15, 5);
        ctx.setFillStyle("#3F3F3F");
        ctx.setFont("bold 10px verdana");
        ctx.fillText(str, posX + offset , 10, width);
    }

    private void roundRect(Context2d ctx, int x, int y, int width, int height, int radius) {
        ctx.beginPath();
        ctx.moveTo(x + radius, y);
        ctx.lineTo(x + width - radius, y);
        ctx.quadraticCurveTo(x + width, y, x + width, y + radius);
        ctx.lineTo(x + width, y + height - radius);
        ctx.quadraticCurveTo(x + width, y + height, x + width - radius, y + height);
        ctx.lineTo(x + radius, y + height);
        ctx.quadraticCurveTo(x, y + height, x, y + height - radius);
        ctx.lineTo(x, y + radius);
        ctx.quadraticCurveTo(x, y, x + radius, y);
        ctx.closePath();
        ctx.setFillStyle("#FFFFFF");
        ctx.fill();
        ctx.setStrokeStyle("#000000");
        ctx.stroke();
    }


}
