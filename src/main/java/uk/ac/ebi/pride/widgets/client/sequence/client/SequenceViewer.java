package uk.ac.ebi.pride.widgets.client.sequence.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import uk.ac.ebi.pride.widgets.client.common.handler.PeptideHandler;
import uk.ac.ebi.pride.widgets.client.common.handler.PrideModificationHandler;
import uk.ac.ebi.pride.widgets.client.common.handler.ProteinHandler;
import uk.ac.ebi.pride.widgets.client.sequence.events.ProteinPositionHighlightedEvent;
import uk.ac.ebi.pride.widgets.client.sequence.events.ProteinRegionHighlightedEvent;
import uk.ac.ebi.pride.widgets.client.sequence.events.ProteinRegionResetEvent;
import uk.ac.ebi.pride.widgets.client.sequence.events.ProteinRegionSelectionEvent;
import uk.ac.ebi.pride.widgets.client.sequence.handlers.ProteinPositionHighlightedHandler;
import uk.ac.ebi.pride.widgets.client.sequence.handlers.ProteinRegionHighlightedHandler;
import uk.ac.ebi.pride.widgets.client.sequence.handlers.ProteinRegionResetHandler;
import uk.ac.ebi.pride.widgets.client.sequence.handlers.ProteinRegionSelectedHandler;
import uk.ac.ebi.pride.widgets.client.sequence.model.ProteinSummary;
import uk.ac.ebi.pride.widgets.client.sequence.model.Sequence;
import uk.ac.ebi.pride.widgets.client.sequence.type.SequenceType;
import uk.ac.ebi.pride.widgets.client.sequence.utils.CanvasProperties;
import uk.ac.ebi.pride.widgets.client.sequence.utils.Tooltip;

import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("UnusedDeclaration")
public class SequenceViewer extends Composite implements HasHandlers {
    public static final CssColor NONE_SELECTED_COLOR = CssColor.make("rgba(255,255,255, 0.5)");

    private HandlerManager handlerManager;

    private boolean peptidesVisible = true;

    //timer refresh rate, in milliseconds
    private static final int REFRESH_RATE = 40;
    private int width, height;

    private Canvas peptidesCanvas;
    private Canvas selectionCanvas;
    private Canvas mainCanvas;
    private Canvas positionCanvas;
    private Canvas modificationCanvas;

    private Sequence sequence;

    // mouse positions relative to canvas
    int mouseX = -100; int lastMouseX = -200; //Do not assign the same value at the beginning
    int mouseY = -100; int lastMouseY = -200; //Do not assign the same value at the beginning
    boolean mouseDown = false;

    public SequenceViewer(SequenceType sequenceType, ProteinHandler proteinHandler) {
        this.handlerManager = new HandlerManager(this);
        this.peptidesCanvas = Canvas.createIfSupported();
        this.selectionCanvas = Canvas.createIfSupported();
        this.mainCanvas = Canvas.createIfSupported();
        this.modificationCanvas = Canvas.createIfSupported();
        this.positionCanvas = Canvas.createIfSupported();

        ProteinSummary proteinSummary = new ProteinSummary(proteinHandler);
        this.sequence = new Sequence(sequenceType, proteinSummary, this.handlerManager);
        this.width = CanvasProperties.getCanvasProperties().getMaxWidth();
        this.height = CanvasProperties.getCanvasProperties().getMaxHeight();
        setCanvasProperties(this.peptidesCanvas, this.width, this.height);
        setCanvasProperties(this.selectionCanvas, this.width, this.height);
        setCanvasProperties(this.mainCanvas, this.width, this.height);
        setCanvasProperties(this.modificationCanvas, this.width, this.height);
        setCanvasProperties(this.positionCanvas, this.width, this.height);
        CanvasProperties.getCanvasProperties().reset();

        initCanvas();

        this.setMousePosition(mouseX, mouseY);
        this.drawPeptides();
        this.draw();
        this.drawModifications();

        initHandlers();

        // setup timer
        Timer timer = new Timer() {
            @Override
            public void run() {
                doUpdate();
            }
        };
        timer.scheduleRepeating(REFRESH_RATE);
    }

