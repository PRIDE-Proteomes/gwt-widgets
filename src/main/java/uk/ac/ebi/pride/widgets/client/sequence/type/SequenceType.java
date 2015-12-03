package uk.ac.ebi.pride.widgets.client.sequence.type;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public interface SequenceType {

    String getTypeName();
    boolean isPositionShown();
    String getFormattedPositionNumber(int number);
    int getNumOfBlocks();
    int getBlockSize();

}
