package uk.ac.ebi.pride.widgets.client.protein.model;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Animated;
import uk.ac.ebi.pride.widgets.client.common.utils.AnimationUtils;
import uk.ac.ebi.pride.widgets.client.protein.constants.Colors;
import uk.ac.ebi.pride.widgets.client.protein.events.ProteinRegionSelectionEvent;
import uk.ac.ebi.pride.widgets.client.protein.interfaces.Clickable;
import uk.ac.ebi.pride.widgets.client.protein.utils.CanvasProperties;
import uk.ac.ebi.pride.widgets.client.protein.utils.ColorFactory;

public class CoveredSequenceRegion extends SequenceRegion implements Clickable, Animated {
    public static final int BOXES_HEIGHT = 50;

    private boolean fireEvent = false;
    private double xMin, xMax, width;
    private int yMin, yMax, height;
    private CssColor regionColor;

    public CoveredSequenceRegion(int start, int peptides, CanvasProperties canvasProperties) {
        super(start, peptides, canvasProperties);
        setBounds();
        this.yMin = CanvasProperties.Y_OFFSET;// + CoveredSequenceBorder.BORDER;
        this.yMax = CanvasProperties.Y_OFFSET + BOXES_HEIGHT;// - CoveredSequenceBorder.BORDER;
        this.height = yMax - yMin;
        this.regionColor = ColorFactory.getBlueBasedColor(getPeptides() * 7);
    }

    @Override
    public void increaseLength() {
        super.increaseLength();
        setBounds();
    }

    private void setBounds(){
        xMin = Math.floor(this.canvasProperties.getPixelFromPosition(getStart()));
        xMax = Math.floor(this.canvasProperties.getPixelFromPosition(getStart() + getLength()));
        width = xMax - xMin;
    }

    @Override
    public boolean isMouseOver(){
        return (xMin<=mouseX && mouseX<xMax) && (yMax>=mouseY && mouseY>=yMin);
    }

    @Override
    public void draw(Context2d ctx) {
        ctx.setFillStyle(regionColor);
        ctx.fillRect(xMin, yMin, width, height);

        if(isMouseOver()){
            showRegionTooltip(ctx);
        }

        if(isMouseOver() || selected){
            ctx.setFillStyle(Colors.PEPTIDE_SELECTED_COLOR);
            ctx.fillRect(xMin, yMin, width, height);
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

    @Override
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

    private void showRegionTooltip(Context2d ctx){
        int offset = 5;
        String str = getPeptides() + " Peptide Evidences";
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
        ctx.setFillStyle("#FFFFFF");
        ctx.fill();
        ctx.setStrokeStyle("#000000");
        ctx.stroke();
    }

    @Override
    public void fireSelectionEvent() {
        if(this.fireEvent){
            this.fireEvent = false;
            handlerManager.fireEvent(new ProteinRegionSelectionEvent(getStart(), getLength(), getPeptides()));
        }
    }
}