package uk.ac.ebi.pride.widgets.client.common.handler;

public interface ProteinModificationHandler {

    Integer getSite();

    PrideModificationHandler getPrideModification();

    Integer getCount();

    Integer getUniqueness();

    Double getPrideScore();

    Double getMascotScore();
}
