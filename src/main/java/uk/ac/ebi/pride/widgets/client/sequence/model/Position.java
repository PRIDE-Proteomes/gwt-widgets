package uk.ac.ebi.pride.widgets.client.sequence.model;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import uk.ac.ebi.pride.widgets.client.common.Clickable;
import uk.ac.ebi.pride.widgets.client.common.Drawable;
import uk.ac.ebi.pride.widgets.client.sequence.data.Protein;
import uk.ac.ebi.pride.widgets.client.sequence.utils.CanvasProperties;
import uk.ac.ebi.pride.widgets.client.sequence.utils.Tooltip;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class Position implements Drawable, DrawablePosition, DrawableSelection, Clickable {
    private static final int WIDTH = 8;
    private static final int HEIGHT = 10;
    private static final CssColor HIGHLIGHT_COLOR = CssColor.make("rgba(255,255,0, .5)");

    private static int regionStart = -1;
    private static int regionEnd = -1;
    private static boolean mouseDown = false;

    //private Protein protein;
    private String aminoAcid;
    private int position;
    private String tooltip;

    // mouse positions relative to canvas
    int mouseX, mouseY;

    private int x, y, xText, yText;
    private int xMax, yMin;

    public Position(Protein protein, int position) {
        //this.protein = protein;
        this.aminoAcid = protein.getSequence().substring(position, position + 1);
        this.position = position + 1;
        this.tooltip = String.valueOf(this.position);

        CanvasProperties canvasProperties = CanvasProperties.getCanvasProperties();
        this.x = canvasProperties.getNextPosX();
        this.y = canvasProperties.getNextPosY();
        this.xMax = this.x + WIDTH;
        this.yMin = y - HEIGHT;
        this.xText = x + 1;
        this.yText = y - 2;
        canvasProperties.reserveHorizontalSpace(WIDTH);
    }

    public boolean isMouseOver(){
        return (x<=mouseX & xMax>mouseX & yMin<=mouseY & y>=mouseY);
    }

    public static void setRegionStart(int regionStart) {
        Position.regionStart = regionStart;
    }

    public static void setRegionEnd(int regionEnd) {
        Position.regionEnd = regionEnd;
    }

    @Override
    public void setMousePosition(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    @Override
    public void draw(Context2d ctx) {
        if(this.position >= 10 && this.position <=20){
            ctx.setFillStyle("#00AF00");
            ctx.fillRect(x, yMin, WIDTH, HEIGHT);
        }
        ctx.setFillStyle("#000000");
        ctx.fillText(this.aminoAcid, xText, yText);
    }

    @Override
    public void drawSelection(Context2d ctx) {
        if(this.position >= regionStart & this.position <= regionEnd){
            ctx.setFillStyle(HIGHLIGHT_COLOR);
            ctx.fillRect(x, yMin, WIDTH, HEIGHT);
            //ctx.setFillStyle("#000000");
            //ctx.fillText(this.aminoAcid, xText, yText);
        }
    }

    @Override
    public void drawPosition(Context2d ctx) {
        if(isMouseOver()){
            Tooltip.getTooltip().show(ctx.getCanvas(), xMax, y, tooltip);
            ctx.setFillStyle(HIGHLIGHT_COLOR);
            ctx.fillRect(x, yMin, WIDTH, HEIGHT);
            ctx.setFillStyle("#0000FF");
            ctx.fillText(this.aminoAcid, xText, yText);
            if(mouseDown) regionEnd = this.position;
        }
    }

    @Override
    public void onMouseUp(int mouseX, int mouseY) {
        mouseDown = false;
        if(isMouseOver()){
            System.out.println("Region Selected: [" + regionStart + ", " + regionEnd + "]");
            regionEnd = this.position;
        }
    }

    @Override
    public void onMouseDown(int mouseX, int mouseY) {
        mouseDown = true;
        if(isMouseOver()){
            regionStart = this.position;
            regionEnd = this.position;
            System.out.println("Mouse down " + this.position);
        }
    }
}
