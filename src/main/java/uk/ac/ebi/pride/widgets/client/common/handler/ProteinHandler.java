package uk.ac.ebi.pride.widgets.client.common.handler;

import java.util.List;

public interface ProteinHandler {

    Integer getLength();

    String getSequence();

    List<ProteinModificationHandler> getModifications();

    List<PeptideHandler> getPeptides();

    List<FeatureHandler> getFeatures();

}
