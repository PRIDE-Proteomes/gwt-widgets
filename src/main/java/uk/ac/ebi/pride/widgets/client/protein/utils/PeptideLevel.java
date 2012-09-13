package uk.ac.ebi.pride.widgets.client.protein.utils;

import uk.ac.ebi.pride.widgets.client.protein.data.Peptide;

import java.util.HashSet;
import java.util.Set;

public class PeptideLevel {

    Set<Peptide> peptides;
    boolean[] availablePositions;

    public PeptideLevel(int proteinLength) {
        this.peptides = new HashSet<Peptide>();
        availablePositions = new boolean[proteinLength];
        for(int i=0; i<proteinLength; ++i){
            availablePositions[i]=true;
        }
    }

    public boolean addPeptide(Peptide peptide){
        int start = peptide.getSite();
        int end = peptide.getEnd();
        if(spaceAvailable(start, end)){
            reserveSpace(start, end);
            peptides.add(peptide);
            return true;
        }
        return false;
    }

    public Set<Peptide> getPeptides() {
        return peptides;
    }

    private boolean spaceAvailable(int start, int end){
        //Next two lines are used because we don't want consecutive peptides in the same level :)
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
        //Next two lines are used because we don't want consecutive peptides in the same level :)
        if(start>1) start--;
        if(end < availablePositions.length) end++;

        for(int i = start; i < end; ++i){
            availablePositions[i] = false;
        }
    }
}
