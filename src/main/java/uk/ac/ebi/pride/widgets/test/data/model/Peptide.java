package uk.ac.ebi.pride.widgets.test.data.model;

import java.util.Collection;

public interface Peptide {

    String getProteinId();
    void setProteinId(String proteinId);

    String getSequence();
    void setSequence(String sequence);

    //The position where peptide is started into protein.
    Integer getPosition();
    void setPosition(Integer site);

    //PeptideHandler end is used very often in the webapp, and make sense to keep it calculated
    Integer getEnd();
    void setEnd(Integer end);

    Integer getUniqueness();
    void setUniqueness(Integer uniqueness);


    Collection<String> getSharedProteins();
    void setSharedProteins(Collection<String> sharedProteins);

    Collection<String> getSharedUpEntries();
    void setSharedUpEntries(Collection<String> sharedUpEntries);

    Collection<String> getSharedGenes();
    void setSharedGenes(Collection<String> sharedGenes);

    //How many psm this peptide have.
    Integer getPsmHits();
    void setPsmHits(Integer psmHits);
}
