package uk.ac.ebi.pride.widgets.client.protein.client;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import uk.ac.ebi.pride.widgets.client.common.handler.PeptideHandler;
import uk.ac.ebi.pride.widgets.client.common.handler.PrideModificationHandler;
import uk.ac.ebi.pride.widgets.client.common.handler.ProteinHandler;
import uk.ac.ebi.pride.widgets.client.common.handler.ProteinModificationHandler;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Drawable;
import uk.ac.ebi.pride.widgets.client.protein.events.*;
import uk.ac.ebi.pride.widgets.client.protein.handlers.*;
import uk.ac.ebi.pride.widgets.client.protein.interfaces.Animated;
import uk.ac.ebi.pride.widgets.client.protein.interfaces.Clickable;
import uk.ac.ebi.pride.widgets.client.protein.model.*;
import uk.ac.ebi.pride.widgets.client.protein.utils.CanvasProperties;
import uk.ac.ebi.pride.widgets.client.protein.utils.PeptideBaseFactory;
import uk.ac.ebi.pride.widgets.client.protein.utils.RegionUtils;

import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public class ProteinViewer extends Composite implements HasHandlers {
    HandlerManager handlerManager;

    private boolean proteinBorder;

    //timer refresh rate, in milliseconds
    static final int REFRESH_RATE = 25;

    final Timer timer;
    private Canvas canvas;
    private boolean objectSelected = false;

    private boolean animated = true;
    // The duration of the animation.
    private static int ANIMATION_DURATION = 3000;

    private ProteinAreaSelection proteinSelection;
    @SuppressWarnings("Convert2Diamond")
    private List<Drawable> components = new LinkedList<Drawable>();

    // mouse positions relative to canvas
    int mouseX = -100; int lastMouseX = -200; //Do not assign the same value at the beginning
    int mouseY = -100; int lastMouseY = -200; //Do not assign the same value at the beginning

    protected boolean isMoving = false;
    private static ContentAnimation contentAnimation;

    private static class ContentAnimation extends Animation {
        private ProteinViewer viewer;

        public void startAnimation(ProteinViewer viewer, boolean animate){
            // Immediately complete previous animation
            cancel();

            this.viewer = viewer;
            this.viewer.isMoving = animate;
            if(animate){
                run(ANIMATION_DURATION);
            }else{
                viewer.draw();
            }
        }

        @Override
        protected void onUpdate(double progress) {
            this.viewer.drawAnimation(progress);
        }

        @Override
        protected void onComplete() {
            super.onComplete();
            this.viewer.isMoving = false;
            this.viewer.doUpdate(true);
        }
    }

    public ProteinViewer(ProteinHandler proteinHandler) {
        this(proteinHandler, false, false);
    }

    public ProteinViewer(ProteinHandler proteinHandler, boolean proteinBorder, boolean naturalSelection) {
        this(900, 90, proteinHandler, proteinBorder, naturalSelection);
    }

    public ProteinViewer(int width, int height, ProteinHandler proteinHandler) {
        this(width, height, proteinHandler, false, false);
    }

    public ProteinViewer(int width, int height, ProteinHandler proteinHandler, boolean proteinBorder, boolean naturalSelection) {
        this.canvas = Canvas.createIfSupported();
        this.canvas.setPixelSize(width, height);
        this.canvas.setCoordinateSpaceWidth(width);
        this.canvas.setCoordinateSpaceHeight(height);

        this.handlerManager = new HandlerManager(this);
        this.proteinBorder = proteinBorder;

        CanvasProperties canvasProperties = new CanvasProperties(proteinHandler, this.canvas);
        ProteinSummary proteinSummary = new ProteinSummary(proteinHandler);

        this.proteinSelection = new ProteinAreaSelection(canvasProperties, naturalSelection);
        this.proteinSelection.setHandlerManager(this.handlerManager);

        ProteinAxis pa = new ProteinAxis(canvasProperties, proteinBorder);
        this.components.add(pa);

        List<SequenceRegion> sequenceRegions = RegionUtils.getSequenceRegions(canvasProperties);
        for (SequenceRegion sr : sequenceRegions) {
            sr.setHandlerManager(this.handlerManager);
            components.add(sr);
        }

        if(proteinBorder){
            components.add(new ProteinBorder(canvasProperties));
            /*Not gonna be used in a near future, so no longer maintained
            List<CoveredSequenceBorder> borders = RegionUtils.getCoveredSequenceBorder(sequenceRegions, canvasProperties);
            for (CoveredSequenceBorder border : borders) {
                components.add(border);
            }*/
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

        ProteinViewer.contentAnimation = new ContentAnimation();
        ProteinViewer.contentAnimation.startAnimation(this, animated);

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

    public HandlerRegistration addProteinAreaSelectedHandler(ProteinAreaSelectedHandler handler){
        return handlerManager.addHandler(ProteinAreaSelectedEvent.TYPE, handler);
    }

    public HandlerRegistration addProteinAreaHighlightedHandler(ProteinAreaHighlightedHandler handler){
        return handlerManager.addHandler(ProteinAreaHighlightEvent.TYPE, handler);
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    public static void setAnimationDuration(int animationDuration) {
        ProteinViewer.ANIMATION_DURATION = animationDuration;
    }

    public void setSelectedArea(int start, int end){
        this.proteinSelection.setSelectedArea(start, end);
        //Next lines set selected an covered region that fits with the selected area
        int length = end - start + 1;
        for (Drawable component : components) {
            if(component instanceof CoveredSequenceRegion){
                CoveredSequenceRegion csr = (CoveredSequenceRegion) component;
                csr.setSelected((csr.getStart() == start && csr.getLength() == length));
                /*if (csr.getStart() == start && csr.getLength() == length){
                    csr.setSelected(true);
                }
                //As soon as we reach some region starting after the "start" value, do NOT need to continue ;)
                if(csr.getStart()>start) break;*/
            }
        }
        doUpdate(true);
    }

    public void resetSelectedArea(){
        this.proteinSelection.resetSelectedArea();
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
                region.setSelected(false);
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

        this.proteinSelection.setMousePosition(mouseX, mouseY);
        boolean objectHover = this.proteinSelection.isMouseOver();
        for (Drawable component : this.components) {
            component.setMousePosition(mouseX, mouseY);
            if(component instanceof Clickable){
                Clickable c = (Clickable) component;
                objectHover = objectHover || c.isMouseOver();
            }
        }
        this.setCursorStyle(objectHover);
    }

    private void setCursorStyle(boolean objectHover){
        Style.Cursor cursor = Style.Cursor.AUTO;

        if(this.proteinSelection.isMovingSelectedRegion()){
            cursor = Style.Cursor.MOVE;
        }else if(objectHover){
            cursor = Style.Cursor.POINTER;
        }else if(this.proteinSelection.isSelectionInProgress()){
            if(this.proteinSelection.getSelectionDirection()>0){
                cursor = Style.Cursor.E_RESIZE;
            }else if(this.proteinSelection.getSelectionDirection()<0){
                cursor = Style.Cursor.W_RESIZE;
            }
        }

        this.getElement().getStyle().setCursor(cursor);
    }

    private void cleanCanvas(Context2d ctx){
        if(this.proteinBorder){
            ctx.setFillStyle(CssColor.make("rgba(255,255,255, 1)"));
        }else{
            ctx.setFillStyle(CssColor.make("rgba(240,240,240, 1)"));
        }
        int width = ctx.getCanvas().getWidth();
        int height = ctx.getCanvas().getHeight();
        ctx.fillRect(0, 0, width, height);
    }

    protected void draw() {
        //While the widget is animated, no other drawing action are executed
        if(this.isMoving) return;

        Context2d ctx = canvas.getContext2d();
        this.cleanCanvas(ctx);

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
        //ProteinAreaSelection is drawn on top of everything as a new requirement :)
        this.proteinSelection.draw(ctx);
    }

    protected void drawAnimation(double progress){
        Context2d ctx = canvas.getContext2d();
        this.cleanCanvas(ctx);
        for (Drawable component : this.components) {
            if(component instanceof Animated){
                Animated a = (Animated) component;
                a.drawAnimation(ctx, progress);
            }
        }
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        handlerManager.fireEvent(event);
    }

    private void mouseLeftButtonDown(MouseDownEvent event){
        boolean objectSelected = false;
        if(!this.proteinSelection.isSelectionInProgress()){
            for (Drawable component : this.components) {
                if(component instanceof Clickable){
                    Clickable c = (Clickable) component;
                    c.onMouseDown(this.mouseX, this.mouseY);
                    objectSelected = objectSelected || c.isMouseOver();
                }
            }
        }
        if(!objectSelected){
            this.proteinSelection.onMouseDown(this.mouseX, this.mouseY);
        }

        doUpdate(true);

        event.stopPropagation();
    }

    private void mouseRightButtonDown(MouseDownEvent event){
        //Nothing here
    }

    private void mouseLeftButtonUp(MouseUpEvent event){
        //IMPORTANT: After adding the moving the selected area, it is important to take into account that now
        boolean afterMoving = this.proteinSelection.isSelectedRegionMoved();
        //IMPORTANT: for the proteinSelection, after onMouseUp, isSelectionInProgress will be always false!
        boolean allowSelection = !this.proteinSelection.isSelectionInProgress() && !afterMoving;
        this.proteinSelection.onMouseUp(this.mouseX, this.mouseY);

        //resetObjectSelection is gonna be used in case of fire the ProteinAreaSelectedEvent
        boolean resetObjectSelection;
        if(afterMoving){
            //By default, the widget will never reset any existing selected object when moving the selected area
            resetObjectSelection = false;
        }else{ //Only if the user has not moved the selected area
            int mouseXAux = allowSelection ? this.mouseX : -100;//Do not assign the same value to mouseXAux and mouseYAux
            int mouseYAux = allowSelection ? this.mouseY : -200;//Do not assign the same value to mouseXAux and mouseYAux
            for (Drawable component : this.components) {
                if(component instanceof Clickable){
                    Clickable c = (Clickable) component;
                    c.onMouseUp(mouseXAux, mouseYAux);
                }
            }
            //NOTE: IMPORTANT! doUpdate will change the value of objectSelected
            resetObjectSelection = this.objectSelected;
        }

        doUpdate(true);

        //If there is an object selected after doUpdate in "mouseUpHandler" means new selection
        if(this.objectSelected && !afterMoving){
            //Iterating is necessary in order to fire the event when all the objects has been already redrawn
            for (Drawable component : this.components) {
                if(component instanceof Clickable){
                    Clickable c = (Clickable) component;
                    //fireSelectionEvent only fires object selected event for a new object selected
                    c.fireSelectionEvent();
                }
            }
            //If there is NOT object selected the proteinSelection has been clicked
        }else{
            //we notify if there has been a "deselection" and if the proteinSelection properties
            //System.out.println(new ProteinAreaSelectedEvent(resetObjectSelection, proteinSelection).toString());
            this.handlerManager.fireEvent(new ProteinAreaSelectedEvent(resetObjectSelection, this.proteinSelection));
        }

        event.stopPropagation();
    }

    private void mouseRightButtonUp(MouseUpEvent event){
        //Nothing here
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

                switch (event.getNativeEvent().getButton()){
                    case NativeEvent.BUTTON_LEFT:
                        mouseLeftButtonDown(event);
                        break;
                    case NativeEvent.BUTTON_MIDDLE:
                        break;
                    case NativeEvent.BUTTON_RIGHT:
                        mouseRightButtonDown(event);
                        break;
                }
            }
        });

        canvas.addMouseUpHandler(new MouseUpHandler() {
            @Override
            public void onMouseUp(MouseUpEvent event) {
                mouseX = event.getRelativeX(canvas.getElement());
                mouseY = event.getRelativeY(canvas.getElement());

                switch (event.getNativeEvent().getButton()){
                    case NativeEvent.BUTTON_LEFT:
                        mouseLeftButtonUp(event);
                        break;
                    case NativeEvent.BUTTON_MIDDLE:
                        break;
                    case NativeEvent.BUTTON_RIGHT:
                        mouseRightButtonUp(event);
                        break;
                }
            }
        });
    }
}
