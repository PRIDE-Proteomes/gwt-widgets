package uk.ac.ebi.pride.widgets.biojs.sequence.options;

import uk.ac.ebi.pride.widgets.biojs.common.Parameters;

import java.util.ArrayList;
import java.util.List;

public class SequenceParameters extends Parameters implements SequenceOptions {
    private static final String COLUMNS_DEFAULT = "{\"size\":40, \"spacedEach\":10}";

    //Required Parameters
    private String sequence;

    //Optional Parameters
    private String id;
    private String format;
    private SequenceHighlights highlights;
    private SequenceColumns columns;
    private SequenceSelection selection;
    private List<SequenceAnnotation> annotations;


    public SequenceParameters(String target, String sequence) {
        this.target = target;
        this.sequence = sequence;
    }

    public SequenceParameters(String sequence) {
        this.sequence = sequence;
    }

    public String getSequence() {
        return sequence;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    private String getHighlightsJSON() {
        if(highlights!=null)
            return highlights.getJSON();
        else
            return "[]";
    }

    public void setHighlights(SequenceHighlights highlights) {
        this.highlights = highlights;
    }

    public void addHighlights(SequenceRegion region) {
        if(highlights==null)
            highlights = new SequenceHighlights(region);
        else
            highlights.addSequenceRegion(region);
    }

    public void removeHighlight(int id){
        for (SequenceRegion sequenceRegion : highlights.getRegionList()) {

        }
    }

    public SequenceColumns getColumns() {
        return columns;
    }

    public void setColumns(int numCols, int spaceEach){
        if(columns==null)
            columns = new SequenceColumns(numCols,spaceEach);
        else{
            columns.setNumCols(numCols);
            columns.setSpaceEach(spaceEach);
        }
    }

    public String getColumnsJSON(){
        if(columns!=null)
            return columns.getJSON();
        else
            return COLUMNS_DEFAULT;
    }

    public void setColumns(SequenceColumns columns) {
        this.columns = columns;
    }

    public SequenceSelection getSelection() {
        return selection;
    }

    private String getSelectionJSON(){
        if(selection!=null)
            return selection.getJSON();
        else
            return "{}";
    }

    public void setSelection(SequenceSelection selection) {
        this.selection = selection;
    }

    public List<SequenceAnnotation> getAnnotations() {
        return annotations;
    }

    public void addAnnotation(SequenceAnnotation annotation){
        if(annotations==null)
            annotations = new ArrayList<SequenceAnnotation>();
        annotations.add(annotation);
    }

    public void removeAnnotation(String name){
        for (SequenceAnnotation annotation : annotations) {
            if(annotation.getName().equals(name)){
                annotations.remove(annotation);
            }
        }
    }

    public void setAnnotations(List<SequenceAnnotation> annotations) {
        this.annotations = annotations;
    }

    private String getAnnotationsJSON(){
        if( annotations==null || annotations.size()==0 ){
            return "[]";
        } else {
            StringBuilder json = new StringBuilder("[");
            for (SequenceAnnotation annotation : annotations) {
                json.append(annotation.getJSON());
                json.append(",");
            }
            json.deleteCharAt(json.length()-1);
            json.append("]");

            return json.toString();
        }
    }

    @Override
    public String getJSON() {
        StringBuilder sb = new StringBuilder();

        sb.append("{\"sequence\": \"");
        sb.append(getSequence());
        sb.append("\", \"target\": \"");
        sb.append(getTarget());
        sb.append("\"");

        if(format!=null){
            sb.append(", \"format\": \"");
            sb.append(getFormat());
            sb.append("\"");
        }
        if(highlights!=null){
            sb.append(", \"highlights\": ");
            sb.append(getHighlightsJSON());
        }
        if(columns!=null){
            sb.append(", \"columns\": ");
            sb.append(getColumnsJSON());
        }
        if(selection!=null){
            sb.append(", \"selection\": ");
            sb.append(getSelectionJSON());
        }
        if(annotations!=null){
            sb.append(", \"annotations\": ");
            sb.append(getAnnotationsJSON());
        }

        sb.append("}");
        return sb.toString();
    }
}
