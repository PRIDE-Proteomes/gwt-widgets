package uk.ac.ebi.pride.widgets.client.feature.model;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Animated;
import uk.ac.ebi.pride.widgets.client.common.utils.AnimationUtils;
import uk.ac.ebi.pride.widgets.client.common.utils.Tooltip;
import uk.ac.ebi.pride.widgets.client.feature.constants.Colors;
import uk.ac.ebi.pride.widgets.client.feature.events.FeatureRegionSelectionEvent;
import uk.ac.ebi.pride.widgets.client.feature.interfaces.Clickable;
import uk.ac.ebi.pride.widgets.client.feature.utils.FeatureCanvasProperties;
import uk.ac.ebi.pride.widgets.client.feature.utils.FeatureType;

/**
 * @author ntoro
 * @since 21/07/15 16:30
 */
public class CoveredFeatureRegion extends FeatureRegion implements Animated, Clickable {

    public static final int BOXES_HEIGHT = 10;
    public static final int RADIUS = 5;

    private final CssColor regionColor;

    private boolean fireEvent = false;
    private long xMin, xMax, width;
    private long yMin, yMax, height;
    private Tooltip tooltip = new Tooltip();

    public CoveredFeatureRegion(int start, int end, String tooltipMessage, String featureType, FeatureCanvasProperties featureCanvasProperties) {
        super(start, end, tooltipMessage, featureType, featureCanvasProperties);
        setBounds();
        this.yMin = FeatureCanvasProperties.Y_OFFSET;
        this.yMax = FeatureCanvasProperties.Y_OFFSET + BOXES_HEIGHT;
        this.regionColor = FeatureType.valueOf(featureType).getCssColor();
        this.height = yMax - yMin;
    }

    private void setBounds(){
        xMin = Math.round(this.featureCanvasProperties.getPixelFromPosition(getStart()));
        xMax = Math.round(this.featureCanvasProperties.getPixelFromPosition(getStart() + getLength()));
        width = xMax - xMin;
    }

    public void onMouseUp(int mouseX, int mouseY) {
        setMousePosition(mouseX, mouseY);
        this.selected = this.fireEvent = isMouseOver();
    }

    @Override
    public void onMouseDown(int mouseX, int mouseY) {
        setMousePosition(mouseX, mouseY);
    }

    @Override
    public boolean isSelected() {
        return this.selected;
    }

    @Override
    public boolean isMouseOver(){
        return (xMin<=mouseX && mouseX<xMax) && (yMax>=mouseY && mouseY>=yMin);
    }

    @Override
    public void draw(Context2d ctx) {
        roundRect(ctx, xMin, yMin, width, height, RADIUS);
        ctx.setFillStyle(regionColor);
        ctx.fill();

        if(isMouseOver()){
            this.tooltip.show(ctx.getCanvas(), (int) xMax, (int) yMax, getTooltipMessage());
            this.tooltip.setAnimationEnabled(false);
        }else{
            this.tooltip.hide();
            this.tooltip.setAnimationEnabled(true);
        }

        if(isMouseOver() || selected){
            roundRect(ctx, xMin, yMin, width, height, RADIUS);
            ctx.setStrokeStyle(Colors.FEATURE_SELECTED_COLOR);
            ctx.stroke();
            ctx.setFillStyle(Colors.FEATURE_SELECTED_COLOR);
            ctx.fill();
        }

    }

    @Override
    public void drawAnimation(Context2d ctx, double progress) {
        progress = AnimationUtils.getProgress(0.25, 0.50, progress);
        if(progress==0) return;
        double aux = yMin + (BOXES_HEIGHT / 2) * (1-progress);
        roundRect(ctx, xMin, (int)aux, width, (int)(height * progress), RADIUS);
        ctx.setFillStyle(regionColor);
        ctx.fill();
    }


    private void roundRect(Context2d ctx, long x, long y, long width, long height, int radius) {
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
    }

    @Override
    public void fireSelectionEvent() {
        if(this.fireEvent){
            this.fireEvent = false;
            handlerManager.fireEvent(new FeatureRegionSelectionEvent(getStart(), getLength()));
        }
    }

}
