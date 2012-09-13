package uk.ac.ebi.pride.widgets.client.protein.model;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.canvas.dom.client.FillStrokeStyle;
import uk.ac.ebi.pride.widgets.client.common.Clickable;
import uk.ac.ebi.pride.widgets.client.common.Drawable;

public class PeptideBase implements Drawable, Clickable {
    public static final int PEPTIDE_HEIGHT = 5;

    private String sequence;
    private boolean selected = false;
    private double xMin, xMax;
    private int yMin, yMax;

    // mouse positions relative to canvas
    int mouseX, mouseY;

    public PeptideBase(ProteinAxis pa, int start, String sequence, int y) {
        this.sequence = sequence;
        this.xMin = pa.getPixelFromValue(start);
        this.xMax = pa.getPixelFromValue(start + sequence.length());
        this.yMin = y;
        this.yMax = y + PEPTIDE_HEIGHT;
    }

    public int getYMax() {
        return yMax;
    }

    public boolean isMouseOver(){
        //Only for "mouse over" detection, xMin and xMax do no take into account the border :)
        double xMinAux = xMin;// + CoveredSequenceBorder.BORDER;
        double xMaxAux = xMax;// - CoveredSequenceBorder.BORDER;
        return (xMinAux<mouseX && xMaxAux>mouseX) && (mouseY<=yMax && mouseY>=yMin);
    }

    @Override
    public void setMousePosition(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    @Override
    public void draw(Context2d ctx) {
        FillStrokeStyle s =  ctx.getFillStyle();
        if(isMouseOver() || selected){
            ctx.setFillStyle(CssColor.make("rgba(255,100,0, 1)"));
        }else{
            ctx.setFillStyle(CssColor.make("rgba(0,0,255, 1)"));
        }
        ctx.fillRect(xMin, yMin, Math.ceil(xMax - xMin), PEPTIDE_HEIGHT);
        ctx.setFillStyle(s);
    }

    @Override
    public void onMouseUp(int mouseX, int mouseY) {
        if(!isMouseOver()){
            selected = false;
        }else{
            if(!selected){
                //handlerManager.fireEvent(new ProteinRegionSelectionEvent(getStart(), getLength(), getPeptides()));
                System.out.println(this.sequence);
            }
            selected = true;
        }
    }

    @Override
    public void onMouseDown(int mouseX, int mouseY) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
