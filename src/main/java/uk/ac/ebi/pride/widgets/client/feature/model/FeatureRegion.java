package uk.ac.ebi.pride.widgets.client.feature.model;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.shared.HandlerManager;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Drawable;
import uk.ac.ebi.pride.widgets.client.feature.events.FeatureRegionHighlightedEvent;

/**
 * @author ntoro
 * @since 21/07/15 16:28
 */
public abstract class FeatureRegion implements Drawable {
    protected HandlerManager handlerManager;
    private boolean highlighted;
    protected boolean selected = false;

    private int start;
    protected int length;
    protected uk.ac.ebi.pride.widgets.client.feature.utils.CanvasProperties canvasProperties;

    // mouse positions relative to canvas
    int mouseX, mouseY;

    protected FeatureRegion(int start, uk.ac.ebi.pride.widgets.client.feature.utils.CanvasProperties canvasProperties) {
        this.highlighted = false;
        this.selected = false;

        this.start = start;
        this.length = 1;
        this.canvasProperties = canvasProperties;
    }

    public void increaseLength() {
        this.length++;
    }

    public int getStart() {
        return start;
    }

    public int getLength() {
        return this.length;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setHandlerManager(HandlerManager handlerManager) {
        this.handlerManager = handlerManager;
    }

    public abstract boolean isMouseOver();

    @Override
    public void setMousePosition(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;

        if(isMouseOver()){
            if(!this.selected && !this.highlighted){
                if(handlerManager!=null)
                    handlerManager.fireEvent(new FeatureRegionHighlightedEvent(this.start, this.length));
                this.highlighted = true;
            }
        }else{
            this.highlighted = false;
        }
    }

    @Override
    public abstract void draw(Context2d context);

    @Override
    public String toString() {
        return "FeatureRegion{" +
                "start=" + start +
                ", length=" + length +
                '}';
    }
}
