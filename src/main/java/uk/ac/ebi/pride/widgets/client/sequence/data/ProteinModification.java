package uk.ac.ebi.pride.widgets.client.sequence.data;

public interface ProteinModification {

    Integer getSite();

    Integer getCount();

    Integer getUniqueness();

    Double getPrideScore();

    Double getMascotScore();
}
