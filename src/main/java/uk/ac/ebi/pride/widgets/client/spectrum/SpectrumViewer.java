package uk.ac.ebi.pride.widgets.client.spectrum;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.*;
import uk.ac.ebi.pride.widgets.client.spectrum.data.Peak;
import uk.ac.ebi.pride.widgets.client.spectrum.data.PeakList;
import uk.ac.ebi.pride.widgets.client.spectrum.model.Spectrum;
import uk.ac.ebi.pride.widgets.client.spectrum.model.SpectrumPeak;


import java.util.ArrayList;
import java.util.List;

public class SpectrumViewer extends Composite {
    //timer refresh rate, in milliseconds
    static final int REFRESH_RATE = 25;
    final Timer timer;

    private Canvas canvas;
    private SpectrumCanvas spectrumCanvas;
    private List<Spectrum> spectrumList;

    // mouse positions relative to canvas
    int mouseX = 0; int lastMouseX = -200; //Do not assign the same value at the beginning
    int mouseY = 0; int lastMouseY = -200; //Do not assign the same value at the beginning

    public SpectrumViewer(List<PeakList> peaksList){
        this(peaksList, 900, 290);
    }

    public SpectrumViewer(List<PeakList> peaksList, int width, int height){
        canvas = Canvas.createIfSupported();
        canvas.setPixelSize(width, height);
        canvas.setCoordinateSpaceWidth(width);
        canvas.setCoordinateSpaceHeight(height);

        initSpectrumCanvas(peaksList, width, height);

        // setup timer
        timer = new Timer() {
            @Override
            public void run() {
                doUpdate();
            }
        };
        timer.scheduleRepeating(REFRESH_RATE);

        // init handlers
        initHandlers();

        VerticalPanel vp = new VerticalPanel();
        vp.getElement().getStyle().setBorderWidth(0, Style.Unit.PX);

        Button resetBtn = new Button("Reset zoom");
        resetBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                spectrumCanvas.resetZoom();
                spectrumCanvas.draw(canvas.getContext2d());
            }
        });
        vp.add(resetBtn);
        vp.add(canvas);

        initWidget(vp);
    }

    private void initSpectrumCanvas(List<PeakList> peaksList, int width, int height){
        spectrumList = new ArrayList<Spectrum>();
        for (PeakList peakList : peaksList) {
            List<SpectrumPeak> peaks = new ArrayList<SpectrumPeak>();
            for (Peak peak : peakList.getPeaks()) {
                peaks.add(new SpectrumPeak(peak));
            }
           spectrumList.add(new Spectrum(peaks));
        }

        spectrumCanvas = new SpectrumCanvas(spectrumList, width, height);
    }

    public synchronized void doUpdate(){
        if(mouseX==lastMouseX && mouseY==lastMouseY) return;
        lastMouseX = mouseX;
        lastMouseY = mouseY;
        spectrumCanvas.setMousePosition(mouseX, mouseY);
        spectrumCanvas.draw(canvas.getContext2d());
    }

    void initHandlers() {
        canvas.addMouseWheelHandler(new MouseWheelHandler() {
            @Override
            public void onMouseWheel(MouseWheelEvent event) {
                event.preventDefault();
                mouseX = event.getRelativeX(canvas.getElement());
                mouseY = event.getRelativeY(canvas.getElement());
                spectrumCanvas.setMousePosition(mouseX, mouseY);
                spectrumCanvas.zoomOverXAxis(event.getDeltaY());
                spectrumCanvas.draw(canvas.getContext2d());
            }
        });

        canvas.addMouseUpHandler(new MouseUpHandler() {
            @Override
            public void onMouseUp(MouseUpEvent event) {
                mouseX = event.getRelativeX(canvas.getElement());
                mouseY = event.getRelativeY(canvas.getElement());
                spectrumCanvas.onMouseUp(mouseX, mouseY);
                spectrumCanvas.draw(canvas.getContext2d());
            }
        });

        canvas.addMouseDownHandler(new MouseDownHandler() {
            @Override
            public void onMouseDown(MouseDownEvent event) {
                mouseX = event.getRelativeX(canvas.getElement());
                mouseY = event.getRelativeY(canvas.getElement());
                spectrumCanvas.onMouseDown(mouseX, mouseY);
            }
        });

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

        canvas.addTouchMoveHandler(new TouchMoveHandler() {
            public void onTouchMove(TouchMoveEvent event) {
                event.preventDefault();
                if (event.getTouches().length() > 0) {
                    Touch touch = event.getTouches().get(0);
                    mouseX = touch.getRelativeX(canvas.getElement());
                    mouseY = touch.getRelativeY(canvas.getElement());
                }
            }
        });

        canvas.addTouchEndHandler(new TouchEndHandler() {
            public void onTouchEnd(TouchEndEvent event) {
                event.preventDefault();
                mouseX = -200;
                mouseY = -200;
            }
        });

        canvas.addGestureStartHandler(new GestureStartHandler() {
            public void onGestureStart(GestureStartEvent event) {
                event.preventDefault();
            }
        });
    }
}
