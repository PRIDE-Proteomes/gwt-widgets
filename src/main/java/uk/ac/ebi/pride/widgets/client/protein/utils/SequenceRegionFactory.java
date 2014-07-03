package uk.ac.ebi.pride.widgets.client.protein.utils;

import uk.ac.ebi.pride.widgets.client.protein.model.CoveredSequenceRegion;
import uk.ac.ebi.pride.widgets.client.protein.model.NonCoveredSequenceRegion;
import uk.ac.ebi.pride.widgets.client.protein.model.ProteinAxis;
import uk.ac.ebi.pride.widgets.client.protein.model.SequenceRegion;

public abstract class SequenceRegionFactory {

    public static SequenceRegion createSequenceRegion(int start, int peptides, CanvasProperties canvasProperties){
        if(peptides==0)
            return new NonCoveredSequenceRegion(start, canvasProperties);
        else
            return new CoveredSequenceRegion(start, peptides, canvasProperties);
    }
}
