package uk.ac.ebi.pride.widgets.client.biojs.sequence.options;

import java.util.ArrayList;
import java.util.List;

public class SequenceHighlights implements SequenceOptions{

    private List<SequenceRegion> regionList = new ArrayList<SequenceRegion>();

    public SequenceHighlights() {

    }

    public SequenceHighlights(SequenceRegion region) {
        addSequenceRegion(region);
    }

    public void addSequenceRegion(SequenceRegion region) {
        this.regionList.add(region);
    }

    public List<SequenceRegion> getRegionList() {
        return regionList;
    }

    @Override
    public String getJSON(){
        if(regionList==null || regionList.size()==0){
            return "[]";
        }else {
            StringBuilder json = new StringBuilder("[");
            for (SequenceRegion region : regionList) {
                json.append(region.getJSON());
                json.append(",");
            }
            json.deleteCharAt(json.length()-1);
            json.append("]");

            return json.toString();
        }
    }
}
