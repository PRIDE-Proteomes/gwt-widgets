package uk.ac.ebi.pride.widgets.client.common.handler;

import java.util.Collection;

public interface PeptideHandler {

    String getSequence();

    Integer getSite();

    Integer getEnd();

    Integer getUniqueness();

    Collection<String> getSharedProteins();

    Collection<String> getSharedGenes();

    /*
    Integer getPsmHits();
    */
}
