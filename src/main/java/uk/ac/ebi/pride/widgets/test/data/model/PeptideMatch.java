package uk.ac.ebi.pride.widgets.test.data.model;

import java.util.Set;

public interface PeptideMatch extends Peptide {
    public Integer getPosition();
    public Integer getUniqueness();
    public Set<String> getSharedProteins();
    public Set<String> getSharedUpEntries();
    public Set<String> getSharedGenes();

}
