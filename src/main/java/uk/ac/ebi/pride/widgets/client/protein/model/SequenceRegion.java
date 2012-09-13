package uk.ac.ebi.pride.widgets.client.protein.model;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.shared.HandlerManager;
import uk.ac.ebi.pride.widgets.client.common.Drawable;
import uk.ac.ebi.pride.widgets.client.protein.events.ProteinRegionHighlightEvent;

public abstract class SequenceRegion implements Drawable {
    protected HandlerManager handlerManager;
    private boolean highlighted;
    protected boolean selected = false;

    private int start;
    private int peptides;
    protected int length;
    private ProteinAxis pa;


    // mouse positions relative to canvas
    int mouseX, mouseY;

    protected SequenceRegion(int start, ProteinAxis pa) {
        this(start, -1, pa);
    }
    protected SequenceRegion(int start, int peptides, ProteinAxis pa) {
        this.highlighted = false;
        this.selected = false;

        this.start = start;
        this.peptides = peptides;
        this.length = 1;
        this.pa = pa;
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

    public ProteinAxis getProteinSegment() {
        return pa;
    }

    public int getPeptides() {
        return peptides;
    }

    public void setHandlerManager(HandlerManager handlerManager) {
        this.handlerManager = handlerManager;
    }

    public abstract boolean isMouseOver();

    public double getPixelFromValue(double value){
        return pa.getPixelFromValue(value);
    }

    @Override
    public void setMousePosition(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;

        if(isMouseOver()){
            if(!this.selected && !this.highlighted){
                if(handlerManager!=null)
                    handlerManager.fireEvent(new ProteinRegionHighlightEvent(this.start, this.length, this.peptides));
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
        return "SequenceRegion{" +
                "start=" + start +
                ", value=" + peptides +
                ", length=" + length +
                '}';
    }
}