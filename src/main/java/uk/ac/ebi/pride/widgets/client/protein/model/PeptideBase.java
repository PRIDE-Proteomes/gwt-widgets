package uk.ac.ebi.pride.widgets.client.protein.model;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.canvas.dom.client.FillStrokeStyle;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.event.shared.HandlerManager;
import uk.ac.ebi.pride.widgets.client.common.handler.PeptideHandler;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Animated;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Drawable;
import uk.ac.ebi.pride.widgets.client.common.utils.AnimationUtils;
import uk.ac.ebi.pride.widgets.client.common.utils.Tooltip;
import uk.ac.ebi.pride.widgets.client.protein.constants.Colors;
import uk.ac.ebi.pride.widgets.client.protein.events.PeptideHighlightedEvent;
import uk.ac.ebi.pride.widgets.client.protein.events.PeptideSelectedEvent;
import uk.ac.ebi.pride.widgets.client.protein.interfaces.Clickable;
import uk.ac.ebi.pride.widgets.client.protein.utils.CanvasProperties;

import java.util.logging.Level;
import java.util.logging.Logger;

import static uk.ac.ebi.pride.widgets.client.protein.utils.PeptideBaseFactory.PEPTIDES_Y;

public class PeptideBase implements Drawable, Clickable, Animated {

    public static final int SAFE_ZONE = 10;
    public static final int PEPTIDE_HEIGHT = 5;

    Logger logger = Logger.getLogger(PeptideBase.class.getName());


    protected HandlerManager handlerManager;
    private PeptideHandler peptide;

    private Tooltip tooltip;
    private String tooltipMessage;
    private CssColor peptideColor;
    private boolean selected = false;
    private boolean fireEvent = false;
    private boolean highlighted = false;
    private boolean mouseOver = false;
    private double xMin, xMax, width;
    private int yMin, yMax;

    // mouse positions relative to canvas
    int mouseX, mouseY;

    public PeptideBase(CanvasProperties canvasProperties, PeptideHandler peptide, int y, String tooltipMessage, CssColor peptideColor, int site, int peptideLength) {
        this.peptide = peptide;
        this.peptideColor = peptideColor;
        this.xMin = canvasProperties.getPixelFromPosition(site);
        this.xMax = canvasProperties.getPixelFromPosition(site + peptideLength);
        this.width = xMax - xMin;
        this.yMin = y;
        this.yMax = y + PEPTIDE_HEIGHT;
        this.tooltip = new Tooltip();
        this.tooltipMessage = tooltipMessage;
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

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public boolean isMouseOver() {
        //Only for "mouse over" detection, xMin and xMax do no take into account the border :)
        return (xMin <= mouseX && xMax >= mouseX) && (mouseY <= yMax && mouseY >= yMin);
    }

    public boolean isMouseOverTooltip(CanvasElement canvas) {

        //We add a safe zone around the tooltip to help the user to stay inside the tooltip
        int left = tooltip.getAbsoluteLeft() - SAFE_ZONE;
        int right = (tooltip.getAbsoluteLeft() + tooltip.getOffsetWidth() + SAFE_ZONE);
        int top = tooltip.getAbsoluteTop() - SAFE_ZONE;
        int bottom = (tooltip.getAbsoluteTop() + tooltip.getOffsetHeight() + SAFE_ZONE);
        int mouseAbsoluteX = canvas.getAbsoluteLeft() + mouseX;
        int mouseAbsoluteY = canvas.getAbsoluteTop() + mouseY;

        logger.log(Level.INFO, "MouseOverTooltip: (" + mouseAbsoluteX + "," + mouseAbsoluteY + ")" +
                "(" + left + "," + right + "," + top + "," + bottom + ")");

        return (left <= mouseAbsoluteX && right >= mouseAbsoluteX) && (mouseAbsoluteY <= bottom && mouseAbsoluteY >= top);
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

    @Override
    public void setMousePosition(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    @Override
    public void draw(Context2d ctx) {
        CssColor color = this.peptideColor;

        if(highlighted){
            color = Colors.PEPTIDE_HIGHLIGHTED_COLOR;
        }

        if(selected){
            color = Colors.PEPTIDE_SELECTED_COLOR;
        }

        boolean mouseOverAux = isMouseOver();
        if (mouseOverAux) {
            FillStrokeStyle oldstyle = ctx.getStrokeStyle();
            double oldLineWith = ctx.getLineWidth();
            ctx.setStrokeStyle(color);
            ctx.setLineWidth(2);
            ctx.strokeRect(xMin, yMin, this.width, PEPTIDE_HEIGHT);
            ctx.setStrokeStyle(oldstyle);
            ctx.setLineWidth(oldLineWith);

            //Ensures only fired the first time the mouse enters in a non highlighted peptide
            if (!this.mouseOver && !selected) {
                handlerManager.fireEvent(new PeptideHighlightedEvent(this.peptide));
            }
        }

        ctx.setFillStyle(color);
        ctx.fillRect(xMin, yMin, this.width, PEPTIDE_HEIGHT);


        if (mouseOverAux) {
            this.tooltip.show(ctx.getCanvas(), (int) xMax, yMax, tooltipMessage);
            this.tooltip.setAnimationEnabled(false);

        } else if (tooltip.isShowing() && !isMouseOverTooltip(ctx.getCanvas())) {
            logger.log(Level.INFO, "Inside Tooltip peptide");

            this.tooltip.hide();
            this.tooltip.setAnimationEnabled(true);
        }

        this.mouseOver = mouseOverAux;
    }


    @Override
    public void drawAnimation(Context2d ctx, double progress) {
        progress = AnimationUtils.getProgress(0.5, 0.85, progress);
        if (progress == 0) return;
        double auxY = (yMin - PEPTIDES_Y) * progress + PEPTIDES_Y;
        ctx.setFillStyle(this.peptideColor);
        ctx.fillRect(xMin, auxY, this.width, PEPTIDE_HEIGHT);
    }

    @Override
    public void fireSelectionEvent() {
        if (this.fireEvent) {
            this.fireEvent = false;
            handlerManager.fireEvent(new PeptideSelectedEvent(this.peptide));
        }
    }
}
