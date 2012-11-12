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
import uk.ac.ebi.pride.widgets.client.common.handler.PeptideHandler;
import uk.ac.ebi.pride.widgets.client.common.handler.PrideModificationHandler;
import uk.ac.ebi.pride.widgets.client.common.handler.ProteinModificationHandler;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Clickable;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Drawable;
import uk.ac.ebi.pride.widgets.client.common.handler.ProteinHandler;
import uk.ac.ebi.pride.widgets.client.protein.events.*;
import uk.ac.ebi.pride.widgets.client.protein.handlers.*;
import uk.ac.ebi.pride.widgets.client.protein.model.*;
import uk.ac.ebi.pride.widgets.client.protein.utils.CanvasProperties;
import uk.ac.ebi.pride.widgets.client.protein.utils.PeptideBaseFactory;
import uk.ac.ebi.pride.widgets.client.protein.utils.RegionUtils;

import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public class ProteinViewer extends Composite implements HasHandlers {
    HandlerManager handlerManager;

    //timer refresh rate, in milliseconds
    static final int REFRESH_RATE = 25;
    final Timer timer;
    private Canvas canvas;
    private boolean objectSelected = false;

    private Background background;
    @SuppressWarnings("Convert2Diamond")
    private List<Drawable> components = new LinkedList<Drawable>();

    // mouse positions relative to canvas
    int mouseX = -100; int lastMouseX = -200; //Do not assign the same value at the beginning
    int mouseY = -100; int lastMouseY = -200; //Do not assign the same value at the beginning

    public ProteinViewer(ProteinHandler proteinHandler) {
        this(proteinHandler, true);
    }

    public ProteinViewer(ProteinHandler proteinHandler, boolean regionBorder) {
        this(900, 90, proteinHandler, regionBorder);
    }

    public ProteinViewer(int width, int height, ProteinHandler proteinHandler) {
        this(width, height, proteinHandler, true);
    }

    public ProteinViewer(int width, int height, ProteinHandler proteinHandler, boolean regionBorder) {
        this.canvas = Canvas.createIfSupported();
        this.canvas.setPixelSize(width, height);
        this.canvas.setCoordinateSpaceWidth(width);
        this.canvas.setCoordinateSpaceHeight(height);

        this.handlerManager = new HandlerManager(this);

        CanvasProperties canvasProperties = new CanvasProperties(proteinHandler, this.canvas);
        ProteinSummary proteinSummary = new ProteinSummary(proteinHandler);

        this.background = new Background(canvasProperties);
        this.background.setHandlerManager(this.handlerManager);

        ProteinAxis pa = new ProteinAxis(canvasProperties);
        this.components.add(pa);

        List<SequenceRegion> sequenceRegions = RegionUtils.getSequenceRegions(canvasProperties);
        for (SequenceRegion sr : sequenceRegions) {
            sr.setHandlerManager(this.handlerManager);
            components.add(sr);
        }

        if(regionBorder){
            List<CoveredSequenceBorder> borders = RegionUtils.getCoveredSequenceBorder(sequenceRegions, canvasProperties);
            for (CoveredSequenceBorder border : borders) {
                components.add(border);
            }
        }

        for (Integer position : proteinSummary.getModificationPositions()) {
            List<ProteinModificationHandler> modifications = proteinSummary.getPrideModifications(position);
            ModificationBase mb = new ModificationBase(position, modifications, canvasProperties);
            mb.setHandlerManager(this.handlerManager);
            components.add(mb);
        }

        int heightAux = height;
        for (PeptideBase peptideBase : PeptideBaseFactory.getPeptideBaseList(canvasProperties)) {
            int yMax = peptideBase.getYMax();
            if(yMax > heightAux) heightAux = yMax;
            peptideBase.setHandlerManager(this.handlerManager);
            components.add(peptideBase);
        }

        if(heightAux > height){
            heightAux += CanvasProperties.Y_OFFSET;
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

    public HandlerRegistration addPeptideHighlightedHandler(PeptideHighlightedHandler handler){
        return handlerManager.addHandler(PeptideHighlightedEvent.TYPE, handler);
    }

    public HandlerRegistration addPeptideSelectedHandler(PeptideSelectedHandler handler){
        return handlerManager.addHandler(PeptideSelectedEvent.TYPE, handler);
    }

    public HandlerRegistration addModificationHighlightedHandler(ModificationHighlightedHandler handler){
        return handlerManager.addHandler(ModificationHighlightedEvent.TYPE, handler);
    }

    public HandlerRegistration addModificationSelectedHandler(ModificationSelectedHandler handler){
        return handlerManager.addHandler(ModificationSelectedEvent.TYPE, handler);
    }

    public HandlerRegistration addBackgroundSelectedHandler(BackgroundSelectedHandler handler){
        return handlerManager.addHandler(BackgroundSelectedEvent.TYPE, handler);
    }

    public HandlerRegistration addBackgroundHighlightedHandler(BackgroundHighlightedHandler handler){
        return handlerManager.addHandler(BackgroundHighlightEvent.TYPE, handler);
    }

    public void setSelectedArea(int start, int end){
        this.background.setSelectedArea(start, end);
        doUpdate(true);
    }

    public void resetSelectedArea(){
        this.background.resetSelectedArea();
        doUpdate(true);
    }

    public void setSelectedPeptide(PeptideHandler peptide){
        int site = peptide.getSite();
        String sequence = peptide.getSequence();
        for (Drawable component : components) {
            if(component instanceof PeptideBase){
                PeptideBase peptideBase = (PeptideBase) component;
                PeptideHandler peptideAux = peptideBase.getPeptide();
                peptideBase.setSelected(peptideAux.getSite().equals(site) && peptideAux.getSequence().equals(sequence));
            }
        }
        doUpdate(true);
    }

    public void resetPeptideSelection(){
        for (Drawable component : components) {
            if(component instanceof PeptideBase){
                PeptideBase peptideBase = (PeptideBase) component;
                peptideBase.setSelected(false);
            }
        }
        doUpdate(true);
    }

    public void selectModificationsBetween(int start, int end){
        for (Drawable component : components) {
            if(component instanceof ModificationBase){
                ModificationBase modificationBase = (ModificationBase) component;
                int pos = modificationBase.getPosition();
                boolean inRange = (pos>=start && pos<=end);
                modificationBase.setSelected(inRange);
            }
        }
        doUpdate(true);
    }

    public void resetModificationSelection(){
        for (Drawable component : components) {
            if(component instanceof ModificationBase){
                ModificationBase modificationBase = (ModificationBase) component;
                modificationBase.setSelected(false);
            }
        }
        doUpdate(true);
    }

    public void setHighlightedModifications(PrideModificationHandler modification){
        for (Drawable component : components) {
            if(component instanceof ModificationBase){
                ModificationBase modificationBase = (ModificationBase) component;
                modificationBase.setHighlighted(modificationBase.containModification(modification));
            }
        }
        doUpdate(true);
    }

    public void resetModificationHighlight(){
        for (Drawable component : components) {
            if(component instanceof ModificationBase){
                ModificationBase modificationBase = (ModificationBase) component;
                modificationBase.setHighlighted(false);
            }
        }
        doUpdate(true);
    }

    public void resetRegionSelection(){
        for (Drawable component : components) {
            if(component instanceof SequenceRegion){
                SequenceRegion region = (SequenceRegion) component;
                region.resetSelection();
            }
        }
        doUpdate(true);
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

        this.background.setMousePosition(mouseX, mouseY);
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

        this.background.draw(ctx);
        //Draw all the components
        this.objectSelected = false;
        for (Drawable component : this.components) {
            component.draw(ctx);
            //Is necessary to keep track of any object selected
            if(component instanceof Clickable){
                boolean selected = ((Clickable) component).isSelected();
                this.objectSelected = this.objectSelected || selected;
            }
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

        canvas.addMouseDownHandler(new MouseDownHandler() {
            @Override
            public void onMouseDown(MouseDownEvent event) {
                mouseX = event.getRelativeX(canvas.getElement());
                mouseY = event.getRelativeY(canvas.getElement());

                boolean objectSelected = false;
                if(!background.selectionInProgress()){
                    for (Drawable component : components) {
                        if(component instanceof Clickable){
                            Clickable c = (Clickable) component;
                            c.onMouseDown(mouseX, mouseY);
                            objectSelected = objectSelected || c.isMouseOver();
                        }
                    }
                }
                if(!objectSelected){
                    background.onMouseDown(mouseX, mouseY);
                }

                doUpdate(true);
            }
        });

        canvas.addMouseUpHandler(new MouseUpHandler() {
            @Override
            public void onMouseUp(MouseUpEvent event) {
                mouseX = event.getRelativeX(canvas.getElement());
                mouseY = event.getRelativeY(canvas.getElement());

                //IMPORTANT: for the background, after onMouseUp, selectionInProgress will be always false!
                boolean allowSelection = !background.selectionInProgress();
                background.onMouseUp(mouseX, mouseY);

                int mouseXAux = allowSelection ? mouseX : -100;//Do not assign the same value to mouseXAux and mouseYAux
                int mouseYAux = allowSelection ? mouseY : -200;//Do not assign the same value to mouseXAux and mouseYAux
                for (Drawable component : components) {
                    if(component instanceof Clickable){
                        Clickable c = (Clickable) component;
                        c.onMouseUp(mouseXAux, mouseYAux);
                    }
                }

                //NOTE: IMPORTANT! doUpdate will change the value of objectSelected
                boolean selectedAux = objectSelected;

                doUpdate(true);

                //If there is NOT object selected the background has been clicked
                if(!objectSelected){
                    //we notify if there has been a "deselection" and if the background properties
                    handlerManager.fireEvent(new BackgroundSelectedEvent(selectedAux, background));
                }
            }
        });
    }
}
