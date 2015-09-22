package uk.ac.ebi.pride.widgets.test.data.proxy;

import uk.ac.ebi.pride.widgets.client.common.handler.PrideModificationHandler;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class PrideModificationProxy implements PrideModificationHandler {
    private final String type;

    public PrideModificationProxy(String modificationType) {
        type = modificationType;
    }

    @Override
    public int getId() {
        return type.hashCode();
    }

    @Override
    public String getName() {
        return type;
    }

    @Override
    public Double getDiffMono() {
        return 0.0;
    }

    @Override
    public boolean isBioSignificance() {
        return false;
    }
}
