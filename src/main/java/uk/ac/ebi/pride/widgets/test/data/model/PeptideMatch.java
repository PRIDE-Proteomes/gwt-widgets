package uk.ac.ebi.pride.widgets.test.data.model;

import java.util.Set;

public interface PeptideMatch extends Peptide {
    Integer getPosition();
    Integer getUniqueness();
    Set<String> getSharedProteins();
    Set<String> getSharedUpEntries();
    Set<String> getSharedGenes();

}
