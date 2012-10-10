package uk.ac.ebi.pride.widgets.client.protein.utils;

import uk.ac.ebi.pride.widgets.client.common.handler.PeptideHandler;

import java.util.HashSet;
import java.util.Set;

public class PeptideLevel {

    Set<PeptideHandler> peptideHandlers;
    boolean[] availablePositions;

    public PeptideLevel(int proteinLength) {
        this.peptideHandlers = new HashSet<PeptideHandler>();
        availablePositions = new boolean[proteinLength];
        for(int i=0; i<proteinLength; ++i){
            availablePositions[i]=true;
        }
    }

    public boolean addPeptide(PeptideHandler peptideHandler){
        int start = peptideHandler.getSite();
        int end = peptideHandler.getEnd();
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
        if(end < availablePositions.length) end++;

        for(int i = start; i < end; ++i){
            availablePositions[i] = false;
        }
    }
}
