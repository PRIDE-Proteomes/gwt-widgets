package uk.ac.ebi.pride.widgets.client.spectrum.model;

import java.util.List;

public class Spectrum {

    final private Double minMZ;
    final private Double maxMZ;
    private Double maxIntensity;
    SpectrumPeak[] peakList;

    public Spectrum(List<SpectrumPeak> peaks) {
        if(peaks !=null && !peaks.isEmpty()){
            this.minMZ = peaks.get(0).getMz();
            this.maxMZ = peaks.get(peaks.size()-1).getMz();
            this.maxIntensity = 0.0;
            peakList = new SpectrumPeak[peaks.size()];
        }else{
            this.minMZ = this.maxMZ = this.maxIntensity = null;
            peakList = new SpectrumPeak[0];
        }

        for(int i=0; i< peakList.length; ++i){
            Double intensity = peaks.get(i).getIntensity();
            if(intensity>this.maxIntensity) this.maxIntensity = intensity;
            peakList[i] = peaks.get(i);
        }
    }

    public Double getMinMZ() {
        return minMZ;
    }

    public Double getMaxMZ() {
        return maxMZ;
    }

    public Double getMaxIntensity() {
        return maxIntensity;
    }

    @Override
    public String toString() {
        return "Spectrum{" +
                "minMZ=" + minMZ +
                ", maxMZ=" + maxMZ +
                '}';
    }
}
