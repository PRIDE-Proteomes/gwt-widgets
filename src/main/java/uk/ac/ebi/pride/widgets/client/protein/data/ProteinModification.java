package uk.ac.ebi.pride.widgets.client.protein.data;

public interface ProteinModification {

    Integer getSite();

    Integer getCount();

    Integer getUniqueness();

    Double getPrideScore();

    Double getMascotScore();
}
