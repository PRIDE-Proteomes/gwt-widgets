package uk.ac.ebi.pride.widgets.biojs.sequence.options;


public class SequenceSelection extends SequenceRegion{

    public SequenceSelection(int start, int end) {
        super(start, end);
    }

    public SequenceSelection(int start, int end, String color, String background) {
        super(start, end, color, background);
    }
}
