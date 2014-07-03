package uk.ac.ebi.pride.widgets.client.protein.utils;

import uk.ac.ebi.pride.widgets.client.common.handler.PeptideHandler;

import java.util.HashSet;
import java.util.Set;

public class PeptideLevel {
    private CanvasProperties canvasProperties;
    int correction;
    Set<PeptideHandler> peptideHandlers;
    boolean[] availablePositions;

    public PeptideLevel(CanvasProperties canvasProperties) {
        this.canvasProperties = canvasProperties;
        //noinspection Convert2Diamond
        this.peptideHandlers = new HashSet<PeptideHandler>();
        correction = (int) Math.floor(canvasProperties.getPixelFromPosition(0));
        int end = (int) Math.ceil(canvasProperties.getPixelFromPosition(canvasProperties.getProteinLength() + 1));
        int length = end - correction + 1;
        availablePositions = new boolean[length];
        for(int i=0; i<length; ++i){
            availablePositions[i]=true;
        }
    }

    public boolean addPeptide(PeptideHandler peptideHandler){
        int start = (int) Math.floor(this.canvasProperties.getPixelFromPosition(peptideHandler.getSite())) - correction;
        int end = (int) Math.ceil(this.canvasProperties.getPixelFromPosition(peptideHandler.getEnd())) - correction;
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
