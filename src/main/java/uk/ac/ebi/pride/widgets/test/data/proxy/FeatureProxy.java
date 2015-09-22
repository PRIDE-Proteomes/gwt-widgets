package uk.ac.ebi.pride.widgets.test.data.proxy;

import uk.ac.ebi.pride.widgets.client.common.handler.FeatureHandler;
import uk.ac.ebi.pride.widgets.test.data.model.Feature;

public class FeatureProxy implements FeatureHandler {

    Feature feature;

    public FeatureProxy(Feature feature) {
        this.feature = feature;
    }

    @Override
    public int getId() {
        return feature.getId();
    }

    @Override
    public String getType() {
        return feature.getType();
    }

    @Override
    public Integer getStart() {
        return feature.getStart();
    }

    @Override
    public Integer getEnd() {
        return feature.getEnd();
    }

}
