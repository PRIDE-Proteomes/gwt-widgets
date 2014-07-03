package uk.ac.ebi.pride.widgets.test.data.model;

public interface ProteinModification {

    String getProteinId();
    void setProteinId(String proteinId);

    PrideModification getPrideModification();
    void setPrideModification(PrideModification prideModification);

    Integer getSite();
    void setSite(Integer site);

    //How many modifications of prideModification type are into the protein.
    Integer getCount();
    void setCount(Integer count);

    Integer getUniqueness();
    void setUniqueness(Integer uniqueness);

    Double getPrideScore();
    void setPrideScore(Double prideScore);

    Double getMascotScore();
    void setMascotScore(Double mascotScore);

}