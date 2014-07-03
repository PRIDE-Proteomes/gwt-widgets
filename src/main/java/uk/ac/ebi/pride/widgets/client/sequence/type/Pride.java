package uk.ac.ebi.pride.widgets.client.sequence.type;

import com.google.gwt.i18n.client.NumberFormat;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class Pride implements SequenceType {

    @Override
    public String getTypeName() {
        return "PRIDE";
    }

    @Override
    public boolean isPositionShown() {
        return true;
    }

    @Override
    public String getFormattedPositionNumber(int number) {
        return NumberFormat.getFormat("00000").format(number);
    }

    @Override
    public int getNumOfBlocks() {
        return 8;
    }

    @Override
    public int getBlockSize() {
        return 10;
    }
}
