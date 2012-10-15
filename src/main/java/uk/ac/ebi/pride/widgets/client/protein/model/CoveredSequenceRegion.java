package uk.ac.ebi.pride.widgets.client.protein.model;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Clickable;
import uk.ac.ebi.pride.widgets.client.protein.events.ProteinRegionSelectionEvent;
import uk.ac.ebi.pride.widgets.client.protein.utils.CanvasProperties;
import uk.ac.ebi.pride.widgets.client.protein.utils.ColorFactory;

public class CoveredSequenceRegion extends SequenceRegion implements Clickable {
    public static final int BOXES_HEIGHT = 50;
    public static final CssColor REGION_SELECTED_COLOR = CssColor.make("rgba(255,255,0, .5)");

    private double xMin, xMax, width;
    private int yMin, yMax, height;
    private CssColor regionColor;

    public CoveredSequenceRegion(int start, int peptides, CanvasProperties canvasProperties) {
        super(start, peptides, canvasProperties);
        setBounds();
        this.yMin = CanvasProperties.Y_OFFSET;// + CoveredSequenceBorder.BORDER;
        this.yMax = CanvasProperties.Y_OFFSET + BOXES_HEIGHT;// - CoveredSequenceBorder.BORDER;
        this.height = yMax - yMin;
        this.regionColor = ColorFactory.getRedBasedColor(getPeptides() * 7);
    }

    @Override
    public void increaseLength() {
        super.increaseLength();
        setBounds();
    }

    private void setBounds(){
        xMin = getPixelFromValue(getStart());
        xMax = getPixelFromValue(getStart() + getLength());
        width = Math.ceil(xMax - xMin);
    }

    @Override
    public boolean isMouseOver(){
        //Only for "mouse over" detection, xMin and xMax do no take into account the border :)
        double xMinAux = xMin;// + CoveredSequenceBorder.BORDER;
        double xMaxAux = xMax;// - CoveredSequenceBorder.BORDER;
        return (xMinAux<mouseX && xMaxAux>mouseX) && (mouseY<=yMax && mouseY>=yMin);
    }

    @Override
    public void draw(Context2d ctx) {
        ctx.setFillStyle(regionColor);
        ctx.fillRect(xMin, yMin, width, height);

        if(isMouseOver()){
            showRegionTooltip(ctx);
        }

        if(isMouseOver() || selected){
            ctx.setFillStyle(REGION_SELECTED_COLOR);
            ctx.fillRect(xMin, yMin, width, height);
        }
    }

    @Override
    public void onMouseUp(int mouseX, int mouseY) {
        setMousePosition(mouseX, mouseY);
        if(!isMouseOver()){
            selected = false;
        }else{
            if(!selected){
                handlerManager.fireEvent(new ProteinRegionSelectionEvent(getStart(), getLength(), getPeptides()));
            }
            selected = true;
        }
    }

    @Override
    public void onMouseDown(int mouseX, int mouseY) {
        setMousePosition(mouseX, mouseY);
    }

    private void showRegionTooltip(Context2d ctx){
        int offset = 5;
        String str = "Peptides: " + getPeptides();
        int width = str.length() * 5;

        int posX;
        int boxWidth = width + 2 * offset;
        if(mouseX > (ctx.getCanvas().getWidth() - boxWidth - CanvasProperties.X_OFFSET)){
            posX = ctx.getCanvas().getWidth() - boxWidth - CanvasProperties.X_OFFSET;
        }else if(mouseX < boxWidth / 2 + CanvasProperties.X_OFFSET){
            posX = CanvasProperties.X_OFFSET;
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
        ctx.setStrokeStyle("#000000");
        ctx.stroke();
    }
}