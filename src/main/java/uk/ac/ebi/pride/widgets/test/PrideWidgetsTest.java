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

//import uk.ac.ebi.pride.widgets.examples.basic.client.BasicViewer;
//import uk.ac.ebi.pride.widgets.examples.basic.events.BookSelectedEvent;
//import uk.ac.ebi.pride.widgets.examples.basic.events.BoxSelectedEvent;
//import uk.ac.ebi.pride.widgets.examples.basic.handlers.BookSelectedHandler;
//import uk.ac.ebi.pride.widgets.examples.basic.handlers.BoxSelectedHandler;

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
        if (200 == response.getStatusCode()) {
            try {
                final Protein protein = ModelFactory.getModelObject(Protein.class, response.getText());
                //PeptideHandler end is used very often in the webapp, and make sense to keep it calculated
                for (Peptide peptide : protein.getPeptides()) {
                    int end = peptide.getPosition() + peptide.getSequence().length() - 1;
                    peptide.setEnd(end);
                }


//            @SuppressWarnings("Convert2Diamond")
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

                ProteinProxy proteinProxy = new ProteinProxy(protein);

                ProteinViewer proteinViewer = new ProteinViewer(proteinProxy);
//            proteinViewer.setSelectedArea(187, 198);
                vp.add(proteinViewer);

                FeatureViewer featureViewer = new FeatureViewer(proteinProxy);
                vp.add(featureViewer);

//            SequenceType sequenceType = new Pride();
//            SequenceViewer sequenceViewer = new SequenceViewer(sequenceType, proteinProxy);
//            vp.add(sequenceViewer);


                RootPanel.get(PLACE_HOLDER).add(vp);
            } catch (ModelFactoryException ex) {
                displayError("Couldn't convert JSON to the model");
            }
        } else {
            displayError("Couldn't retrieve JSON (status code: " + response.getStatusCode() + "). Trying offline example");
            offLineExample();
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

    private void offLineExample() {

        try {
            String jsonProtein =
                    "{" +
                    "\"accession\":\"A0A067XG54\"," +
                    "\"gene\":\"ENSG00000101974\"," +
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
                            "\"id\":\"[KGATLK|9606]\"," +
                            "\"symbolic\":true," +
                            "\"sequence\":\"KGATLK\"," +
                            "\"taxonID\":9606," +
                            "\"modifiedLocations\":[]," +
                            "\"tissues\":[\"LIVER\"]," +
                            "\"assays\":[\"17685\",\"31515\",\"31516\",\"31517\",\"31518\",\"31563\",\"31564\",\"31567\",\"31605\",\"31606\"]," +
                            "\"clusters\":[],\"" +
                            "position\":247," +
                            "\"uniqueness\":17" +
                        "}," +
                        "{" +
                            "\"id\":\"[LFQTNTELLELTTK|9606]\"," +
                            "\"symbolic\":true," +
                            "\"sequence\":\"LFQTNTELLELTTK\"," +
                            "\"taxonID\":9606," +
                            "\"modifiedLocations\":[]," +
                            "\"tissues\":[\"CELL_CULTURE\",\"COLORECTAL_CANCER_CELL_LINE\",\"LIVER\",\"SW_480_CELL\"]," +
                            "\"assays\":[\"21768\",\"31527\",\"31575\",\"31576\",\"31711\",\"31712\",\"33078\"]," +
                            "\"clusters\":[]," +
                            "\"position\":690," +
                            "\"uniqueness\":5" +
                        "}," +
                        "{" +
                            "\"id\":\"[SLGPENLLLK|9606]\"," +
                            "\"symbolic\":true," +
                            "\"sequence\":\"SLGPENLLLK\"," +
                            "\"taxonID\":9606," +
                            "\"modifiedLocations\":[]," +
                            "\"tissues\":[\"CELL_CULTURE\",\"LIVER\"]," +
                            "\"assays\":[\"9039\",\"31882\",\"33074\"]," +
                            "\"clusters\":[]," +
                            "\"position\":238," +
                            "\"uniqueness\":5" +
                        "}," +
                        "{" +
                            "\"id\":\"[YTLWNFLPK|9606]\"," +
                            "\"symbolic\":true," +
                            "\"sequence\":\"YTLWNFLPK\"," +
                            "\"taxonID\":9606," +
                            "\"modifiedLocations\":[]," +
                            "\"tissues\":[\"COLORECTAL_CANCER_CELL_LINE\",\"SW_480_CELL\"]," +
                            "\"assays\":[\"21768\"],\"clusters\":[]," +
                            "\"position\":50," +
                            "\"uniqueness\":5" +
                        "}" +
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
                    "\"uniquePeptideCount\":0," +
                    "\"coverage\":null," +
                    "\"regions\":null" +
                    "}";

            final Protein protein = ModelFactory.getModelObject(Protein.class, jsonProtein);

            //PeptideHandler end is used very often in the webapp, and make sense to keep it calculated
            for (Peptide peptide : protein.getPeptides()) {
                int end = peptide.getPosition() + peptide.getSequence().length() - 1;
                peptide.setProteinId(protein.getAccession());
                peptide.setEnd(end);
            }

            protein.setLength(protein.getSequence().length());
            ProteinProxy proteinProxy = new ProteinProxy(protein);

            ProteinViewer proteinViewer = new ProteinViewer(proteinProxy);
            proteinViewer.setSelectedArea(187, 198);
            vp.add(proteinViewer);

            FeatureViewer featureViewer = new FeatureViewer(proteinProxy);
            featureViewer.setSelectedArea(187, 198);
            vp.add(featureViewer);

            SequenceViewer sequenceViewer = new SequenceViewer(new Pride(), proteinProxy);
            vp.add(sequenceViewer);

        } catch (ModelFactoryException e) {
            displayError("Coudn't convert the JSON");
        }
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
