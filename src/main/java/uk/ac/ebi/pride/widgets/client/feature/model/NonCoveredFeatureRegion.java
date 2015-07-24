package uk.ac.ebi.pride.widgets.client.feature.model;

import com.google.gwt.canvas.dom.client.Context2d;

/**
 * @author ntoro
 * @since 21/07/15 16:31
 */
public class NonCoveredFeatureRegion extends FeatureRegion {
    public NonCoveredFeatureRegion(int start, uk.ac.ebi.pride.widgets.client.feature.utils.CanvasProperties canvasProperties) {
        super(start, canvasProperties);
    }

    @Override
    public boolean isMouseOver() {
        return false;
    }

    @Override
    public void draw(Context2d context) {

    }
}
