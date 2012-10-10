package uk.ac.ebi.pride.widgets.client.protein.utils;

import uk.ac.ebi.pride.widgets.client.common.handler.PeptideHandler;
import uk.ac.ebi.pride.widgets.client.common.handler.ProteinHandler;

import java.util.LinkedList;
import java.util.List;

public class PeptideLevelCollection {

    private List<PeptideLevel> peptideLevels;
    private int proteinLength;
    private int currentLevel;

    public PeptideLevelCollection(ProteinHandler proteinHandler) {
        this.peptideLevels = new LinkedList<PeptideLevel>();
        this.proteinLength = proteinHandler.getLength();
        initialize(proteinHandler);
    }

    private void initialize(ProteinHandler proteinHandler){
        this.currentLevel = 0;
        for (PeptideHandler peptideHandler : proteinHandler.getPeptides()) {
            PeptideLevel peptideLevel = getOrCreatePeptideLevel();
            while (!peptideLevel.addPeptide(peptideHandler)){
                peptideLevel = getOrCreatePeptideLevel();
            }
            this.currentLevel = 0;
        }
    }

    public List<PeptideLevel> getPeptideLevels() {
        return peptideLevels;
    }

    private PeptideLevel getOrCreatePeptideLevel(){
        PeptideLevel peptideLevel;
        if(peptideLevels.isEmpty() || peptideLevels.size() == this.currentLevel){
            peptideLevel = new PeptideLevel(this.proteinLength);
            peptideLevels.add(peptideLevel);
        }else{
            peptideLevel = peptideLevels.get(this.currentLevel);
        }
        this.currentLevel++;
        return peptideLevel;
    }
}
