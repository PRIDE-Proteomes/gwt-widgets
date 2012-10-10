package uk.ac.ebi.pride.widgets.client.protein.utils;

import uk.ac.ebi.pride.widgets.client.common.handler.PeptideHandler;
import uk.ac.ebi.pride.widgets.client.protein.model.ProteinAxis;

import java.util.HashSet;
import java.util.Set;

public class PeptideLevel {
    private ProteinAxis pa;
    int correction;
    Set<PeptideHandler> peptideHandlers;
    boolean[] availablePositions;

    public PeptideLevel(int proteinLength, ProteinAxis pa) {
        //noinspection Convert2Diamond
        this.peptideHandlers = new HashSet<PeptideHandler>();
        this.pa = pa;
        correction = (int) Math.floor(pa.getPixelFromValue(0));
        int end = (int) Math.ceil(pa.getPixelFromValue(proteinLength + 1));
        int length = end - correction + 1;
        availablePositions = new boolean[length];
        for(int i=0; i<length; ++i){
            availablePositions[i]=true;
        }
    }

    public boolean addPeptide(PeptideHandler peptideHandler){
        int start = (int) Math.floor(pa.getPixelFromValue(peptideHandler.getSite())) - correction;
        int end = (int) Math.ceil(pa.getPixelFromValue(peptideHandler.getEnd())) - correction;
        if(spaceAvailable(start, end)){
            reserveSpace(start, end);
            peptideHandlers.add(peptideHandler);
            return true;
        }
        return false;
    }

    public Set<PeptideHandler> getPeptideHandlers() {
        return peptideHandlers;
    }

    private boolean spaceAvailable(int start, int end){
        //Next two lines are used because we don't want consecutive peptideHandlers in the same level :)
        if(start>1) start--;
        if(start>1) start--;
        if(end < availablePositions.length) end++;
        if(end < availablePositions.length) end++;

        int length = availablePositions.length;
        if( start > end || end > length)
            return false;

        for(int i = start; i < end; ++i){
            if(!availablePositions[i]) return false;
        }

        return true;
    }

    private void reserveSpace(int start, int end){
        //Next two lines are used because we don't want consecutive peptideHandlers in the same level :)
        if(start>1) start--;
        if(start>1) start--;
        if(end < availablePositions.length) end++;
        if(end < availablePositions.length) end++;

        for(int i = start; i < end; ++i){
            availablePositions[i] = false;
        }
    }
}
