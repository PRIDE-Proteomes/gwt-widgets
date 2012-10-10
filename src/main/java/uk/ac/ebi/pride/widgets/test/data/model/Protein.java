package uk.ac.ebi.pride.widgets.test.data.model;

import java.util.List;

public interface Protein {

    public String getId();
    public void setId(String id);

    public Integer getLength();
    public void setLength(Integer length);

    public Integer getUniqueCoverage();
    public void setUniqueCoverage(Integer uniqueCoverage);

    public Integer getNonUniqueCoverage();
    public void setNonUniqueCoverage(Integer nonUniqueCoverage);

    public String getSequence();
    public void setSequence(String sequence);

    public List<ProteinModification> getModifications();
    public void setModifications(List<ProteinModification> modifications);

    public List<Peptide> getPeptides();
    public void setPeptides(List<Peptide> peptides);

}