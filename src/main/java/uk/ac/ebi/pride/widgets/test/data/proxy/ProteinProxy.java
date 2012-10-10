package uk.ac.ebi.pride.widgets.test.data.proxy;

import uk.ac.ebi.pride.widgets.client.common.handler.PeptideHandler;
import uk.ac.ebi.pride.widgets.client.common.handler.ProteinHandler;
import uk.ac.ebi.pride.widgets.client.common.handler.ProteinModificationHandler;
import uk.ac.ebi.pride.widgets.test.data.model.Peptide;
import uk.ac.ebi.pride.widgets.test.data.model.Protein;
import uk.ac.ebi.pride.widgets.test.data.model.ProteinModification;

import java.util.LinkedList;
import java.util.List;

public class ProteinProxy implements ProteinHandler {
    Protein protein;
    List<PeptideHandler> peptideAdapters;
    List<ProteinModificationHandler> proteinModifications;

    public ProteinProxy(Protein protein) {
        this.protein = protein;
        peptideAdapters = new LinkedList<PeptideHandler>();
        for (Peptide peptide : protein.getPeptides()) {
            peptideAdapters.add(new PeptideProxy(peptide));
        }
        proteinModifications = new LinkedList<ProteinModificationHandler>();
        for (ProteinModification proteinModification : protein.getModifications()) {
            proteinModifications.add(new ProteinModificationProxy(proteinModification));
        }
    }

    @Override
    public Integer getLength() {
        return protein.getLength();
    }

    @Override
    public String getSequence() {
        return protein.getSequence();
    }

    @Override
    public List<ProteinModificationHandler> getModifications() {
        return proteinModifications;
    }

    @Override
    public List<PeptideHandler> getPeptides() {
        return peptideAdapters;
    }
}
