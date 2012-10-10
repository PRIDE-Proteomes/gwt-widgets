package uk.ac.ebi.pride.widgets.test.data.proxy;

import uk.ac.ebi.pride.widgets.client.common.handler.PrideModificationHandler;
import uk.ac.ebi.pride.widgets.client.common.handler.ProteinModificationHandler;
import uk.ac.ebi.pride.widgets.test.data.model.ProteinModification;

public class ProteinModificationProxy implements ProteinModificationHandler {

    ProteinModification proteinModification;

    public ProteinModificationProxy(ProteinModification proteinModification) {
        this.proteinModification = proteinModification;
    }

    @Override
    public Integer getSite() {
        return proteinModification.getSite();
    }

    @Override
    public PrideModificationHandler getPrideModification() {
        return new PrideModificationProxy(proteinModification.getPrideModification());
    }

    @Override
    public Integer getCount() {
        return proteinModification.getCount();
    }

    @Override
    public Integer getUniqueness() {
        return proteinModification.getUniqueness();
    }

    @Override
    public Double getPrideScore() {
        return proteinModification.getPrideScore();
    }

    @Override
    public Double getMascotScore() {
        return proteinModification.getMascotScore();
    }
}
