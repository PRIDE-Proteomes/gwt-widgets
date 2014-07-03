package uk.ac.ebi.pride.widgets.test.data.model;

public interface PrideModification {

    int getId();
    void setId(int id);

    //i.e. Oxidation
    String getName();
    void setName(String name);

    Double getDiffMono();
    void setDiffMono(Double diffMono);

    boolean isBioSignificance();
    void setBioSignificance(boolean bioSignificance);

}
