package uk.ac.ebi.pride.widgets.test.data.model;

public interface Peak {

    Double getMz();
    void setMz(Double mz);

    Double getIntensity();
    void setIntensity(Double intensity);

    Double getAnnotation();
    void setAnnotation(String annotation);

}
