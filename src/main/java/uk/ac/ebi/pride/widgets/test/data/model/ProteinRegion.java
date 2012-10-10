package uk.ac.ebi.pride.widgets.test.data.model;

public class ProteinRegion {

    private Integer start;
    private Integer length;

    public ProteinRegion(Integer start, Integer length) {
        this.start = start;
        this.length = length;
    }

    public Integer getStart() {
        return start;
    }

    public Integer getLength() {
        return length;
    }
}
