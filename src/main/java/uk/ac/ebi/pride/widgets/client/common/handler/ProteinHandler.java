package uk.ac.ebi.pride.widgets.client.common.handler;

import java.util.List;

public interface ProteinHandler {

    public Integer getLength();

    public String getSequence();

    public List<ProteinModificationHandler> getModifications();

    public List<PeptideHandler> getPeptides();

    public List<FeatureHandler> getFeatures();

}
