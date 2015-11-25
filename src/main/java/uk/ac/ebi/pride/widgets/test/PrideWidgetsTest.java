package uk.ac.ebi.pride.widgets.test;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import uk.ac.ebi.pride.widgets.client.feature.client.FeatureViewer;
import uk.ac.ebi.pride.widgets.client.protein.client.ProteinViewer;
import uk.ac.ebi.pride.widgets.client.sequence.client.SequenceViewer;
import uk.ac.ebi.pride.widgets.client.sequence.type.Pride;
import uk.ac.ebi.pride.widgets.client.table.events.SingleSelectionChangeEvent;
import uk.ac.ebi.pride.widgets.client.table.events.TableResetEvent;
import uk.ac.ebi.pride.widgets.client.table.handlers.SingleSelectionChangeHandler;
import uk.ac.ebi.pride.widgets.client.table.handlers.TableResetHandler;
import uk.ac.ebi.pride.widgets.test.data.factory.ModelFactory;
import uk.ac.ebi.pride.widgets.test.data.factory.ModelFactoryException;
import uk.ac.ebi.pride.widgets.test.data.model.Peptide;
import uk.ac.ebi.pride.widgets.test.data.model.Protein;
import uk.ac.ebi.pride.widgets.test.data.proxy.ProteinProxy;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PrideWidgetsTest implements EntryPoint, RequestCallback, SingleSelectionChangeHandler<Peptide>, TableResetHandler {
    // IMPORTANT! ATTENTION!
    // Do NOT use the class name for the place holder ( but it is case sensitive :D )
    private static final String PLACE_HOLDER = "pridew";
    private static final String JSON_URL = "http://localhost:9190/protein/A0A067XG54?includeDetails=true";

    private final VerticalPanel vp = new VerticalPanel();
    private Label errorMsgLabel;

    @Override
    public void onModuleLoad() {
        final RequestCallback rc = this;
        vp.add(new HTMLPanel("Pride GWT Widgets Test"));
        errorMsgLabel = new Label();
        vp.add(errorMsgLabel);

        RootPanel.get(PLACE_HOLDER).add(vp);
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {

            @Override
            public void execute() {
                String url = URL.encode(JSON_URL);
                RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, url);
                requestBuilder.setHeader("Accept", "application/json");
                try {
                    requestBuilder.sendRequest(null, rc);
                } catch (RequestException ex) {
                    displayError("Couldn't retrieve JSON trying offline example");
                    offLineExample();
                }
            }
        });
    }

    @Override
    public void onResponseReceived(Request request, Response response) {
        Protein protein = null;

        if (200 == response.getStatusCode()) {
            try {
                protein = ModelFactory.getModelObject(Protein.class, response.getText());
            } catch (ModelFactoryException ex) {
                displayError("Couldn't convert JSON to the model");
            }
        } else {
            displayError("Couldn't retrieve JSON (status code: " + response.getStatusCode() + "). Trying offline example");
            protein = offLineExample();
        }

        if (protein != null) {

            //PeptideHandler end is used very often in the webapp, and make sense to keep it calculated
            for (Peptide peptide : protein.getPeptides()) {
                int end = peptide.getPosition() + peptide.getSequence().length() - 1;
                peptide.setProteinId(protein.getAccession());
                peptide.setEnd(end);
            }

            protein.setLength(protein.getSequence().length());
            ProteinProxy proteinProxy = new ProteinProxy(protein);

            ProteinViewer proteinViewer = new ProteinViewer(1020, 90, proteinProxy);
            vp.add(proteinViewer);

            FeatureViewer featureViewer = new FeatureViewer(1020, 20, proteinProxy); //Optimize to 20
            vp.add(featureViewer);

            SequenceViewer sequenceViewer = new SequenceViewer(new Pride(), proteinProxy);
            vp.add(sequenceViewer);

//            final SingleSelectionTable<Peptide> table = new SingleSelectionTable<Peptide>();
//            table.setList(protein.getPeptides());
//            table.addSingleSelectionChangeHandler(this);
//            table.addTableResetHandler(this);
//            // Create sequence column.
//            table.addColumn(new TextColumn<Peptide>() {
//                @Override
//                public String getValue(Peptide peptide) {
//                    return peptide.getSequence();
//                }
//            });
//
//            // Create site column.
//            table.addColumn(new TextColumn<Peptide>() {
//                @Override
//                public String getValue(Peptide peptide) {
//                    return peptide.getSite().toString();
//                }
//            });
//
//            // Create psmHits column.
//            table.addColumn(new TextColumn<Peptide>() {
//                @Override
//                public String getValue(Peptide peptide) {
//                    return peptide.getPsmHits().toString();
//                }
//            });
//
//            //ResizeLayoutPanel tableContainer = new ResizeLayoutPanel();
//            table.setHeight("150px");
//            //tableContainer.add(table);
//            table.setPageSize(protein.getPeptides().size());

//            vp.add(table);

            RootPanel.get(PLACE_HOLDER).add(vp);
        } else {
            displayError("Couldn't convert JSON to the model");
        }

    }

    @Override
    public void onError(Request request, Throwable exception) {
        displayError("Exception has happened querying the web service. Trying offline example");
        offLineExample();
    }

    @Override
    public void onSelectionChange(SingleSelectionChangeEvent<Peptide> event) {
        System.out.println(event.getItem().getSequence());
    }

    @Override
    public void onTableReset(TableResetEvent eventTable) {
        System.out.println("Table selection reset :)");
    }

    private Protein offLineExample() {

        Protein protein = null;

        try {
            String jsonProtein =
                    "{" +
                            "\"accession\":\"A0A067XG54\"," +
                            "\"genes\":[\"ENSG00000101974\"]," +
                            "\"taxonID\":9606," +
                            "\"sequence\":" +
                            "\"MFRRSLNRFCAGEEKRVGTRTVFVGNHPVSETEAYIAQRFCDNRIVSSKYTLWNFLPKNLFEQFRRIANFYFLIIFLVQVTVDTPTSPVTSGLP" +
                            "LFFVITVTAIKQGYEDCLRHRADNEVNKSTVYIIENAKRVRKESEKIKVGDVVEVQADETFPCDLILLSSCTTDGTCYVTTASLDGESNCKTHYAV" +
                            "RDTIALCTAESIDTLRAAIECEQPQPDLYKFVGRINIYSNSLEAVARSLGPENLLLKGATLKNTEKIYGVAVYTGMETKMALNYQGKSQKRSAVEK" +
                            "SINAFLIVYLFILLTKAAVCTTLKYVWQSTPYNDEPWYNQKTQKERETLKVLKMFTDFLSFMVLFNFIIPVSMYVTVEMQKFLGSFFISWDKDFYD" +
                            "EEINEGALVNTSDLNEELGQVDYVFTDKTGTLTENSMEFIECCIDGHKYKGVTQEVDGLSQTDGTLTYFDKVDKNREELFLRALCLCHTVEIKTND" +
                            "AVDGATESAELTYISSSPDEIALVKGAKRYGFTFLGNRNGYMRVENQRKEIEEYELLHTLNFDAVRRRMSVIVKTQEGDILLFCKGADSAVFPRVQ" +
                            "NHEIELTKVHVERNAMDGYRTLCVAFKEIAPDDYERINRQLIEAKMALQDREEKMEKVFDDIETNMNLIGATAVEDKLQDQAAETIEALHAAGLKV" +
                            "WVLTGDKMETAKSTCYACRLFQTNTELLELTTKTIEESERKEDRLHELLIEYRKKLLHEFPKSTRSFKKAWTEHQEYGLIIDGSTLSLILNSSQDS" +
                            "SSNNYKSIFLQICMKCTAVLCCRMAPLQKAQIVRMVKNLKGSPITLSIGDGANDVSMILESHVGIGKEGRQAARNSDYSVPKFKHLKKLLLAHGHL" +
                            "YYVRIAHLVQYFFYKNLCFILPQFLYQFFCGFSQQPLYDAAYLTMYNICFTSLPILAYSLLEQHINIDTLTSDPRLYMKISGNAMLQLGPFLYWTF" +
                            "LAAFEGTVFFFGTYFLFQTASLEENGKVYGNWTFGTIVFTVLVFTVTLKLALDTRFWTWINHFVIWGSLAFYVFFSFFWGGIIWPFLKQQRMYFVF" +
                            "AQMLSSVSTWLAIILLIFISLFPEILLIVLKNVRRRSARNPNLELPMLLSYKHTDSGYS\"," +
                            "\"description\":\"A0A067XG54_HUMAN Phospholipid-translocating ATPase OS=Homo sapiens GN=ATP11C PE=1 SV=1\"," +
                            "\"modifiedLocations\":[]," +
                            "\"tissues\":[\"CELL_CULTURE\",\"COLORECTAL_CANCER_CELL_LINE\",\"LIVER\",\"SW_480_CELL\"]," +
                            "\"peptides\":[" +
                            "{" +
                            "\"id\":\"[AAIECEQPQPDLYK|9606]\"," +
                            "\"symbolic\":true," +
                            "\"sequence\":\"AAIECEQPQPDLYK\"," +
                            "\"taxonID\":9606," +
                            "\"modifiedLocations\":[{\"position\":5,\"modification\":\"CARBAMIDOMETHYL\"}]," +
                            "\"tissues\":[\"JURKAT_CELL\",\"LIVER\"]," +
                            "\"assays\":[\"31019\",\"31725\",\"31726\"]," +
                            "\"clusters\":[\"5374848\"]," +
                            "\"position\":207," +
                            "\"uniqueness\":2," +
                            "\"sharedProteins\":[\"Q8NB49-2\",\"Q8NB49-3\",\"Q8NB49\",\"Q8NB49-4\",\"A0A067XG54\"]," +
                            "\"sharedGenes\":[\"ENSG00000101974\"]" +
                            "}," +
                            "{" +
                            "\"id\":\"[LFQTNTELLELTTK|9606]\"," +
                            "\"symbolic\":true," +
                            "\"sequence\":\"LFQTNTELLELTTK\"," +
                            "\"taxonID\":9606," +
                            "\"modifiedLocations\":[]," +
                            "\"tissues\":[\"CELL_CULTURE\",\"LIVER\"]," +
                            "\"assays\":[\"31527\",\"31575\",\"31576\",\"31711\",\"31712\",\"33078\"]," +
                            "\"clusters\":[\"5423750\",\"5425311\"]," +
                            "\"position\":690," +
                            "\"uniqueness\":2," +
                            "\"sharedProteins\":[\"Q8NB49-2\",\"Q8NB49\",\"Q8NB49-3\",\"Q8NB49-4\",\"A0A067XG54\"]," +
                            "\"sharedGenes\":[\"ENSG00000101974\"]" +
                            "}," +
                            "{" +
                            "\"id\":\"[LQDQAAETIEALHAAGLK|9606]\"," +
                            "\"symbolic\":true," +
                            "\"sequence\":\"LQDQAAETIEALHAAGLK\"," +
                            "\"taxonID\":9606," +
                            "\"modifiedLocations\":[]," +
                            "\"tissues\":[\"LIVER\"]," +
                            "\"assays\":[\"31535\",\"31536\",\"31583\",\"31584\",\"31749\",\"31750\"]," +
                            "\"clusters\":[\"6121352\"]," +
                            "\"position\":652," +
                            "\"uniqueness\":2," +
                            "\"sharedProteins\":[\"Q8NB49-2\",\"Q8NB49-3\",\"Q8NB49\",\"Q8NB49-4\",\"A0A067XG54\"]," +
                            "\"sharedGenes\":[\"ENSG00000101974\"]" +
                            "}," +
                            "{" +
                            "\"id\":\"[SLGPENLLLK|9606]\"," +
                            "\"symbolic\":true," +
                            "\"sequence\":\"SLGPENLLLK\"," +
                            "\"taxonID\":9606," +
                            "\"modifiedLocations\":[]," +
                            "\"tissues\":[\"LIVER\"]," +
                            "\"assays\":[\"9039\",\"31881\",\"31882\"]," +
                            "\"clusters\":[\"1789046\",\"1798829\"]," +
                            "\"position\":238," +
                            "\"uniqueness\":2," +
                            "\"sharedProteins\":[\"Q8NB49-2\",\"Q8NB49-3\",\"Q8NB49\",\"Q8NB49-4\",\"A0A067XG54\"]," +
                            "\"sharedGenes\":[\"ENSG00000101974\"]" +
                            "}," +
                            "{" +
                            "\"id\":\"[YTLWNFLPK|9606]\"," +
                            "\"symbolic\":true," +
                            "\"sequence\":" +
                            "\"YTLWNFLPK\"," +
                            "\"taxonID\":9606," +
                            "\"modifiedLocations\":[]," +
                            "\"tissues\":[\"COLORECTAL_CANCER_CELL_LINE\",\"LIVER\",\"SW_480_CELL\"]," +
                            "\"assays\":[\"21768\",\"31731\",\"31732\",\"31862\"]," +
                            "\"clusters\":[\"2442144\",\"2442504\"]," +
                            "\"position\":50," +
                            "\"uniqueness\":2," +
                            "\"sharedProteins\":[\"Q8NB49-2\",\"Q8NB49-3\",\"Q8NB49\",\"Q8NB49-4\",\"A0A067XG54\"]," +
                            "\"sharedGenes\":[\"ENSG00000101974\"]}" +
                            "]," +
                            "\"features\":[" +
                            "{\"id\":662,\"type\":\"SIGNAL\",\"start\":1,\"end\":100}," +
                            "{\"id\":663,\"type\":\"TRANSMEM\",\"start\":110,\"end\":130}," +
                            "{\"id\":664,\"type\":\"TOPO_DOM\",\"start\":131,\"end\":166}," +
                            "{\"id\":665,\"type\":\"TRANSMEM\",\"start\":166,\"end\":186}," +
                            "{\"id\":666,\"type\":\"INTRAMEM\",\"start\":187,\"end\":200}," +
                            "{\"id\":667,\"type\":\"TRANSMEM\",\"start\":201,\"end\":221}," +
                            "{\"id\":668,\"type\":\"SITE\",\"start\":222,\"end\":264}," +
                            "{\"id\":669,\"type\":\"TRANSMEM\",\"start\":265,\"end\":285}" +
                            "]," +
                            "\"uniquePeptideToProteinCount\":0," +
                            "\"uniquePeptideToIsoformCount\":0," +
                            "\"uniquePeptideToGeneCount\":0," +
                            "\"nonUniquePeptidesCount\":0," +
                            "\"coverage\":null," +
                            "\"regions\":null" +
                            "}";

            protein = ModelFactory.getModelObject(Protein.class, jsonProtein);

        } catch (ModelFactoryException e) {
            displayError("Coudn't convert the JSON");
        }

        return protein;
    }

    /**
     * If can't get JSON, display error message.
     *
     * @param error
     */
    private void displayError(String error) {
        errorMsgLabel.setText("Error: " + error);
        errorMsgLabel.setVisible(true);
    }

}
