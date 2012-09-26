package uk.ac.ebi.pride.widgets.client.sequence.data;

import java.util.List;

public interface Protein{

    public Integer getLength();

    public String getSequence();

    public List<ProteinModification> getModifications();

    public List<Peptide> getPeptides();
}
