package uk.ac.ebi.pride.widgets.test.data.proxy;

import uk.ac.ebi.pride.widgets.client.common.handler.PrideModificationHandler;
import uk.ac.ebi.pride.widgets.test.data.model.PrideModification;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class PrideModificationProxy implements PrideModificationHandler {
    private PrideModification prideModification;

    public PrideModificationProxy(PrideModification prideModification) {
        this.prideModification = prideModification;
    }

    @Override
    public int getId() {
        return prideModification.getId();
    }

    @Override
    public String getName() {
        return prideModification.getName();
    }

    @Override
    public Double getDiffMono() {
        return prideModification.getDiffMono();
    }

    @Override
    public boolean isBioSignificance() {
        return prideModification.isBioSignificance();
    }
}
