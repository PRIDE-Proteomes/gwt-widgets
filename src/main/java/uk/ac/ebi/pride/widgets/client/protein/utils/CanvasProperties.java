package uk.ac.ebi.pride.widgets.client.protein.utils;

import com.google.gwt.canvas.client.Canvas;
import uk.ac.ebi.pride.widgets.client.common.handler.ProteinHandler;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class CanvasProperties {
    public static final int X_OFFSET = 4;
    public static final int Y_OFFSET = 20;

    private ProteinHandler proteinHandler;
    private double delta;

    public CanvasProperties(ProteinHandler proteinHandler, Canvas canvas) {
        this.proteinHandler = proteinHandler;

        int width = canvas.getCanvasElement().getWidth() - ( 2 * X_OFFSET );
        double length = proteinHandler.getLength();
        this.delta = width / length;
    }

    public final double getPixelFromValue(double value){
        return (this.delta * value) + X_OFFSET;
    }

    public ProteinHandler getProteinHandler() {
        return proteinHandler;
    }

    public int getProteinLength(){
        return this.proteinHandler.getLength();
    }
}
