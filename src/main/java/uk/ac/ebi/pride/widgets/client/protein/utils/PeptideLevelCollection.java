package uk.ac.ebi.pride.widgets.client.protein.utils;

import uk.ac.ebi.pride.widgets.client.common.handler.PeptideHandler;

import java.util.LinkedList;
import java.util.List;

public class PeptideLevelCollection {
    private CanvasProperties canvasProperties;
    private List<PeptideLevel> peptideLevels;
    private int currentLevel;

    public PeptideLevelCollection(CanvasProperties canvasProperties) {
        this.canvasProperties = canvasProperties;
        //noinspection Convert2Diamond
        this.peptideLevels = new LinkedList<PeptideLevel>();
        initialize();
    }

    private void initialize(){
        this.currentLevel = 0;
        for (PeptideHandler peptideHandler : canvasProperties.getProteinHandler().getPeptides()) {
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
            peptideLevel = new PeptideLevel(this.canvasProperties);
            peptideLevels.add(peptideLevel);
        }else{
            peptideLevel = peptideLevels.get(this.currentLevel);
        }
        this.currentLevel++;
        return peptideLevel;
    }
}
