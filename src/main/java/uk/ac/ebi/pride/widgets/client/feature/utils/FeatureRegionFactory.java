package uk.ac.ebi.pride.widgets.client.feature.utils;

import uk.ac.ebi.pride.widgets.client.feature.model.CoveredFeatureRegion;
import uk.ac.ebi.pride.widgets.client.feature.model.FeatureRegion;
import uk.ac.ebi.pride.widgets.client.feature.model.NonCoveredFeatureRegion;

public abstract class FeatureRegionFactory {

    public static FeatureRegion createFeatureRegion(int start, int numFeature, FeatureCanvasProperties featureCanvasProperties){
        if (numFeature != 0) {
            return new CoveredFeatureRegion(start, featureCanvasProperties);
        } else {
            return new NonCoveredFeatureRegion(start, featureCanvasProperties);
        }
    }
}
