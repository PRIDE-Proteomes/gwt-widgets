package uk.ac.ebi.pride.widgets.test.data.model;

import java.util.List;

public interface PeakList {

    long getSpectrumId();
    void setSpectrumId(long spectrumId);

    /*List<Double> getMzArray();
    void setMzArray(List<Double> mzArray);*/

/*
    List<Double> getIntensityArray();
    void setIntensityArray(List<Double> intensityArray);
*/

    List<Peak> getPeaks();
    void setPeaks(List<Peak> peaks);

    Double getMzStart();
    void setMzStart(Double mzStart);

    Double getMzStop();
    void setMzStop(Double mzStop);
}
