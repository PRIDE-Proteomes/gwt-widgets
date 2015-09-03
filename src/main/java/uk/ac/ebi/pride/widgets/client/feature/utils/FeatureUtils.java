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


        for (FeatureHandler featureHandler : featureCanvasProperties.getProteinHandler().getFeatures()) {
            int start = featureHandler.getStart(); //From one base to cero base
            int end = featureHandler.getEnd();
            FeatureRegion region = FeatureRegionFactory.createFeatureRegion(start, end, getFeatureTooltip(featureHandler), featureHandler.getType() ,1, featureCanvasProperties);
            regions.add(region);

        }

        return regions;
    }

    public static String getFeatureTooltip(FeatureHandler feature){
        StringBuilder sb = new StringBuilder();

        sb.append("<span style=\"font-weight:bold;color:");
        sb.append( FeatureType.typeOf(feature.getType()).getColor());
        sb.append("\">");
        sb.append(FeatureType.typeOf(feature.getType()).getDisplayName());
        sb.append("</span>");

        sb.append("<br/>");
        sb.append("Position: ");
        sb.append(feature.getStart());
        sb.append(" - ");
        sb.append(feature.getEnd());
        sb.append("<br/>");
        sb.append("Lenght: ");
        sb.append(feature.getEnd() - feature.getStart() + 1);

        return sb.toString();
    }
}
