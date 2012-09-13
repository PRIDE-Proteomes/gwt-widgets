package uk.ac.ebi.pride.widgets.client.spectrum.model;

public class SpectrumPeak {
    private Double mz;
    private Double intensity;
    private String annotation;

    public SpectrumPeak(uk.ac.ebi.pride.widgets.client.spectrum.data.Peak peak){
        this.mz = peak.getMz();
        this.intensity = peak.getIntensity();
    }

    public SpectrumPeak(Double mz, Double intensity) {
        this.mz = mz;
        this.intensity = intensity;
    }

    public SpectrumPeak(Double mz, Double intensity, String notation) {
        this(mz, intensity);
        this.annotation = notation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public Double getMz() {
        return mz;
    }

    public Double getIntensity() {
        return intensity;
    }

    public String getAnnotation() {
        return annotation;
    }

    @Override
    public String toString() {
        return "SpectrumPeak{" +
                "mz=" + mz +
                ", intensity=" + intensity +
                ", annotation='" + annotation + '\'' +
                '}';
    }
}
