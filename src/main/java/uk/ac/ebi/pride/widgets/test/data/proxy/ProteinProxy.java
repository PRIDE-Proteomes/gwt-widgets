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
    List<PeptideHandler> peptides;
    List<ProteinModificationHandler> proteinModifications;

    @SuppressWarnings("Convert2Diamond")
    public ProteinProxy(Protein protein) {
        this.protein = protein;
        this.peptides = new LinkedList<PeptideHandler>();
        for (Peptide peptide : protein.getPeptides()) {
            this.peptides.add(new PeptideProxy(peptide));
        }
        this.proteinModifications = new LinkedList<ProteinModificationHandler>();
        for (ProteinModification proteinModification : protein.getModifications()) {
            this.proteinModifications.add(new ProteinModificationProxy(proteinModification));
        }
    }

    @Override
    public Integer getLength() {
        return this.protein.getLength();
    }

    @Override
    public String getSequence() {
        return this.protein.getSequence();
    }

    @Override
    public List<ProteinModificationHandler> getModifications() {
        return this.proteinModifications;
    }

    @Override
    public List<PeptideHandler> getPeptides() {
        return this.peptides;
    }
}
