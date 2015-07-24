package uk.ac.ebi.pride.widgets.client.protein.model;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Drawable;
import uk.ac.ebi.pride.widgets.client.protein.utils.CanvasProperties;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class ProteinBorder implements Drawable {
    public static final CssColor BORDER_COLOR = CssColor.make("rgba(0,0,0, 1)");
    public static final CssColor BORDER_SELECTED_COLOR = CssColor.make("rgba(0,0,255, 1)");

    private double xMin, xMax, yMin, yMax;
    private double xMinAux, xMaxAux;
    // mouse positions relative to canvas
    int mouseX, mouseY;

    public ProteinBorder(CanvasProperties canvasProperties) {
        int length = canvasProperties.getProteinLength();
        this.xMin = canvasProperties.getPixelFromPosition(0);
        this.xMax = canvasProperties.getPixelFromPosition(length);
        this.yMin = CanvasProperties.Y_OFFSET;
        this.yMax = CanvasProperties.Y_OFFSET + CoveredSequenceRegion.BOXES_HEIGHT;

        this.xMinAux = canvasProperties.getPixelFromPosition(1);
        this.xMaxAux = canvasProperties.getPixelFromPosition(length);
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
            ctx.setStrokeStyle(BORDER_SELECTED_COLOR);
        else
            ctx.setStrokeStyle(BORDER_COLOR);

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
