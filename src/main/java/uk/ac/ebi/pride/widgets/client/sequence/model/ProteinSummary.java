package uk.ac.ebi.pride.widgets.client.sequence.model;

import uk.ac.ebi.pride.widgets.client.common.handler.PeptideHandler;
import uk.ac.ebi.pride.widgets.client.common.handler.PrideModificationHandler;
import uk.ac.ebi.pride.widgets.client.common.handler.ProteinHandler;
import uk.ac.ebi.pride.widgets.client.common.handler.ProteinModificationHandler;

import java.util.*;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class ProteinSummary {

    private String sequence;
    private int length;
    @SuppressWarnings("Convert2Diamond")
    private Set<Integer> nonUniquePeptidesPositions = new HashSet<Integer>();
    @SuppressWarnings("Convert2Diamond")
    private Set<Integer> uniquePeptidesPositions = new HashSet<Integer>();
    @SuppressWarnings("Convert2Diamond")
    public Set<Integer> modificationPositions = new HashSet<Integer>();

    public Map<Integer, List<PrideModificationHandler>> modificationType = new HashMap<Integer, List<PrideModificationHandler>>();

    public ProteinSummary(ProteinHandler proteinHandler) {
        this.sequence = proteinHandler.getSequence();
        this.length = proteinHandler.getLength();

        for (PeptideHandler peptideHandler : proteinHandler.getPeptides()) {
            if (peptideHandler.getSite() > 0 && peptideHandler.getEnd() <= proteinHandler.getLength()) {
                for(int p= peptideHandler.getSite(); p<= peptideHandler.getEnd(); ++p){
                    if(peptideHandler.getUniqueness()==1){
                        uniquePeptidesPositions.add(p);
                    }else{
                        nonUniquePeptidesPositions.add(p);
                    }
                }
            } // else: ignore this peptide, as it is outside the protein sequence scope
        }

        for (ProteinModificationHandler proteinModificationHandler : proteinHandler.getModifications()) {
            int site = proteinModificationHandler.getSite();
            List<PrideModificationHandler> list;

            if(modificationType.containsKey(site))
                list = modificationType.get(site);
            else
                list = new LinkedList<PrideModificationHandler>();

            list.add(proteinModificationHandler.getPrideModification());
            modificationType.put(site, list);
            modificationPositions.add(site);
        }
    }

    public String getSequence() {
        return sequence;
    }

    public int getLength() {
        return length;
    }

    public Set<Integer> getNonUniquePeptidesPositions() {
        return nonUniquePeptidesPositions;
    }

    public Set<Integer> getUniquePeptidesPositions() {
        return uniquePeptidesPositions;
    }

    public Set<Integer> getModificationPositions() {
        return modificationPositions;
    }

    public List<PrideModificationHandler> getPrideModifications(Integer site){
        if(modificationType.containsKey(site))
            return modificationType.get(site);
        return new LinkedList<PrideModificationHandler>();
    }
}
