package uk.ac.ebi.pride.widgets.client.sequence.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import uk.ac.ebi.pride.widgets.client.sequence.data.Protein;
import uk.ac.ebi.pride.widgets.client.sequence.model.Sequence;
import uk.ac.ebi.pride.widgets.client.sequence.type.SequenceType;
import uk.ac.ebi.pride.widgets.client.sequence.utils.CanvasProperties;
import uk.ac.ebi.pride.widgets.client.sequence.utils.Tooltip;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
//@SuppressWarnings("UnusedDeclaration")
public class SequenceViewer extends Composite implements HasHandlers {
    private HandlerManager handlerManager;

    //timer refresh rate, in milliseconds
    private static final int REFRESH_RATE = 40;
    private final Timer timer;
    private Canvas canvas;
    private Canvas selectionCanvas;
    private Canvas positionCanvas;
    private AbsolutePanel canvasHolder;

    private Sequence sequence;

    // mouse positions relative to canvas
    int mouseX = -100; int lastMouseX = -200; //Do not assign the same value at the beginning
    int mouseY = -100; int lastMouseY = -200; //Do not assign the same value at the beginning
    boolean mouseDown = false;

    public SequenceViewer(SequenceType sequenceType, Protein protein) {
        this.handlerManager = new HandlerManager(this);
        this.canvas = Canvas.createIfSupported();
        this.positionCanvas = Canvas.createIfSupported();
        this.selectionCanvas = Canvas.createIfSupported();

        this.sequence = new Sequence(sequenceType, protein);
        setCanvasProperties(this.canvas);
        setCanvasProperties(this.selectionCanvas);
        setCanvasProperties(this.positionCanvas);
        CanvasProperties.getCanvasProperties().reset();

        this.setMousePosition(mouseX, mouseY);
        this.draw();

        // setup timer
        timer = new Timer() {
            @Override
            public void run() {
                doUpdate();
            }
        };
        timer.scheduleRepeating(REFRESH_RATE);

        initHandlers();

        canvasHolder = new AbsolutePanel();

        initCanvas();
    }

    private void initCanvas(){
        canvasHolder.add(canvas);
        int left = canvasHolder.getWidgetLeft(canvas);
        int top = canvasHolder.getWidgetTop(canvas);
        canvasHolder.add(selectionCanvas, left, top);
        canvasHolder.add(positionCanvas, left, top);
        initWidget(canvasHolder);
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
        Context2d ctx = this.canvas.getContext2d();
        ctx.setFont("normal 10px courier new");

        //Clean the canvas
        int width = ctx.getCanvas().getWidth();
        int height = ctx.getCanvas().getHeight();
        ctx.clearRect(0, 0, width, height);

        //Draw all the lines
        ctx.setFillStyle("#000000");
        sequence.draw(ctx);
    }

    protected void drawSelection() {
        Context2d ctx = this.selectionCanvas.getContext2d();
        ctx.setFont("normal 10px courier new");

        //Clean the canvas
        int width = ctx.getCanvas().getWidth();
        int height = ctx.getCanvas().getHeight();
        ctx.clearRect(0, 0, width, height);

        //Draw all the lines
        ctx.setFillStyle("#000000");
        sequence.drawSelection(ctx);
    }

    protected void drawPosition() {
        Context2d ctx = this.positionCanvas.getContext2d();
        ctx.setFont("normal 10px courier new");

        //Clean the canvas
        int width = ctx.getCanvas().getWidth();
        int height = ctx.getCanvas().getHeight();
        ctx.clearRect(0, 0, width, height);

        //Draw all the lines
        ctx.setFillStyle("#000000");
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
    private void setCanvasProperties(Canvas canvas){
        int width = CanvasProperties.getCanvasProperties().getMaxWidth();
        int height = CanvasProperties.getCanvasProperties().getMaxHeight();
        canvas.setCoordinateSpaceWidth(width);
        canvas.setCoordinateSpaceHeight(height);
        canvas.setPixelSize(width, height);
    }

    /*
    @Override
    protected void onLoad() {
        super.onLoad();
        int left = canvasHolder.getWidgetLeft(canvas);
        int top = canvasHolder.getWidgetTop(canvas);
        canvasHolder.add(positionCanvas, left, top);
    }
    */
}
