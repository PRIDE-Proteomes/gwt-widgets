package uk.ac.ebi.pride.widgets.biojs.sequence.options;

public class SequenceAnnotationRegion extends SequenceRegion {
    private String color;

    public SequenceAnnotationRegion(int start, int end, String color) {
        super(start, end);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String getJSON(){
        return "{\"start\": " +  getStart() +", \"end\": " + getEnd() + ", \"color\": \"" + color + "\"}";
    }
}
