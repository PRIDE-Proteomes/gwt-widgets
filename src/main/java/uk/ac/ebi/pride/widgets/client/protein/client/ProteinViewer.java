package uk.ac.ebi.pride.widgets.client.protein.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import uk.ac.ebi.pride.widgets.client.common.Clickable;
import uk.ac.ebi.pride.widgets.client.common.Drawable;
import uk.ac.ebi.pride.widgets.client.protein.data.Protein;
import uk.ac.ebi.pride.widgets.client.protein.events.ProteinRegionHighlightEvent;
import uk.ac.ebi.pride.widgets.client.protein.events.ProteinRegionSelectionEvent;
import uk.ac.ebi.pride.widgets.client.protein.handlers.ProteinRegionHighlightedHandler;
import uk.ac.ebi.pride.widgets.client.protein.handlers.ProteinRegionSelectedHandler;
import uk.ac.ebi.pride.widgets.client.protein.model.CoveredSequenceBorder;
import uk.ac.ebi.pride.widgets.client.protein.model.PeptideBase;
import uk.ac.ebi.pride.widgets.client.protein.model.ProteinAxis;
import uk.ac.ebi.pride.widgets.client.protein.model.SequenceRegion;
import uk.ac.ebi.pride.widgets.client.protein.utils.PeptideBaseFactory;
import uk.ac.ebi.pride.widgets.client.protein.utils.RegionUtils;


import java.util.ArrayList;
import java.util.List;

public class ProteinViewer extends Composite implements HasHandlers {
    HandlerManager handlerManager;

    //timer refresh rate, in milliseconds
    static final int REFRESH_RATE = 25;
    final Timer timer;
    private Canvas canvas;

    private List<Drawable> components = new ArrayList<Drawable>();

    // mouse positions relative to canvas
    int mouseX = -100; int lastMouseX = -200; //Do not assign the same value at the beginning
    int mouseY = -100; int lastMouseY = -200; //Do not assign the same value at the beginning

    public ProteinViewer(Protein protein) {
        this(protein, true);
    }

    public ProteinViewer(Protein protein, boolean regionBorder) {
        this(900, 90, protein, regionBorder);
    }

    public ProteinViewer(int width, int height, Protein protein) {
        this(width, height, protein, true);
    }

    public ProteinViewer(int width, int height, Protein protein, boolean regionBorder) {
        this.canvas = Canvas.createIfSupported();
        this.canvas.setPixelSize(width, height);
        this.canvas.setCoordinateSpaceWidth(width);
        this.canvas.setCoordinateSpaceHeight(height);

        this.handlerManager = new HandlerManager(this);

        ProteinAxis pa = new ProteinAxis(protein, this.canvas);
        components.add(pa);

        List<SequenceRegion> sequenceRegions = RegionUtils.getSequenceRegions(pa);
        for (SequenceRegion sr : sequenceRegions) {
            sr.setHandlerManager(this.handlerManager);
            components.add(sr);
        }

        if(regionBorder){
            List<CoveredSequenceBorder> borders = RegionUtils.getCoveredSequenceBorder(sequenceRegions);
            for (CoveredSequenceBorder border : borders) {
                components.add(border);
            }
        }

        int heightAux = height;
        for (PeptideBase peptideBase : PeptideBaseFactory.getPeptideBaseList(pa, protein)) {
            int yMax = peptideBase.getYMax();
            if(yMax > heightAux) heightAux = yMax;
            components.add(peptideBase);
        }

        if(heightAux > height){
            heightAux += ProteinAxis.Y_OFFSET;
            this.canvas.getCanvasElement().setHeight(heightAux);
            this.canvas.setPixelSize(width, heightAux);
        }

        // setup timer
        timer = new Timer() {
            @Override
            public void run() {
                doUpdate();
            }
        };
        timer.scheduleRepeating(REFRESH_RATE);

        initHandlers();

        initWidget(canvas);
    }

    public HandlerRegistration addProteinRegionHighlightedHandler(ProteinRegionHighlightedHandler handler){
        return handlerManager.addHandler(ProteinRegionHighlightEvent.TYPE, handler);
    }

    public HandlerRegistration addProteinRegionSelectedHandler(ProteinRegionSelectedHandler handler) {
        return handlerManager.addHandler(ProteinRegionSelectionEvent.TYPE, handler);
    }

    protected void doUpdate(){
        doUpdate(false);
    }

    protected synchronized void doUpdate(boolean forceDraw){
        if(!forceDraw){
            if(mouseX==lastMouseX && mouseY==lastMouseY) return;
        }
        lastMouseX = mouseX;
        lastMouseY = mouseY;
        this.setMousePosition(mouseX, mouseY);
        this.draw();
    }

    protected void setMousePosition(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        for (Drawable component : this.components) {
            component.setMousePosition(mouseX, mouseY);
        }
    }

    protected void draw() {
        Context2d ctx = canvas.getContext2d();

        //Clean the canvas
        ctx.setFillStyle(CssColor.make("rgba(255,255,255, 1)"));
        int width = ctx.getCanvas().getWidth();
        int height = ctx.getCanvas().getHeight();
        ctx.fillRect(0, 0, width, height);

        //Draw all the components
        for (Drawable component : this.components) {
            component.draw(ctx);
        }
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        handlerManager.fireEvent(event);
    }

    void initHandlers() {
        canvas.addMouseMoveHandler(new MouseMoveHandler() {
            public void onMouseMove(MouseMoveEvent event) {
                mouseX = event.getRelativeX(canvas.getElement());
                mouseY = event.getRelativeY(canvas.getElement());
            }
        });

        canvas.addMouseOutHandler(new MouseOutHandler() {
            public void onMouseOut(MouseOutEvent event) {
                mouseX = -200;
                mouseY = -200;
            }
        });

        /*
        canvas.addMouseDownHandler(new MouseDownHandler() {
            @Override
            public void onMouseDown(MouseDownEvent event) {
                for (Drawable component : components) {
                    if(component instanceof Clickable){
                        mouseX = event.getRelativeX(canvas.getElement());
                        mouseY = event.getRelativeY(canvas.getElement());
                        ((Clickable) component).onMouseDown(mouseX, mouseY);
                        doUpdate(true);
                    }
                }
            }
        });*/

        canvas.addMouseUpHandler(new MouseUpHandler() {
            @Override
            public void onMouseUp(MouseUpEvent event) {
                mouseX = event.getRelativeX(canvas.getElement());
                mouseY = event.getRelativeY(canvas.getElement());
                for (Drawable component : components) {
                    if(component instanceof Clickable){
                        ((Clickable) component).onMouseUp(mouseX, mouseY);
                    }
                }
                doUpdate(true);
            }
        });

        canvas.addMouseDownHandler(new MouseDownHandler() {
            @Override
            public void onMouseDown(MouseDownEvent event) {
                mouseX = event.getRelativeX(canvas.getElement());
                mouseY = event.getRelativeY(canvas.getElement());
                for (Drawable component : components) {
                    if(component instanceof Clickable){
                        ((Clickable) component).onMouseDown(mouseX, mouseY);
                    }
                }
            }
        });
    }
}
