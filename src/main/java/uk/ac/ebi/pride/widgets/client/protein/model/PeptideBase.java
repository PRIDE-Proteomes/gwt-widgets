package uk.ac.ebi.pride.widgets.client.protein.model;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.event.shared.HandlerManager;
import uk.ac.ebi.pride.widgets.client.common.handler.PeptideHandler;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Clickable;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Drawable;
import uk.ac.ebi.pride.widgets.client.protein.events.PeptideHighlightedEvent;
import uk.ac.ebi.pride.widgets.client.protein.events.PeptideSelectedEvent;
import uk.ac.ebi.pride.widgets.client.protein.utils.CanvasProperties;
import uk.ac.ebi.pride.widgets.client.protein.utils.Tooltip;

public class PeptideBase implements Drawable, Clickable {
    public static final CssColor PEPTIDE_SELECTED_COLOR = CssColor.make("rgba(255,255,0, 0.75)");
    public static final CssColor NON_UNIQUE_PEPTIDE_COLOR = CssColor.make("rgba(46,228,255, .5)");
    public static final CssColor UNIQUE_PEPTIDE_COLOR = CssColor.make("rgba(0,0,175, 1)");

    public static final int PEPTIDE_HEIGHT = 5;

    protected HandlerManager handlerManager;
    private PeptideHandler peptide;
    private Tooltip tooltip = new Tooltip();
    private String tooltipMessage;
    private CssColor peptideColor;
    private boolean selected = false;
    private boolean mouseOver = false;
    private double xMin, xMax, width;
    private int yMin, yMax;

    // mouse positions relative to canvas
    int mouseX, mouseY;

    public PeptideBase(CanvasProperties canvasProperties, PeptideHandler peptide, int y) {
        this.peptide = peptide;
        this.peptideColor = peptide.getUniqueness()==1 ? UNIQUE_PEPTIDE_COLOR : NON_UNIQUE_PEPTIDE_COLOR;
        this.xMin = canvasProperties.getPixelFromPosition(peptide.getSite());
        this.xMax = canvasProperties.getPixelFromPosition(peptide.getSite() + peptide.getSequence().length());
        this.width = Math.ceil(xMax - xMin);
        this.yMin = y;
        this.yMax = y + PEPTIDE_HEIGHT;

        this.tooltipMessage = this.getModificationTooltip();
    }

    public void setHandlerManager(HandlerManager handlerManager) {
        this.handlerManager = handlerManager;
    }

    public int getYMax() {
        return yMax;
    }

    public PeptideHandler getPeptide() {
        return peptide;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isMouseOver(){
        //Only for "mouse over" detection, xMin and xMax do no take into account the border :)
        //double xMinAux = xMin;// + CoveredSequenceBorder.BORDER;
        //double xMaxAux = xMax;// - CoveredSequenceBorder.BORDER;
        return (xMin<=mouseX && xMax>=mouseX) && (mouseY<=yMax && mouseY>=yMin);
    }

    @Override
    public void setMousePosition(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    @Override
    public void draw(Context2d ctx) {
        ctx.setFillStyle(this.peptideColor);
        ctx.fillRect(xMin, yMin, this.width, PEPTIDE_HEIGHT);

        boolean mouseOverAux = isMouseOver();
        if(mouseOverAux || selected){
            ctx.setFillStyle(PEPTIDE_SELECTED_COLOR);
            ctx.fillRect(xMin, yMin, this.width, PEPTIDE_HEIGHT);

            //Ensures only fired the first time the mouse enters in a non highlighted peptide
            if(!selected && !this.mouseOver){
                handlerManager.fireEvent(new PeptideHighlightedEvent(this.peptide));
            }
        }
        if(mouseOverAux){
            this.tooltip.show(ctx.getCanvas(), (int) xMax, yMax, tooltipMessage);
            this.tooltip.setAnimationEnabled(false);
        }else{
            this.tooltip.hide();
            this.tooltip.setAnimationEnabled(true);
        }

        this.mouseOver = mouseOverAux;
    }

    @Override
    public void onMouseUp(int mouseX, int mouseY) {
        setMousePosition(mouseX, mouseY);
        if(!isMouseOver()){
            selected = false;
        }else{
            if(!selected){
                handlerManager.fireEvent(new PeptideSelectedEvent(this.peptide));
            }
            selected = true;
        }
    }

    @Override
    public void onMouseDown(int mouseX, int mouseY) {
        setMousePosition(mouseX, mouseY);
    }

    @Override
    public boolean isSelected() {
        return this.selected;
    }

    private String getModificationTooltip(){
        StringBuilder sb = new StringBuilder("<span style=\"font-weight:bold;color:");
        if(this.peptide.getUniqueness()>1){
            sb.append(NON_UNIQUE_PEPTIDE_COLOR.value());
            sb.append("\">NON UNIQUE PEPTIDE</span>");
        }else{
            sb.append(UNIQUE_PEPTIDE_COLOR.value());
            sb.append("\">UNIQUE PEPTIDE</span>");
        }
        sb.append("<br/>");
        sb.append("&nbsp;&nbsp;&nbsp;&nbsp;Sequence: ");
        sb.append(this.peptide.getSequence());
        sb.append("<br/>");
        sb.append("&nbsp;&nbsp;&nbsp;&nbsp;Start: ");
        sb.append(this.peptide.getSite());
        sb.append(";&nbsp;&nbsp;&nbsp;End: ");
        sb.append(this.peptide.getEnd());
        return sb.toString();
    }

}
