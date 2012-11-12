package uk.ac.ebi.pride.widgets.client.spectrum.model;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Clickable;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Drawable;


import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Convert2Diamond")
public class SpectrumDrawer implements Drawable, Clickable {
    private static final String PEAK_DEFAULT_COLOR = "red";
    private static final String PEAK_SELECTED_COLOR = "blue";

    private XAxis xAxis;
    private YAxis yAxis;

    // contains the complete spectrum data
    private List<Spectrum> spectrumList = new ArrayList<Spectrum>();

    // contains the binned spectrum data
    private List<BinnedSpectrum> binnedSpectrumList = new ArrayList<BinnedSpectrum>();

    // mouse positions relative to canvas
    int mouseX, mouseY;

    public SpectrumDrawer(XAxis xAxis, YAxis yAxis) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        filter();
    }

    public void addSpectrum(Spectrum spectrum) {
        spectrumList.add(spectrum);
        binnedSpectrumList.add(new BinnedSpectrum(xAxis, spectrum));
    }

    private void filter(){
        int xBins = xAxis.getBinsNumber();
    }

    public void update(){
        for (BinnedSpectrum binnedSpectrum : binnedSpectrumList) {
            binnedSpectrum.update();
        }
    }

    @Override
    public void draw(Context2d ctx) {
        for (BinnedSpectrum binnedSpectrum : binnedSpectrumList) {
            for(int bin=0; bin<binnedSpectrum.getBinsNumber(); ++bin){
                SpectrumPeak peak = binnedSpectrum.get(bin);
                if(peak ==null) continue;

                double peakX = xAxis.getBinX(bin);
                double peakY = Math.max(yAxis.getPixelFromValue(peak.getIntensity()), yAxis.getY()- yAxis.getHeight());
                peakY = Math.min(peakY, xAxis.getY());

                ctx.beginPath();
                ctx.setStrokeStyle(getPeakColor(peakX, peakY));
                ctx.setLineWidth(XAxis.BIN_WIDTH);
                ctx.moveTo(peakX, xAxis.getY());
                ctx.lineTo(peakX, peakY);
                ctx.closePath();
                ctx.stroke();
            }
        }
    }

    private String getPeakColor(double peakX, double  peakY){
        return isMouseOver(peakX, peakY) ? PEAK_SELECTED_COLOR : PEAK_DEFAULT_COLOR;
    }

    private boolean isMouseOver(double peakX, double peakY){
        double y = xAxis.getY();
        return mouseX > peakX && (peakX + XAxis.BIN_WIDTH) >= mouseX && y >= mouseY  && mouseY >= peakY;
    }

    @Override
    public void setMousePosition(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    @Override
    public boolean isMouseOver() {
        return false;
    }

    @Override
    public void onMouseUp(int mouseX, int mouseY) {
        setMousePosition(mouseX, mouseY);
        for (BinnedSpectrum binnedSpectrum : binnedSpectrumList) {
            for(int bin=0; bin<binnedSpectrum.getBinsNumber(); ++bin){
                SpectrumPeak peak = binnedSpectrum.get(bin);
                if(peak ==null) continue;
                double peakX = xAxis.getBinX(bin);
                double peakY = yAxis.getPixelFromValue(peak.getIntensity());
                if(isMouseOver(peakX, peakY)){
                    if(!GWT.isScript()) System.out.println(peak);
                }
            }
        }
        if(!GWT.isScript()) System.out.println("--");
    }

    @Override
    public void onMouseDown(int mouseX, int mouseY) {
        setMousePosition(mouseX, mouseY);
    }

    @Override
    public boolean isSelected() {
        return false;
    }
}