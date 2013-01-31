package uk.ac.ebi.pride.widgets.test;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.http.client.*;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.*;
import uk.ac.ebi.pride.widgets.client.protein.client.ProteinViewer;
import uk.ac.ebi.pride.widgets.client.sequence.client.SequenceViewer;
import uk.ac.ebi.pride.widgets.client.sequence.type.Pride;
import uk.ac.ebi.pride.widgets.client.sequence.type.SequenceType;
import uk.ac.ebi.pride.widgets.client.table.client.SingleSelectionTable;
import uk.ac.ebi.pride.widgets.client.table.events.SingleSelectionChangeEvent;
import uk.ac.ebi.pride.widgets.client.table.events.TableResetEvent;
import uk.ac.ebi.pride.widgets.client.table.handlers.SingleSelectionChangeHandler;
import uk.ac.ebi.pride.widgets.client.table.handlers.TableResetHandler;
import uk.ac.ebi.pride.widgets.examples.basic.client.BasicViewer;
import uk.ac.ebi.pride.widgets.examples.basic.events.BookSelectedEvent;
import uk.ac.ebi.pride.widgets.examples.basic.events.BoxSelectedEvent;
import uk.ac.ebi.pride.widgets.examples.basic.handlers.BookSelectedHandler;
import uk.ac.ebi.pride.widgets.examples.basic.handlers.BoxSelectedHandler;
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

    @Override
    public void onModuleLoad() {
        basicExample();

//        final RequestCallback rc = this;
//
//        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
//
//            @Override
//            public void execute() {
//                String url = "/prideq-web-service/human_up/protein/P02768"; //A0AVT1";
//                RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, url);
//                requestBuilder.setHeader("Accept", "application/json");
//                try {
//                    requestBuilder.sendRequest(null, rc);
//                } catch (RequestException ex) {
//                    //TODO
//                }
//
//            }
//        });
    }

    @Override
    public void onResponseReceived(Request request, Response response) {
        try {
            final Protein protein = ModelFactory.getModelObject(Protein.class, response.getText());
            //PeptideHandler end is used very often in the webapp, and make sense to keep it calculated
            for (Peptide peptide : protein.getPeptides()) {
                int end = peptide.getSite() + peptide.getSequence().length() - 1;
                peptide.setEnd(end);
            }


            VerticalPanel vp = new VerticalPanel();
            vp.add(new HTMLPanel("Pride GWT Widgets"));

            @SuppressWarnings("Convert2Diamond")
            final SingleSelectionTable<Peptide> table = new SingleSelectionTable<Peptide>();
            table.setList(protein.getPeptides());
            table.addSingleSelectionChangeHandler(this);
            table.addTableResetHandler(this);
            // Create sequence column.
            table.addColumn(new TextColumn<Peptide>() {
                @Override
                public String getValue(Peptide peptide) {
                    return peptide.getSequence();
                }
            });

            // Create site column.
            table.addColumn(new TextColumn<Peptide>() {
                @Override
                public String getValue(Peptide peptide) {
                    return peptide.getSite().toString();
                }
            });

            // Create psmHits column.
            table.addColumn(new TextColumn<Peptide>() {
                @Override
                public String getValue(Peptide peptide) {
                    return peptide.getPsmHits().toString();
                }
            });

            //ResizeLayoutPanel tableContainer = new ResizeLayoutPanel();
            table.setHeight("150px");
            //tableContainer.add(table);
            table.setPageSize(protein.getPeptides().size());

            vp.add(table);

            ProteinProxy proteinProxy = new ProteinProxy(protein);

            ProteinViewer proteinViewer = new ProteinViewer(proteinProxy);
            proteinViewer.setSelectedArea(187, 198);
            vp.add(proteinViewer);

            SequenceType sequenceType = new Pride();
            SequenceViewer sequenceViewer = new SequenceViewer(sequenceType, proteinProxy);
            vp.add(sequenceViewer);


            RootPanel.get(PLACE_HOLDER).add(vp);
        } catch (ModelFactoryException e) {
            //TODO
        }
    }

    @Override
    public void onError(Request request, Throwable exception) {
        //TODO
    }

    @Override
    public void onSelectionChange(SingleSelectionChangeEvent<Peptide> event) {
        System.out.println(event.getItem().getSequence());
    }

    @Override
    public void onTableReset(TableResetEvent eventTable) {
        System.out.println("Table selection reset :)");
    }

    private void basicExample(){
        VerticalPanel vp = new VerticalPanel();
        vp.add(new HTMLPanel("Basic example"));

        BasicViewer basicViewer = new BasicViewer(900, 500);
        basicViewer.addBookSelectedHandler(new BookSelectedHandler() {
            @Override
            public void onBookSelected(BookSelectedEvent e) {
                System.out.println("Book selected: " + e.getBook().getCode());
            }
        });
        basicViewer.addBoxSelectedHandler(new BoxSelectedHandler() {
            @Override
            public void onBoxSelected(BoxSelectedEvent e) {
                System.out.println("Box selected: " + e.getBox().getCode());
            }
        });

        vp.add(basicViewer);

        RootPanel.get(PLACE_HOLDER).add(vp);
    }
}
