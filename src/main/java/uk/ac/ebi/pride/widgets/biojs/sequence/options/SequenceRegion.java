package uk.ac.ebi.pride.widgets.biojs.sequence.options;

public class SequenceRegion implements SequenceOptions {
    private int start;
    private int end;
    private String color;
    private String background;

    public SequenceRegion(int start, int end) {
        this.start = start;
        this.end = end;
    }



    public SequenceRegion(int start, int end, String color, String background) {
        this(start,end);
        this.color = color;
        this.background = background;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String getJSON(){
        if(color==null)
            return "{\"start\": " +  start +", \"end\": " + end + "}";
        else
            return "{\"start\": " +  start +", \"end\": " + end + ", \"color\": \"" + color + "\", \"background\": \"" + background + "\"}";
    }
}
