package uk.ac.ebi.pride.widgets.biojs.sequence.options;

import java.util.List;

public class SequenceAnnotation implements SequenceOptions{
    private String name;
    private String html;
    private String color;
    private List<SequenceAnnotationRegion> regions;

    public SequenceAnnotation(String name, String html, String color, List<SequenceAnnotationRegion> regions) {
        this.name = name;
        this.html = html;
        this.color = color;
        this.regions = regions;
    }

    public String getName() {
        return name;
    }

    public String getHtml() {
        return html;
    }

    public String getColor() {
        return color;
    }

    public List<SequenceAnnotationRegion> getRegions() {
        return regions;
    }

    private String getRegionsJSON(){
        if(regions==null || regions.size()==0){
            return "[]";
        }else{
            StringBuilder json = new StringBuilder("[");
            for (SequenceRegion region : regions) {
                json.append(region.getJSON());
                json.append(",");
            }
            json.deleteCharAt(json.length()-1);
            json.append("]");

            return json.toString();
        }
    }

    @Override
    public String getJSON(){
        StringBuilder sb = new StringBuilder();
        sb.append("{\"name\": \"");
        sb.append(name);
        sb.append("\", \"html\": \"");
        sb.append(html);
        sb.append("\", \"color\": \"");
        sb.append(color);
        sb.append("\", \"regions\": ");
        sb.append(getRegionsJSON());
        sb.append("}");

        return sb.toString();
    }
}
