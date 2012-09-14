package uk.ac.ebi.pride.widgets.biojs.common;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTMLPanel;
import uk.ac.ebi.pride.widgets.biojs.sequence.options.SequenceParameters;

public abstract class BioJsPanel extends HTMLPanel {
    protected SequenceParameters parameters;

    public BioJsPanel(SequenceParameters parameters) {
        super("");
        if(parameters.getTarget()==null || parameters.getTarget().isEmpty()){
            parameters.setHTMLPanel(this);
        }else{
            parameters.setHTMLPanel(wrap(DOM.getElementById(parameters.getTarget())));
            //initializeBioJsObject();
        }
        this.parameters = parameters;
    }

    protected abstract void initializeBioJsObject();

    @Override
    protected void onLoad() {
        super.onLoad();
        initializeBioJsObject();
    }
}