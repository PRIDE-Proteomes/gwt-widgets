package uk.ac.ebi.pride.widgets.client.common.handler;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public interface PrideModificationHandler {

    int getId();

    String getName(); //i.e. Oxidation

    Double getDiffMono();

    boolean isBioSignificance();
}
