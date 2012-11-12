package uk.ac.ebi.pride.widgets.client.protein.model;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.event.shared.HandlerManager;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Clickable;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Drawable;
import uk.ac.ebi.pride.widgets.client.protein.events.BackgroundHighlightEvent;
import uk.ac.ebi.pride.widgets.client.protein.utils.CanvasProperties;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class Background implements Drawable, Clickable {
    public static final CssColor SELECTED_COLOR = CssColor.make("rgba(255,255,0, 0.25)");

    protected HandlerManager handlerManager;
    private CanvasProperties canvasProperties;

    private int start, end;
    private double xMin, width;

    // mouse positions relative to canvas
    private int mouseX;
    private boolean mouseDown;
    private boolean selecting;

    public Background(CanvasProperties canvasProperties) {
        this.canvasProperties = canvasProperties;
        this.width = 0;
        this.mouseDown = false;
        this.selecting = false;
    }

    public void setHandlerManager(HandlerManager handlerManager) {
        this.handlerManager = handlerManager;
    }

    public void setSelectedArea(int start, int end){
        this.start = start;
        this.end = end;
        this.xMin = this.canvasProperties.getPixelFromPosition(this.start);
        double xMax = this.canvasProperties.getPixelFromPosition(this.end + 1);
        this.width = xMax - xMin;
    }

    private void setSelectedArea(double xMin, double xMax){
        this.xMin = xMin;
        this.start = this.canvasProperties.getPositionFromPixel(this.xMin);
        this.end = this.canvasProperties.getPositionFromPixel(xMax);
        this.width = xMax - this.xMin;

    }

    public int getStart() {
        return start < end ? start : end;
    }

    public int getEnd() {
        return end > start ? end : start;
    }

    public void resetSelectedArea(){
        this.width = 0;
    }

    public boolean isMouseOver(){
        double xMax = this.xMin + this.width;
        return this.xMin<this.mouseX && this.mouseX<xMax;
    }

    @Override
    public boolean isSelected() {
        return hasSelection() && isMouseOver();
    }

    public boolean hasSelection(){
        return this.width != 0;
    }

    public boolean selectionInProgress() {
        return this.mouseDown && this.selecting;
    }

    @Override
    public void onMouseDown(int mouseX, int mouseY) {
        this.mouseDown = true;
        this.mouseX = mouseX;
        this.selecting = !hasSelection() || !isMouseOver();
        if(this.selecting){
            this.xMin = mouseX;
            this.width = 0;
        }
    }

    @Override
    public void onMouseUp(int mouseX, int mouseY) {
        this.mouseDown = false;
        this.selecting = false;
        this.mouseX = mouseX;
    }

    @Override
    public void setMousePosition(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        if(selectionInProgress()){
            setSelectedArea(this.xMin, (double) mouseX);
            if(this.hasSelection()){
                //System.out.println(new BackgroundHighlightEvent(this).toString());
                handlerManager.fireEvent(new BackgroundHighlightEvent(this));
            }
        }
    }

    @Override
    public void draw(Context2d ctx) {
        if(this.hasSelection()){
            ctx.setFillStyle(SELECTED_COLOR);
            ctx.fillRect(this.xMin, 0, this.width, ctx.getCanvas().getHeight());
        }
    }

    @Override
    public String toString() {
        return "Background{" +
                "xMin=" + xMin +
                ", width=" + width +
                ", selection=" + hasSelection() +
                '}';
    }
}
