package uk.ac.ebi.pride.widgets.test;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import uk.ac.ebi.pride.widgets.client.protein.client.ProteinViewer;
import uk.ac.ebi.pride.widgets.client.sequence.client.SequenceViewer;
import uk.ac.ebi.pride.widgets.client.sequence.type.Pride;
import uk.ac.ebi.pride.widgets.client.sequence.type.SequenceType;
import uk.ac.ebi.pride.widgets.test.data.factory.ModelFactory;
import uk.ac.ebi.pride.widgets.test.data.factory.ModelFactoryException;
import uk.ac.ebi.pride.widgets.test.data.model.Peptide;
import uk.ac.ebi.pride.widgets.test.data.model.Protein;
import uk.ac.ebi.pride.widgets.test.data.proxy.ProteinProxy;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PrideWidgetsTest implements EntryPoint, RequestCallback {
    // IMPORTANT! ATTENTION!
    // Do NOT use the class name for the place holder ( but it is case sensitive :D )
    private static final String PLACE_HOLDER = "pridew";

    @Override
    public void onModuleLoad() {
        final RequestCallback rc = this;

        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {

            @Override
            public void execute() {
                String url = "/prideq-web-service/human_up/protein/P02768"; //A0AVT1";
                RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, url);
                requestBuilder.setHeader("Accept", "application/json");
                try {
                    requestBuilder.sendRequest(null, rc);
                } catch (RequestException ex) {
                    //TODO
                }

            }
        });
    }

    @Override
    public void onResponseReceived(Request request, Response response) {
        try {
            Protein protein = ModelFactory.getModelObject(Protein.class, response.getText());
            //PeptideHandler end is used very often in the webapp, and make sense to keep it calculated
            for (Peptide peptide : protein.getPeptides()) {
                int end = peptide.getSite() + peptide.getSequence().length() - 1;
                peptide.setEnd(end);
            }


            VerticalPanel vp = new VerticalPanel();
            vp.add(new HTMLPanel("Pride GWT Widgets"));
            if(protein!=null){
                ProteinProxy proteinProxy = new ProteinProxy(protein);

                vp.add(new ProteinViewer(proteinProxy));

                SequenceType sequenceType = new Pride();
                vp.add(new SequenceViewer(sequenceType, proteinProxy));
            }else{
                vp.add(new Label("Protein is NULL"));
            }

            RootPanel.get(PLACE_HOLDER).add(vp);
        } catch (ModelFactoryException e) {
            //TODO
        }
    }

    @Override
    public void onError(Request request, Throwable exception) {
        //TODO
    }
}
