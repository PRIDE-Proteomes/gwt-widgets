package uk.ac.ebi.pride.widgets.client.protein.model;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.event.shared.HandlerManager;
import uk.ac.ebi.pride.widgets.client.common.handler.PrideModificationHandler;
import uk.ac.ebi.pride.widgets.client.common.handler.ProteinModificationHandler;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Clickable;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Drawable;
import uk.ac.ebi.pride.widgets.client.protein.events.ModificationHighlightedEvent;
import uk.ac.ebi.pride.widgets.client.protein.events.ModificationSelectedEvent;
import uk.ac.ebi.pride.widgets.client.protein.utils.CanvasProperties;
import uk.ac.ebi.pride.widgets.client.protein.utils.Tooltip;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class ModificationBase implements Clickable, Drawable {
    public static final int MODIFICATION_HEIGHT = 6;
    public static final int MODIFICATION_TICK_WIDTH = 8;
    public static final CssColor MODIFICATION_COLOR = CssColor.make("rgba(255,0,0, 1)");
    public static final CssColor MODIFICATION_SELECTED_COLOR = CssColor.make("rgba(255,255,0, 0.75)");
    public static final CssColor MODIFICATION_HIGHLIGHTED_COLOR = CssColor.make("rgba(0,255,0, 1)");

    protected HandlerManager handlerManager;
    private int position;
    private List<ProteinModificationHandler> modifications;
    private Tooltip tooltip = new Tooltip();
    private String tooltipMessage;
    private boolean selected = false;
    private boolean highlighted = false;
    private boolean mouseOver = false;
    private double ax, ay, bx, by, cx, cy;

    // mouse positions relative to canvas
    int mouseX, mouseY;

    public ModificationBase(int position, List<ProteinModificationHandler> modifications, CanvasProperties canvasProperties) {
        this.position = position;
        this.modifications = modifications;
        ax = canvasProperties.getPixelFromPosition(position);
        bx = ax - MODIFICATION_TICK_WIDTH / 2;
        cx = ax + MODIFICATION_TICK_WIDTH / 2;

        ay = CanvasProperties.Y_OFFSET + CoveredSequenceRegion.BOXES_HEIGHT;
        by = cy = ay + MODIFICATION_HEIGHT;

        this.tooltipMessage = getModificationTooltip();
    }

    public int getPosition() {
        return this.position;
    }

    public boolean containModification(PrideModificationHandler modification) {
        for (ProteinModificationHandler aux : modifications) {
            if(aux.getPrideModification().getId()==modification.getId()){
                return true;
            }
        }
        return false;
    }

    public void setHandlerManager(HandlerManager handlerManager) {
        this.handlerManager = handlerManager;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public boolean isMouseOver(){
        return (mouseX >= bx && mouseX <= cx) && (mouseY <= by && mouseY >= ay);
    }

    @Override
    public void onMouseUp(int mouseX, int mouseY) {
        setMousePosition(mouseX, mouseY);
        if(!isMouseOver()){
            selected = false;
        }else{
            if(!selected){
                handlerManager.fireEvent(new ModificationSelectedEvent(this.position, this.modifications));
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

    @Override
    public void setMousePosition(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    @Override
    public void draw(Context2d ctx) {
        ctx.setFillStyle(MODIFICATION_COLOR);
        ctx.beginPath();
        ctx.moveTo(ax, ay);
        ctx.lineTo(bx, by);
        ctx.lineTo(cx, cy);
        ctx.closePath();
        ctx.fill();

        if(highlighted){
            ctx.setFillStyle(MODIFICATION_HIGHLIGHTED_COLOR);
            ctx.beginPath();
            ctx.moveTo(ax, ay);
            ctx.lineTo(bx, by);
            ctx.lineTo(cx, cy);
            ctx.closePath();
            ctx.fill();
        }

        boolean mouseOverAux = isMouseOver();
        if(mouseOverAux || selected){
            ctx.setFillStyle(MODIFICATION_SELECTED_COLOR);
            ctx.beginPath();
            ctx.moveTo(ax, ay);
            ctx.lineTo(bx, by);
            ctx.lineTo(cx, cy);
            ctx.closePath();
            ctx.fill();

            //Ensures only fired the first time the mouse enters in a non highlighted modification
            if(!this.mouseOver && !selected){
                handlerManager.fireEvent(new ModificationHighlightedEvent(this.position, this.modifications));
            }
        }
        if(mouseOverAux){
            this.tooltip.show(ctx.getCanvas(), (int) cx, (int) cy, tooltipMessage);
            this.tooltip.setAnimationEnabled(false);
        }else{
            this.tooltip.hide();
            this.tooltip.setAnimationEnabled(true);
        }

        this.mouseOver = mouseOverAux;
    }

    private String getModificationTooltip(){
        @SuppressWarnings("Convert2Diamond")
        Map<String, Integer> count = new HashMap<String, Integer>();
        for (ProteinModificationHandler prideModification : this.modifications) {
            PrideModificationHandler aux = prideModification.getPrideModification();
            int n = count.containsKey(aux.getName()) ? count.get(aux.getName()) : 0;
            count.put(aux.getName(), n + 1);
        }
        StringBuilder sb = new StringBuilder("<span style=\"font-weight:bold;color:");
        sb.append(MODIFICATION_COLOR.value());
        sb.append("\">MODIFICATION</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;Position: ");
        sb.append(String.valueOf(this.position));
        //sb.append("<br/>&nbsp;&nbsp;&nbsp;&nbsp;Composed of:");
        for (String modificationName : count.keySet()) {
            int c = count.get(modificationName);
            sb.append("<br/>&nbsp;&nbsp;&nbsp;&nbsp;");
            sb.append(modificationName);
            sb.append(":&nbsp;");
            sb.append(c);
            sb.append("&nbsp;observation");
            if(c>1) sb.append("s");
        }
        return sb.toString();
    }
}