    private void initCanvas(){
        AbsolutePanel canvasHolder = new AbsolutePanel();
        canvasHolder.add(this.peptidesCanvas);
        //canvasHolder.add(this.selectionCanvas, 0, 0);
        canvasHolder.add(this.mainCanvas, 0, 0);
        canvasHolder.add(this.modificationCanvas, 0, 0);
        canvasHolder.add(this.selectionCanvas, 0, 0);
        canvasHolder.add(this.positionCanvas, 0, 0);
        initWidget(canvasHolder);
    }

    public HandlerRegistration addProteinPositionHighlightedHandler(ProteinPositionHighlightedHandler handler){
        return handlerManager.addHandler(ProteinPositionHighlightedEvent.TYPE, handler);
    }

    public HandlerRegistration addProteinRegionHighlightedHandler(ProteinRegionHighlightedHandler handler){
        return handlerManager.addHandler(ProteinRegionHighlightedEvent.TYPE, handler);
    }

    public HandlerRegistration addProteinRegionResetHandler(ProteinRegionResetHandler handler){
        return handlerManager.addHandler(ProteinRegionResetEvent.TYPE, handler);
    }

    public HandlerRegistration addProteinRegionSelectedHandler(ProteinRegionSelectedHandler handler) {
        return handlerManager.addHandler(ProteinRegionSelectionEvent.TYPE, handler);
    }

    public void resetSelection(){
        this.sequence.resetSelection();
        drawSelection();
    }

    public void selectRegion(int start, int end){
        this.sequence.selectRegion(start, end);
        drawSelection();
    }

    public void filterModification(PrideModificationHandler prideModification){
        this.sequence.setHighlightedModification(prideModification);
        drawModifications();
    }

    public void highlightModificationBeetween(PrideModificationHandler prideModification, int start, int end) {
        this.sequence.setHighlightedModification(prideModification, start, end);
        drawModifications();
    }

    public void highlightModification(int modPosition) {
        this.sequence.setHighlightedModification(modPosition);
        drawModifications();
    }

    public void setHighlightedModifications(List<Integer> modPositions) {

        this.resetModification();

        for (Integer modPosition : modPositions) {
            //FIX: Only the last one is highlighted
            this.sequence.setHighlightedModification(modPosition);
        }

        drawModifications();

    }

    public void setSelectedModifications(List<Integer> modPositions) {

        this.resetModification();
        this.resetSelection();

        for (Integer modPosition : modPositions) {
            //FIX: Only the last one is highlighted
            this.sequence.setSelectedModification(modPosition);
            this.sequence.selectRegion(modPosition, modPosition);
        }

        drawModifications();
        drawSelection();

    }

    public void setHighlightedModifications(List<PrideModificationHandler> modifications, List<PeptideHandler> peptides) {

        this.resetModification();

        for (PrideModificationHandler modification : modifications) {
            for (PeptideHandler peptide : peptides) {
                int startRegion = peptide.getSite();
                int endRegion = peptide.getEnd();

                //FIX: Only the last one is highlighted
                this.sequence.setHighlightedModification(modification, startRegion, endRegion);
            }
        }

        drawModifications();

    }

    public void resetModification(){
        this.sequence.resetPeptidesFilter();
        drawModifications();
    }

    public void showPeptides(){
        this.peptidesVisible = true;
        drawPeptides();
    }

    public void hidePeptides(){
        this.peptidesVisible = false;
        drawPeptides();
    }

    public void setVisiblePeptide(PeptideHandler peptide){
        if (peptide.getSite() > 0 && peptide.getEnd() <= this.sequence.getSequenceLength()) {
            this.sequence.setVisiblePeptide(peptide);
            drawPeptides();
        } // else: ignore this peptide, as it is outside the protein sequence scope
        else {
            resetVisiblePeptides();
        }
    }

    public void resetVisiblePeptides(){
        this.sequence.resetPeptidesFilter();
        drawPeptides();
    }

    public void setHighlightedPeptide(PeptideHandler peptide){

        if (peptide.getSite() > 0 && peptide.getEnd() <= this.sequence.getSequenceLength()) {
            this.sequence.setHighlightedPeptide(peptide);
            drawPeptides();
        }
    }

