package uk.ac.ebi.pride.widgets.test.data.model;

public interface Peptide {

    String getProteinId();
    void setProteinId(String proteinId);

    String getSequence();
    void setSequence(String sequence);

    //The site where peptide is started into protein.
    Integer getSite();
    void setSite(Integer site);

    //PeptideHandler end is used very often in the webapp, and make sense to keep it calculated
    Integer getEnd();
    void setEnd(Integer end);

    Integer getUniqueness();
    void setUniqueness(Integer uniqueness);

    //How many psm this peptide have.
    Integer getPsmHits();
    void setPsmHits(Integer psmHits);
}
