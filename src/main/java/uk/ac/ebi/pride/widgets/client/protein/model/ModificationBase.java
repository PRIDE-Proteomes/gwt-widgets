package uk.ac.ebi.pride.widgets.client.protein.model;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import uk.ac.ebi.pride.widgets.client.common.handler.PrideModificationHandler;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Clickable;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Drawable;
import uk.ac.ebi.pride.widgets.client.protein.utils.CanvasProperties;

import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class ModificationBase implements Clickable, Drawable {
    public static final int MODIFICATION_HEIGHT = 6;
    public static final int MODIFICATION_TICK_WIDTH = 8;
    public static final CssColor MODIFICATION_COLOR = CssColor.make("rgba(255,0,0, 1)");

    private List<PrideModificationHandler> modification;
    private double xMin;
    private double yMin, yMax;

    public ModificationBase(int position, List<PrideModificationHandler> modification, CanvasProperties canvasProperties) {
        this.modification = modification;
        yMin = CanvasProperties.Y_OFFSET + CoveredSequenceRegion.BOXES_HEIGHT;
        yMax = yMin + MODIFICATION_HEIGHT;

        xMin = canvasProperties.getPixelFromValue(position);
    }

    @Override
    public void onMouseUp(int mouseX, int mouseY) {

    }

    @Override
    public void onMouseDown(int mouseX, int mouseY) {

    }

    @Override
    public void setMousePosition(int mouseX, int mouseY) {

    }

    @Override
    public void draw(Context2d ctx) {
        ctx.setFillStyle(MODIFICATION_COLOR);

        ctx.beginPath();
        ctx.moveTo(xMin,yMin);
        ctx.lineTo(xMin - MODIFICATION_TICK_WIDTH / 2, yMax);
        ctx.lineTo(xMin + MODIFICATION_TICK_WIDTH / 2, yMax);
        ctx.fill();
    }
}
