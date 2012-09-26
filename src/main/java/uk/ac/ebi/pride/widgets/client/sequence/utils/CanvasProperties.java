package uk.ac.ebi.pride.widgets.client.sequence.utils;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 *
 * Singleton objet to keep track of the canvas properties and where to place the next component
 *
 * .------------------------------------------------    <-Canvas
 * | .-------. .-------. <- (nextPosX, nextPosY)
 * | |       | |       |
 * | |       | |       |    <-- A canvas component
 * | |       | |       |
 * | ·-------· ·-------· <- (maxWidth, maxHeight)
 * |
 */
public class CanvasProperties {
    private static CanvasProperties canvasProperties;

    public static final int X_OFFSET = 12;
    public static final int Y_OFFSET = 24;
    public static final int X_SPACE = 5;
    public static final int NEW_LINE_OFFSET = 12;

    private int nextPosX;
    private int nextPosY;
    private int maxWidth;
    private int maxHeight;

    private CanvasProperties() {
        this.nextPosX = X_OFFSET;
        this.nextPosY = Y_OFFSET;
        this.maxWidth = 0;
        this.maxHeight = 0;
    }

    public int getNextPosX() {
        return nextPosX;
    }

    public int getNextPosY() {
        return nextPosY;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void reserveHorizontalSpace(int width){
        reserveSpace(width, 0);
    }

    public void reserveSpace(int width, int height){
        int maxWidthAux = nextPosX + width;
        if(maxWidthAux>this.maxWidth)
            this.maxWidth = maxWidthAux;

        int maxHeightAux = nextPosY + height;
        if(maxHeightAux>this.maxHeight)
            this.maxHeight = maxHeightAux;

        this.nextPosX += width;
        //IMPORTANT: this.nextPosY is NOT updated in this method
    }

    public void setNewRow(){
        this.nextPosX = X_OFFSET;
        this.nextPosY = this.maxHeight + NEW_LINE_OFFSET;
        if(this.nextPosY>this.maxHeight)
            this.maxHeight = this.nextPosY;
    }

    public void reset(){
        canvasProperties = new CanvasProperties();
    }

    public static CanvasProperties getCanvasProperties(){
        if(canvasProperties==null)
            canvasProperties = new CanvasProperties();

        return canvasProperties;
    }
}
