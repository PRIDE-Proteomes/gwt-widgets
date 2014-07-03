package uk.ac.ebi.pride.widgets.client.protein.model;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.event.shared.HandlerManager;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Clickable;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Drawable;
import uk.ac.ebi.pride.widgets.client.protein.events.ProteinAreaHighlightEvent;
import uk.ac.ebi.pride.widgets.client.protein.utils.CanvasProperties;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class ProteinAreaSelection implements Drawable, Clickable {
    public static final CssColor NONE_SELECTED_COLOR = CssColor.make("rgba(255,255,255, 0.75)");
    public static final CssColor SELECTED_COLOR = CssColor.make("rgba(255,255,0, 0.25)");

    protected HandlerManager handlerManager;
    private CanvasProperties canvasProperties;
    private boolean naturalSelection;

    private int start, end;
    private double xMin, width;

    private double xRelative;
    private final double proteinStart, proteinEnd;

    // mouse positions relative to canvas
    private int mouseX;
    private boolean mouseDown;
    private boolean selecting;
    private boolean movingSelectedRegion;
    private boolean selectedRegionMoved;

    public ProteinAreaSelection(CanvasProperties canvasProperties, boolean naturalSelection) {
        this.canvasProperties = canvasProperties;
        this.naturalSelection = naturalSelection;
        this.width = 0;
        this.mouseDown = false;
        this.selecting = false;
        this.movingSelectedRegion = false;
        this.selectedRegionMoved = false;
        this.proteinStart = canvasProperties.getPixelFromPosition(1);
        this.proteinEnd = canvasProperties.getPixelFromPosition(canvasProperties.getProteinLength()+1);
    }

    public void setHandlerManager(HandlerManager handlerManager) {
        this.handlerManager = handlerManager;
    }

    public void setSelectedArea(int start, int end){
        if(start!=this.start || end!=this.end){
            this.xMin = this.canvasProperties.getPixelFromPosition(start);
            double xMax = this.canvasProperties.getPixelFromPosition(end + 1);
            this.width = xMax - xMin;
        }
        this.start = start;
        this.end = end;
        this.xRelative = -1;
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
        if(!hasSelection()) return false;

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

    public boolean isSelectionInProgress() {
        return this.mouseDown && this.selecting;
    }

    public double getSelectionDirection(){
        return Math.signum(this.width);
    }

    public boolean isMovingSelectedRegion() {
        return this.movingSelectedRegion;
    }

    public boolean isSelectedRegionMoved() {
        return this.selectedRegionMoved;
    }

    @Override
    public void onMouseDown(int mouseX, int mouseY) {
        this.mouseDown = true;
        this.mouseX = mouseX;
        this.selecting = !hasSelection() || !isMouseOver();
        if(this.selecting){
            //IMPORTANT We use this.xMin because is double and there are two setSelectedArea, one for int one for double
            this.xMin = mouseX;
            this.setSelectedArea(this.xMin, this.xMin);
        }else if(isMouseOver()){
            this.movingSelectedRegion = true;
            this.selectedRegionMoved = false;
            this.xRelative = this.mouseX - this.xMin;
        }

        /*this.selecting = !hasSelection() || !isMouseOver();
        if(isMouseOver()){
            this.movingSelectedRegion = true;
            this.selectedRegionMoved = false;
            this.xRelative = this.mouseX - this.xMin;
        }else{
            this.setSelectedArea(this.xMin, this.xMin);
        }*/
    }

    @Override
    public void onMouseUp(int mouseX, int mouseY) {
        this.mouseDown = false;
        this.selecting = false;
        this.mouseX = mouseX;

        this.movingSelectedRegion = false;
        this.xRelative = -1;

        //CORRECTION when user selects from right to left
        if(this.width<0){
            this.xMin = this.xMin + this.width;
            this.width = Math.abs(this.width);
        }
    }

    @Override
    public void setMousePosition(int mouseX, int mouseY) {
        if(mouseX == this.mouseX) return;

        this.mouseX = mouseX;
        //By default we have to set this, to keep consistency
        this.selectedRegionMoved = false;
        if(isSelectionInProgress()){
            setSelectedArea(this.xMin, (double) mouseX);
            if(this.hasSelection()){
                //System.out.println(new ProteinAreaHighlightEvent(this).toString());
                handlerManager.fireEvent(new ProteinAreaHighlightEvent(this));
            }
        }else if(this.movingSelectedRegion && this.mouseX>=0){
            this.selectedRegionMoved = true;
            double aux = this.mouseX - this.xRelative;
            if(aux<this.proteinStart){
                this.xMin = this.proteinStart;
            }else if((aux + this.width) > this.proteinEnd){
                this.xMin = this.proteinEnd - this.width;
            }else{
                this.xMin = aux;
            }
            setSelectedArea(this.xMin, this.xMin + this.width);
            if(this.hasSelection()){
                //System.out.println(new ProteinAreaHighlightEvent(this).toString());
                handlerManager.fireEvent(new ProteinAreaHighlightEvent(this));
            }
        }
    }

    @Override
    public void draw(Context2d ctx) {
        if(!this.hasSelection()) return;

        if(this.naturalSelection){
            ctx.setFillStyle(SELECTED_COLOR);
            ctx.fillRect(this.xMin, 0, this.width, ctx.getCanvas().getHeight());
        }else{
            double xMin = this.xMin;
            double width = this.width;

            //LOCAL CORRECTION when user selects from right to left
            if(this.width<0){
                xMin = this.xMin + this.width;
                width = Math.abs(this.width);
            }

            ctx.setFillStyle(NONE_SELECTED_COLOR);
            double widthAux = xMin - this.proteinStart + CanvasProperties.X_OFFSET;
            ctx.fillRect(0, 0, widthAux, ctx.getCanvas().getHeight());

            double xMax = xMin + width;
            widthAux = ctx.getCanvas().getWidth()- xMax + CanvasProperties.X_OFFSET;
            ctx.fillRect(xMax, 0, widthAux, ctx.getCanvas().getHeight());
        }
    }

    @Override
    public String toString() {
        return "ProteinAreaSelection{" +
                "xMin=" + xMin +
                ", width=" + width +
                ", selection=" + hasSelection() +
                '}';
    }
}
