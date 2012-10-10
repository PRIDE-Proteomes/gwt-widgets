package uk.ac.ebi.pride.widgets.test.data.model;

public interface PeptideModification {
    Integer getId();
    void setId(Integer id);

    Integer getSpecId();
    void setSpecId(Integer specId);

    Integer getLocation();
    void setLocation(Integer location);

    String getOriginalAccession();
    void setOriginalAccession(String originalAccession);

    String getMainAccession();
    void setMainAccession(String mainAccession);

    String getModMonoDelta();
    void setModMonoDelta(String modMonoDelta);

    String getPrideModId();
    void setPrideModId(String prideModId);

    Integer getBioSignificance();
    void setBioSignificance(Integer bioSignificance);
}
