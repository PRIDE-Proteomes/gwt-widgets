package uk.ac.ebi.pride.widgets.client.protein.model;

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
    public Map<Integer, List<ProteinModificationHandler>> modificationsAtSite = new HashMap<Integer, List<ProteinModificationHandler>>();

    public ProteinSummary(ProteinHandler proteinHandler) {
        this.sequence = proteinHandler.getSequence();
        this.length = proteinHandler.getLength();

        for (ProteinModificationHandler proteinModification: proteinHandler.getModifications()) {
            int site = proteinModification.getSite();
            List<ProteinModificationHandler> list;

            if(this.modificationsAtSite.containsKey(site)){
                list = this.modificationsAtSite.get(site);
            }else{
                list = new LinkedList<ProteinModificationHandler>();
            }

            list.add(proteinModification);
            this.modificationsAtSite.put(site, list);
            this.modificationPositions.add(site);
        }
    }

    public String getSequence() {
        return this.sequence;
    }

    public int getLength() {
        return this.length;
    }

    public Set<Integer> getModificationPositions() {
        return this.modificationPositions;
    }

    public List<ProteinModificationHandler> getPrideModifications(Integer site){
        if(this.modificationsAtSite.containsKey(site))
            return this.modificationsAtSite.get(site);
        return new LinkedList<ProteinModificationHandler>();
    }
}
