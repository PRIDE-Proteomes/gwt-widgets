package uk.ac.ebi.pride.widgets.client.sequence.type;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public interface SequenceType {

    public String getTypeName();
    public boolean isPositionShown();
    public String getFormattedPositionNumber(int number);
    public int getNumOfBlocks();
    public int getBlockSize();

}
