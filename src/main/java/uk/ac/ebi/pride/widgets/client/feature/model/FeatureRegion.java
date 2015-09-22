package uk.ac.ebi.pride.widgets.client.feature.model;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.shared.HandlerManager;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Drawable;
import uk.ac.ebi.pride.widgets.client.feature.events.FeatureRegionHighlightEvent;
import uk.ac.ebi.pride.widgets.client.feature.utils.FeatureCanvasProperties;

/**
 * @author ntoro
 * @since 21/07/15 16:28
 */
public abstract class FeatureRegion implements Drawable {
    protected HandlerManager handlerManager;
    private boolean highlighted;
    protected boolean selected = false;

    private final int start;
    private final String featureType;
    private final int length;
    private final String tooltipMessage;

    protected FeatureCanvasProperties featureCanvasProperties;

    // mouse positions relative to canvas
    int mouseX, mouseY;

    // Start and end are defined in protein coordinates

    protected FeatureRegion(int start, int end, String tooltipMessage, String featureType, FeatureCanvasProperties featureCanvasProperties) {
        this.highlighted = false;
        this.selected = false;
        this.featureType = featureType;
        this.start = start;
        this.length = end - start + 1;
        this.tooltipMessage = tooltipMessage;
        this.featureCanvasProperties = featureCanvasProperties;
    }

    public int getStart() {
        return start;
    }

    public int getLength() {
        return this.length;
    }

    public String getTooltipMessage() {
        return tooltipMessage;
    }

    public String getFeatureType() {
        return featureType;
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
                    handlerManager.fireEvent(new FeatureRegionHighlightEvent(this.start, this.length));
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
