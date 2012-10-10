package uk.ac.ebi.pride.widgets.client.spectrum.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.canvas.dom.client.FillStrokeStyle;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Clickable;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Drawable;
import uk.ac.ebi.pride.widgets.client.spectrum.model.*;


import java.util.ArrayList;
import java.util.List;

public class SpectrumCanvas implements Drawable, Clickable {

    public final static int X_OFFSET = 60;
    public final static int Y_OFFSET = 30;

    final CssColor redrawColor = CssColor.make("rgba(255,255,255, 1)");

    private List<Drawable> components = new ArrayList<Drawable>();

    private int height;
    private int width;

    // mouse positions relative to canvas
    int mouseX = -200; int mouseDownX = -200;
    int mouseY = -200; int mouseDownY = -200;

    private Rank xRank;
    private Rank yRank;

    private XAxis xAxis;
    private YAxis yAxis;
    private SpectrumDrawer spectrumDrawer;

    public SpectrumCanvas(List<Spectrum> spectrumList, int width, int height) {
        this.height = height;
        this.width = width;

        this.xRank =  getXRank(spectrumList);
        this.yRank =  getYRank(spectrumList);

        this.xAxis = new XAxis(X_OFFSET, height - Y_OFFSET, width - 2 * X_OFFSET, this.xRank);
        this.yAxis = new YAxis(X_OFFSET, height - Y_OFFSET, height - 2 * Y_OFFSET, this.yRank);

        this.spectrumDrawer = new SpectrumDrawer(this.xAxis, this.yAxis);
        for (Spectrum spectrum : spectrumList) {
            this.spectrumDrawer.addSpectrum(spectrum);
        }

        components.add(this.spectrumDrawer);
        components.add(this.yAxis);
        components.add(this.xAxis);
    }

    private Rank getXRank(List<Spectrum> spectrumList){
        Double xMin = null;
        Double xMax = null;
        for (Spectrum spectrum : spectrumList) {
            Double minMz = spectrum.getMinMZ();
            xMin = xMin==null || minMz<xMin?minMz:xMin;

            Double maxMz = spectrum.getMaxMZ();
            xMax = xMax==null || maxMz>xMax?maxMz:xMax;
        }
        return new Rank(xMin, xMax);
    }

    private Rank getYRank(List<Spectrum> spectrumList){
        Double yMax = null;
        for (Spectrum spectrum : spectrumList) {
            Double maxIntensity = spectrum.getMaxIntensity();
            yMax = yMax==null || maxIntensity>yMax?maxIntensity:yMax;
        }
        return new Rank(0, yMax, 6);
    }

    public void zoomOverXAxis(int delta) {
        Rank xRank = xAxis.getRank();

        //xLDelta and xRDelta are calculated in order to keep the position under the mouse in the middle when scroll zooming
        double vAux = xAxis.getValueFromPixel(mouseX);
        double xLDelta = ( vAux - xRank.getFrom() ) * 0.1;
        double xRDelta =  (xRank.getTo() - vAux) * 0.1;

        // zoom not restricted
        if(delta<0){ //ZOOM IN
            xAxis.setRank(new Rank(xRank.getFrom() + xLDelta, xRank.getTo() - xRDelta));
        }else{ //ZOOM OUT
            xAxis.setRank(new Rank(xRank.getFrom() - xLDelta, xRank.getTo() + xRDelta));
        }
        spectrumDrawer.update();
    }

    @Override
    public void draw(Context2d ctx) {
        FillStrokeStyle s =  ctx.getFillStyle();

        ctx.setFillStyle(redrawColor);
        ctx.fillRect(0, 0, width, height);
        ctx.setFillStyle(s);

        for (Drawable component : components) {
            component.draw(ctx);
        }

        // drawing ZOOM AREA RECTANGLE
        if(mouseDownX!=-200 && mouseDownY!=-200){
            ctx.setFillStyle(CssColor.make("rgba(0,0,255, .5)"));
            ctx.fillRect(mouseDownX, mouseDownY, mouseX - mouseDownX ,  mouseY - mouseDownY);
            ctx.setFillStyle(s);
        }


    }

    @Override
    public void setMousePosition(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        for (Drawable component : components) {
            component.setMousePosition(mouseX, mouseY);
        }
    }

    @Override
    public void onMouseUp(int mouseX, int mouseY) {
        if(mouseDownX!=-200 && mouseDownY!=-200 && mouseDownX!=mouseX && mouseDownY!=mouseY){
            int xFrom = Math.min(mouseDownX, mouseX);
            int xTo = Math.max(mouseDownX, mouseX);
            xAxis.setRank(new Rank(xAxis.getValueFromPixel(xFrom),xAxis.getValueFromPixel(xTo)));

            int yFrom = Math.max(mouseDownY, mouseY);
            int yTo = Math.min(mouseDownY, mouseY);
            yAxis.setRank(new Rank(yAxis.getValueFromPixel(yFrom),yAxis.getValueFromPixel(yTo), 6));

            spectrumDrawer.update();
        }else{
            spectrumDrawer.onMouseUp(mouseX, mouseY);
        }
        //Every time "onMouseUp" happens, both values have to be set to "-200"
        mouseDownX = -200;
        mouseDownY = -200;
    }

    @Override
    public void onMouseDown(int mouseX, int mouseY) {
        mouseDownX = mouseX;
        mouseDownY = mouseY;
        spectrumDrawer.onMouseDown(mouseX, mouseY);
    }

    public void resetZoom(){
        xAxis.setRank(this.xRank);
        yAxis.setRank(this.yRank);
        spectrumDrawer.update();
    }
}
