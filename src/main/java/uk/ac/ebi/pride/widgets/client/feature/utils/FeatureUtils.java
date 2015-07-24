package uk.ac.ebi.pride.widgets.client.feature.utils;

import uk.ac.ebi.pride.widgets.client.common.handler.FeatureHandler;
import uk.ac.ebi.pride.widgets.client.feature.model.FeatureRegion;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"deprecation", "UnusedDeclaration", "Convert2Diamond"})
public abstract class FeatureUtils {

    public static List<FeatureRegion> getSequenceRegions(FeatureCanvasProperties featureCanvasProperties) {
        List<FeatureRegion> regions = new ArrayList<FeatureRegion>();
        final int proteinLength = featureCanvasProperties.getProteinLength();

        //initialize an array with an empty peptide set per position
        int aux[] = new int[proteinLength];
        for (int i = 0; i < proteinLength; ++i) {
            aux[i] = 0;
        }

        //we add a flag in every position of the protein where there is a feature
        //we assume there is no overlap in the features
        for (FeatureHandler featureHandler : featureCanvasProperties.getProteinHandler().getFeatures()) {
            int start = featureHandler.getStart() - 1; //From one base to cero base
            int end = featureHandler.getEnd() - 1;
            if (start >= 0 && end <= proteinLength) {
                for (int i = start; i < end; ++i) {
                    aux[i]++;
                }
            } // else: ignore this range, as it is outside the protein sequence scope
        }
//
//        //now regions are created by grouping the different values in the previous array
        int lastNumFeature = -1;
        for (int i = 0; i < proteinLength; ++i) {
            int numFeature = aux[i];
            assert numFeature < 2; //We don't support overlap in one feature in this moment
            FeatureRegion region;
            if (numFeature == lastNumFeature) {
                region = regions.get(regions.size() - 1);
                region.increaseLength();
            } else {
                region = FeatureRegionFactory.createFeatureRegion(i + 1, numFeature, featureCanvasProperties);
                regions.add(region);
            }
            lastNumFeature = numFeature;
        }

        return regions;
    }
}
