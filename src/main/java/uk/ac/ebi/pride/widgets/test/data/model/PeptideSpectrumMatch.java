package uk.ac.ebi.pride.widgets.test.data.model;

import java.util.List;

public interface PeptideSpectrumMatch {
    /*TODO Types data are String, but there is a bug in GWT 2.4 than is totally impossible do a Autobean with Long.
    TODO Know we are going to change to Integer but when we will use GWT 2.5, We will have to change.
    TODO When it will works, We will have to change here and in the webService*/
    String getId();
    void setId(String id);

    Integer getAccession();
    void setAccession(Integer accession);

    Integer getSpectrumId();
    void setSpectrumId(Integer spectrumId);

    String getPrecursorMZ();
    void setPrecursorMZ(String precursorMZ);

    Integer getPrecursorCharge();
    void setPrecursorCharge(Integer precursorCharge);

    String getSequence();
    void setSequence(String sequence);

    String getDeltaMZ();
    void setDeltaMZ(String deltaMZ);

    String getPrideScore();
    void setPrideScore(String prideScore);

    String getMascotScore();
    void setMascotScore(String mascotScore);

    Integer getMappings();
    void setMappings(Integer mappings);

    String getUniqueMappingAccession();
    void setUniqueMappingAccession(String uniqueMappingAccession);

    Integer getUniqueMappingStart();
    void setUniqueMappingStart(Integer uniqueMappingStart);

    Integer getInternalMappings();
    void setInternalMappings(Integer internalMappings);

    List<PeptideModification> getModifications();
    void setModifications(List<PeptideModification> modifications);

    String getAnnotatedSequence();
    void setAnnotatedSequence(String annotatedSequence);
}
