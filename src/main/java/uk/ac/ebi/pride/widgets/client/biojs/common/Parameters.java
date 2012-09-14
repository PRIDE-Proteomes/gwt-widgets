package uk.ac.ebi.pride.widgets.client.biojs.common;

import com.google.gwt.user.client.ui.HTMLPanel;

public abstract class Parameters {
    //Required Parameters
    protected String target;
    protected HTMLPanel panel;

    public void setHTMLPanel(HTMLPanel panel){
        this.panel = panel;
        this.target = getOrCreateDivId(panel);
    }

    public String getTarget() {
        return target;
    }

    private static String getOrCreateDivId(HTMLPanel panel){
        String divId = panel.getElement().getId();
        if(divId.isEmpty()){
            divId = HTMLPanel.createUniqueId();
            panel.getElement().setId(divId);
        }
        return  divId;
    }
}
