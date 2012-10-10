package uk.ac.ebi.pride.widgets.client.sequence.utils;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class CanvasSelection {
    private int regionStart;
    private int regionEnd;
    private boolean mouseDown;

    public CanvasSelection() {
        resetSelection();
    }

    public CanvasSelection(int regionStart, int regionEnd) {
        this.regionStart = regionStart;
        this.regionEnd = regionEnd;
    }

    public CanvasSelection(CanvasSelection canvasSelection){
        this.regionStart = canvasSelection.regionStart;
        this.regionEnd = canvasSelection.regionEnd;
        this.mouseDown = canvasSelection.mouseDown;
    }

    public int getRegionStart() {
        return regionStart;
    }

    public void setRegionStart(int regionStart) {
        this.regionStart = regionStart;
    }

    public int getRegionEnd() {
        return regionEnd;
    }

    public void setRegionEnd(int regionEnd) {
        this.regionEnd = regionEnd;
    }

    public boolean isMouseDown() {
        return mouseDown;
    }

    public void setMouseDown(boolean mouseDown) {
        this.mouseDown = mouseDown;
    }

    public boolean containsSelection(){
        return (this.regionStart!=-1 && this.regionEnd!=-1);
    }

    public void resetSelection(){
        this.regionStart = -1;
        this.regionEnd = -1;
        this.mouseDown = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CanvasSelection that = (CanvasSelection) o;

        if (regionEnd != that.regionEnd) return false;
        if (regionStart != that.regionStart) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = regionStart;
        result = 31 * result + regionEnd;
        return result;
    }

    @Override
    public String toString() {
        return  "[" + regionStart + ", " + regionEnd + "]";
    }
}
