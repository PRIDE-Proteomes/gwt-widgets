package uk.ac.ebi.pride.widgets.test.data.factory;

import com.google.gwt.http.client.*;
import uk.ac.ebi.pride.widgets.test.data.model.Peptide;
import uk.ac.ebi.pride.widgets.test.data.model.Protein;

public class ProteinRetriever implements RequestCallback {

    public ProteinRetriever() {
    }

    public void loadProtein(String accession){
        String url = "/prideq-web-service/human_up/protein/" + accession;
        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, url);
        requestBuilder.setHeader("Accept", "application/json");
        try {
            requestBuilder.sendRequest(null, this);
        } catch (RequestException ex) {
            //TODO
        }
    }

    @Override
    public void onResponseReceived(Request request, Response response) {
        if(response.getStatusText().equals("OK")){
            try{
                Protein protein = ModelFactory.getModelObject(Protein.class, response.getText());
                //PeptideHandler end is used very often in the webapp, and make sense to keep it calculated
                for (Peptide peptide : protein.getPeptides()) {
                    int end = peptide.getSite() + peptide.getSequence().length() - 1;
                    peptide.setEnd(end);
                }
            }catch (ModelFactoryException e){
                //TODO
            }
        }else{
            //TODO
        }
    }

    @Override
    public void onError(Request request, Throwable exception) {
        //TODO
    }
}
