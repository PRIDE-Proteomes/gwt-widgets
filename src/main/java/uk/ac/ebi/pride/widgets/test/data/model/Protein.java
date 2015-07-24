package uk.ac.ebi.pride.widgets.test.data.model;

import java.util.List;

public interface Protein {

    public String getAccession();
    public void setAccession(String accession);

    public Integer getLength();
    public void setLength(Integer length);

    public Integer getUniqueCoverage();
    public void setUniqueCoverage(Integer uniqueCoverage);

    public Integer getNonUniqueCoverage();
    public void setNonUniqueCoverage(Integer nonUniqueCoverage);

    public String getSequence();
    public void setSequence(String sequence);

    public List<ProteinModification> getModifiedLocations();
    public void setModifiedLocations(List<ProteinModification> modifiedLocations);

    public List<Peptide> getPeptides();
    public void setPeptides(List<Peptide> peptides);

    public List<Feature> getFeatures();
    public void setFeatures(List<Feature> features);

}