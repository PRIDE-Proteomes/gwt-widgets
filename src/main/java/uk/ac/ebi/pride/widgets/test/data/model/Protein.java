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

    public List<String> getGenes();
    public int getTaxonID();

    public String getSequence();
    public void setSequence(String sequence);

    public String getDescription();

    public List<ModifiedLocation> getModifiedLocations();
    public void setModifiedLocations(List<ModifiedLocation> modifiedLocations);

    public List<String> getTissues();

    public String getCoverage();

    public List<List<Integer>> getRegions();

    public List<PeptideMatch> getPeptides();
    public void setPeptides(List<Peptide> peptides);

    public List<Feature> getFeatures();
    public void setFeatures(List<Feature> features);

    public int getUniquePeptideToProteinCount();
    public int getUniquePeptideToGenePeptideCount();
    public int getNonUniquePeptidesCount();

}