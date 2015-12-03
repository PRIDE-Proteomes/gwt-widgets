package uk.ac.ebi.pride.widgets.test.data.model;

import java.util.List;

public interface Protein {

    String getAccession();
    void setAccession(String accession);

    Integer getLength();
    void setLength(Integer length);

    Integer getUniqueCoverage();
    void setUniqueCoverage(Integer uniqueCoverage);

    Integer getNonUniqueCoverage();
    void setNonUniqueCoverage(Integer nonUniqueCoverage);

    List<String> getGenes();
    int getTaxonID();

    String getSequence();
    void setSequence(String sequence);

    String getDescription();

    List<ModifiedLocation> getModifiedLocations();
    void setModifiedLocations(List<ModifiedLocation> modifiedLocations);

    List<String> getTissues();

    String getCoverage();

    List<List<Integer>> getRegions();

    List<PeptideMatch> getPeptides();
    void setPeptides(List<Peptide> peptides);

    List<Feature> getFeatures();
    void setFeatures(List<Feature> features);

    int getUniquePeptideToProteinCount();
    int getUniquePeptideToGenePeptideCount();
    int getNonUniquePeptidesCount();

}