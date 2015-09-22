package uk.ac.ebi.pride.widgets.client.feature.model;

import com.google.gwt.canvas.dom.client.Context2d;
import uk.ac.ebi.pride.widgets.client.feature.utils.FeatureCanvasProperties;

/**
 * @author ntoro
 * @since 21/07/15 16:31
 */
public class NonCoveredFeatureRegion extends FeatureRegion {
    public NonCoveredFeatureRegion(int start, int end, String tooltip, String featureType, FeatureCanvasProperties featureCanvasProperties) {
        super(start, end, tooltip, featureType, featureCanvasProperties);
    }

    @Override
    public boolean isMouseOver() {
        return false;
    }

    @Override
    public void draw(Context2d context) {

    }
}
