package uk.ac.ebi.pride.widgets.client.spectrum.model;

class BinnedSpectrum {
    private XAxis xAxis;
    private Spectrum spectrum;

    SpectrumPeak[] binnedPeaks;

    BinnedSpectrum(XAxis xAxis, Spectrum spectrum) {
        this.xAxis = xAxis;
        this.spectrum = spectrum;
        update();
    }

    public Integer getBinsNumber() {
        return binnedPeaks.length;
    }

    public SpectrumPeak get(int bin){
        try{
            return binnedPeaks[bin];
        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    public void update() {
        this.binnedPeaks = new SpectrumPeak[xAxis.getBinsNumber()];
        for (SpectrumPeak peak : spectrum.peakList) {
            if(peak.getMz()<xAxis.getRank().getFrom() || peak.getMz()>xAxis.getRank().getTo())
                continue;
            int bin = xAxis.getBinFromValue(peak.getMz());

            SpectrumPeak aux = binnedPeaks[bin];
            if(aux==null){
                binnedPeaks[bin]= peak;
            }else{
                if(peak.getIntensity() > binnedPeaks[bin].getIntensity()){
                    binnedPeaks[bin] = peak;
                }
            }
        }
    }
}