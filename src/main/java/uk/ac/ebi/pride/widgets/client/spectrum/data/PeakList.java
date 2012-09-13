package uk.ac.ebi.pride.widgets.client.spectrum.data;

import java.util.List;

public interface PeakList {

    long getSpectrumId();

    List<Peak> getPeaks();

    Double getMzStart();

    Double getMzStop();
}