    public void setHighlightedPeptides(List<PeptideHandler> peptides) {

        this.sequence.resetPeptidesFilter();

        for (PeptideHandler peptide : peptides) {
            if (peptide.getSite() > 0 && peptide.getEnd() <= this.sequence.getSequenceLength()) {
                this.sequence.setHighlightedPeptide(peptide);
            } // else: ignore this peptide, as it is outside the protein sequence scope
        }


        drawPeptides();

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

        if(mouseDown)
            this.drawSelection();

        this.drawPosition();
    }


    protected void initHandlers() {
        positionCanvas.addMouseMoveHandler(new MouseMoveHandler() {
            public void onMouseMove(MouseMoveEvent event) {
                mouseX = event.getRelativeX(positionCanvas.getElement());
                mouseY = event.getRelativeY(positionCanvas.getElement());
            }
        });

        positionCanvas.addMouseOutHandler(new MouseOutHandler() {
            public void onMouseOut(MouseOutEvent event) {
                mouseX = -200;
                mouseY = -200;
                Tooltip.getTooltip().hide();
            }
        });

        positionCanvas.addMouseDownHandler(new MouseDownHandler() {
            @Override
            public void onMouseDown(MouseDownEvent event) {
                mouseDown = true;
                int mouseX = event.getRelativeX(positionCanvas.getElement());
                int mouseY = event.getRelativeY(positionCanvas.getElement());
                sequence.onMouseDown(mouseX, mouseY);
                doUpdate(true);
            }
        });

        positionCanvas.addMouseUpHandler(new MouseUpHandler() {
            @Override
            public void onMouseUp(MouseUpEvent event) {
                mouseDown = false;
                int mouseX = event.getRelativeX(positionCanvas.getElement());
                int mouseY = event.getRelativeY(positionCanvas.getElement());
                sequence.onMouseUp(mouseX, mouseY);
            }
        });
    }

    protected void setMousePosition(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        sequence.setMousePosition(mouseX, mouseY);
    }

    protected void draw() {
        Context2d ctx = this.mainCanvas.getContext2d();
        //Clean the canvas
        ctx.clearRect(0, 0, this.width, this.height);
        //Draw all the sequence
        sequence.draw(ctx);
    }

    protected void drawPeptides() {
        Context2d ctx = this.peptidesCanvas.getContext2d();
        //Clean the canvas
        ctx.clearRect(0, 0, this.width, this.height);
        //Draw all the peptides
        if(peptidesVisible){
            sequence.drawPeptides(ctx);
        }
    }

    protected void drawSelection() {
        Context2d ctx = this.selectionCanvas.getContext2d();
        //Clean the canvas
        ctx.clearRect(0, 0, this.width, this.height);
        if(sequence.isSelected()){
            ctx.setFillStyle(NONE_SELECTED_COLOR);
            ctx.fillRect(0, 0, this.width, this.height);
        }

        //Draw all the selection
        sequence.drawSelection(ctx);
    }

    protected void drawModifications(){
        Context2d ctx = this.modificationCanvas.getContext2d();
        //Clean the canvas
        ctx.clearRect(0, 0, this.width, this.height);
        //Draw all the modifications
        sequence.drawModifications(ctx);
    }

    protected void drawPosition() {
        Context2d ctx = this.positionCanvas.getContext2d();
        //Clean the canvas
        ctx.clearRect(0, 0, this.width, this.height);
        //Draw all the mouse position highlight
        sequence.drawPosition(ctx);
    }

    /*
    public void setSequenceType(SequenceType sequenceType) {
        if(this.sequenceType!=sequenceType){
            this.sequenceType = sequenceType;
            doUpdate(true);
        }
    }*/

    //INITIALIZE THE CANVAS taking into account the CanvasProperties
    private void setCanvasProperties(Canvas canvas, int width, int height){
        canvas.setCoordinateSpaceWidth(width);
        canvas.setCoordinateSpaceHeight(height);
        canvas.setPixelSize(width, height);
    }
}
