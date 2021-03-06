package uk.ac.ebi.pride.widgets.client.feature.model;

import com.google.gwt.canvas.dom.client.Context2d;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Drawable;
import uk.ac.ebi.pride.widgets.client.feature.constants.Colors;
import uk.ac.ebi.pride.widgets.client.feature.utils.FeatureCanvasProperties;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class FeatureBorder implements Drawable {

    private double xMin, xMax, yMin, yMax;
    private double xMinAux, xMaxAux;
    // mouse positions relative to canvas
    int mouseX, mouseY;

    public FeatureBorder(FeatureCanvasProperties featureCanvasProperties) {
        int length = featureCanvasProperties.getProteinLength();
        this.xMin = featureCanvasProperties.getPixelFromPosition(0);
        this.xMax = featureCanvasProperties.getPixelFromPosition(length);
        this.yMin = FeatureCanvasProperties.Y_OFFSET;
        this.yMax = FeatureCanvasProperties.Y_OFFSET + CoveredFeatureRegion.BOXES_HEIGHT;

        this.xMinAux = featureCanvasProperties.getPixelFromPosition(1);
        this.xMaxAux = featureCanvasProperties.getPixelFromPosition(length);
    }

    public boolean isMouseOver(){
        return (xMinAux<=mouseX && xMaxAux>=mouseX) && (mouseY<=yMax && mouseY>=yMin);
    }

    @Override
    public void setMousePosition(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    @Override
    public void draw(Context2d ctx) {
        if(isMouseOver())
            ctx.setStrokeStyle(Colors.FEATURE_BORDER_SELECTED_COLOR);
        else
            ctx.setStrokeStyle(Colors.FEATURE_BORDER_COLOR);

        ctx.beginPath();
        ctx.moveTo(xMin, yMin);
        ctx.lineTo(xMin, yMax);
        ctx.lineTo(xMax, yMax);
        ctx.lineTo(xMax, yMin);
        ctx.lineTo(xMin, yMin);
        ctx.closePath();
        ctx.stroke();
    }
}
