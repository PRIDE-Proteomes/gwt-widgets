package uk.ac.ebi.pride.widgets.client.protein.utils;

import uk.ac.ebi.pride.widgets.client.protein.data.Peptide;
import uk.ac.ebi.pride.widgets.client.protein.data.Protein;

import java.util.LinkedList;
import java.util.List;

public class PeptideLevelCollection {

    private List<PeptideLevel> peptideLevels;
    private int proteinLength;
    private int currentLevel;

    public PeptideLevelCollection(Protein protein) {
        this.peptideLevels = new LinkedList<PeptideLevel>();
        this.proteinLength = protein.getLength();
        initialize(protein);
    }

    private void initialize(Protein protein){
        this.currentLevel = 0;
        for (Peptide peptide : protein.getPeptides()) {
            PeptideLevel peptideLevel = getOrCreatePeptideLevel();
            while (!peptideLevel.addPeptide(peptide)){
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
