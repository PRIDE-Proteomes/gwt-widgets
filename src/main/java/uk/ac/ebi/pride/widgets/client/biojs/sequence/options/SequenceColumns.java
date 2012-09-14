package uk.ac.ebi.pride.widgets.client.biojs.sequence.options;

public class SequenceColumns implements SequenceOptions {
    private int numCols;
    private int spaceEach;

    public SequenceColumns(int numCols, int spaceEach) {
        this.numCols = numCols;
        this.spaceEach = spaceEach;
    }

    public int getNumCols() {
        return numCols;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public int getSpaceEach() {
        return spaceEach;
    }

    public void setSpaceEach(int spaceEach) {
        this.spaceEach = spaceEach;
    }

    @Override
    public String getJSON(){
        return "{\"size\": " + numCols + ",\"spacedEach\": " + spaceEach + "}";
    }
}
