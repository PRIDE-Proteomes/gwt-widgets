package uk.ac.ebi.pride.widgets.test.data.proxy;

import uk.ac.ebi.pride.widgets.client.common.handler.PeptideHandler;
import uk.ac.ebi.pride.widgets.test.data.model.Peptide;

import java.util.Collection;

public class PeptideProxy implements PeptideHandler {

    Peptide peptide;

    public PeptideProxy(Peptide peptide) {
        this.peptide = peptide;
    }

    @Override
    public String getSequence() {
        return peptide.getSequence();
    }

    @Override
    public Integer getSite() {
        return peptide.getPosition();
    }

    @Override
    public Integer getEnd() {
        return peptide.getEnd();
    }

    @Override
    public Integer getUniqueness() {
        return peptide.getUniqueness();
    }

    @Override
    public Collection<String> getSharedProteins() {
        return peptide.getSharedProteins();
    }

    @Override
    public Collection<String> getSharedGenes() {
        return peptide.getSharedGenes();
    }
}
