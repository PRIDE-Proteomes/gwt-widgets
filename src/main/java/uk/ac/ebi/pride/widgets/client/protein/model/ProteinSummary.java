package uk.ac.ebi.pride.widgets.client.protein.model;

import uk.ac.ebi.pride.widgets.client.common.handler.PrideModificationHandler;
import uk.ac.ebi.pride.widgets.client.common.handler.ProteinHandler;
import uk.ac.ebi.pride.widgets.client.common.handler.ProteinModificationHandler;

import java.util.*;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("Convert2Diamond")
public class ProteinSummary {

    private String sequence;
    private int length;

    public Set<Integer> modificationPositions = new HashSet<Integer>();
    public Map<Integer, List<PrideModificationHandler>> modificationType = new HashMap<Integer, List<PrideModificationHandler>>();

    public ProteinSummary(ProteinHandler proteinHandler) {
        this.sequence = proteinHandler.getSequence();
        this.length = proteinHandler.getLength();

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

    public Set<Integer> getModificationPositions() {
        return modificationPositions;
    }

    public List<PrideModificationHandler> getPrideModifications(Integer site){
        if(modificationType.containsKey(site))
            return modificationType.get(site);
        return new LinkedList<PrideModificationHandler>();
    }
}
