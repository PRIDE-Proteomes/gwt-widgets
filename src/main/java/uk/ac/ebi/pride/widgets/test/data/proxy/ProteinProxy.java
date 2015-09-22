package uk.ac.ebi.pride.widgets.test.data.proxy;

import uk.ac.ebi.pride.widgets.client.common.handler.FeatureHandler;
import uk.ac.ebi.pride.widgets.client.common.handler.PeptideHandler;
import uk.ac.ebi.pride.widgets.client.common.handler.ProteinHandler;
import uk.ac.ebi.pride.widgets.client.common.handler.ProteinModificationHandler;
import uk.ac.ebi.pride.widgets.test.data.model.Feature;
import uk.ac.ebi.pride.widgets.test.data.model.ModifiedLocation;
import uk.ac.ebi.pride.widgets.test.data.model.PeptideMatch;
import uk.ac.ebi.pride.widgets.test.data.model.Protein;

import java.util.ArrayList;
import java.util.List;

public class ProteinProxy implements ProteinHandler {
    private final Protein protein;
    private final List<ProteinModificationHandler> modifications;
    private final List<PeptideHandler> peptideHandlers;
    private final List<FeatureHandler> featureHandlers;

    public ProteinProxy(Protein protein) {
        this.protein = protein;

        peptideHandlers = new ArrayList<PeptideHandler>();
        for(PeptideMatch p : protein.getPeptides()) {
            peptideHandlers.add(new PeptideProxy(p));
        }

        modifications = new ArrayList<ProteinModificationHandler>();
        for(ModifiedLocation mod : protein.getModifiedLocations()) {
            modifications.add(new ProteinModificationProxy(mod, protein));
        }

        featureHandlers = new ArrayList<FeatureHandler>();
        for(Feature feature : protein.getFeatures()) {
            featureHandlers.add(new FeatureProxy(feature));
        }
    }

    @Override
    public Integer getLength() {
        return protein.getSequence().length();
    }

    @Override
    public String getSequence() {
        return protein.getSequence();
    }

    @Override
    public List<ProteinModificationHandler> getModifications() {
        return modifications;
    }

    @Override
    public List<PeptideHandler> getPeptides() {
        return peptideHandlers;
    }

    @Override
    public List<FeatureHandler> getFeatures() {
        return featureHandlers;
    }
}
