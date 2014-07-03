package uk.ac.ebi.pride.widgets.test.data.proxy;

import uk.ac.ebi.pride.widgets.client.common.handler.PeptideHandler;
import uk.ac.ebi.pride.widgets.test.data.model.Peptide;

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
        return peptide.getSite();
    }

    @Override
    public Integer getEnd() {
        return peptide.getEnd();
    }

    @Override
    public Integer getUniqueness() {
        return peptide.getUniqueness();
    }
}
