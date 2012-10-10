package uk.ac.ebi.pride.widgets.client.protein.utils;

import uk.ac.ebi.pride.widgets.client.common.handler.PeptideHandler;
import uk.ac.ebi.pride.widgets.client.protein.model.CoveredSequenceBorder;
import uk.ac.ebi.pride.widgets.client.protein.model.NonCoveredSequenceRegion;
import uk.ac.ebi.pride.widgets.client.protein.model.ProteinAxis;
import uk.ac.ebi.pride.widgets.client.protein.model.SequenceRegion;

import java.util.*;

public abstract class RegionUtils {

    public static List<SequenceRegion> getSequenceRegions(ProteinAxis pa){
        List<SequenceRegion> regions = new ArrayList<SequenceRegion>();

        //initialize an array with an empty peptide set per position
        List<Set<String>> aux = new LinkedList<Set<String>>();
        for(int i=0; i<pa.getProteinHandler().getLength(); ++i){aux.add(new HashSet<String>());}

        //for every peptide we add the sequence in every position were the peptide already is
        //[(ABC, AB),(ABC, AB),(AB), ... ]
        for (PeptideHandler peptideHandler : pa.getProteinHandler().getPeptides()) {
            String seq = peptideHandler.getSequence();
            int start = peptideHandler.getSite() - 1;
            int end = start + seq.length();
            for(int i=start; i<end; ++i){aux.get(i).add(seq);}
        }

        //now regions are created by grouping the different values in the previous array
        Set<String> lastSet = null;
        for(int i=0; i<aux.size(); ++i){
            Set<String> peptideSet = aux.get(i);
            SequenceRegion region;
            if(peptideSet.equals(lastSet)){
                region = regions.get(regions.size() - 1);
                region.increaseLength();
            }else{
                region = SequenceRegionFactory.createSequenceRegion(i + 1, peptideSet.size(), pa);
                regions.add(region);
            }
            lastSet = peptideSet;
        }

        return regions;
    }

    public static List<CoveredSequenceBorder> getCoveredSequenceBorder(List<SequenceRegion> regions){
        List<CoveredSequenceBorder> borders = new ArrayList<CoveredSequenceBorder>();

        boolean newBorder = true;
        for (SequenceRegion region : regions) {
            if(region instanceof NonCoveredSequenceRegion){
                newBorder = true;
            }else{
                if(newBorder){
                    int start = region.getStart();
                    int length = region.getLength();
                    CoveredSequenceBorder border = new CoveredSequenceBorder(start, length, region.getProteinSegment());
                    borders.add(border);
                }else{
                    CoveredSequenceBorder border = borders.get(borders.size()-1);
                    border.increaseLength(region.getLength());
                }
                newBorder = false;
            }
        }

        return borders;
    }
}
