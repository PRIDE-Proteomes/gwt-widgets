package uk.ac.ebi.pride.widgets.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import uk.ac.ebi.pride.widgets.client.sequence.client.SequenceViewer;
import uk.ac.ebi.pride.widgets.client.sequence.data.Peptide;
import uk.ac.ebi.pride.widgets.client.sequence.data.Protein;
import uk.ac.ebi.pride.widgets.client.sequence.data.ProteinModification;
import uk.ac.ebi.pride.widgets.client.sequence.type.Pride;
import uk.ac.ebi.pride.widgets.client.sequence.type.SequenceType;

import java.util.LinkedList;
import java.util.List;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PrideWidgetsTest implements EntryPoint {
    // IMPORTANT! ATTENTION!
    // Do NOT use the class name for the place holder ( but it is case sensitive :D )
    private static final String PLACE_HOLDER = "pridew";

    @Override
    public void onModuleLoad() {
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {

            @Override
            public void execute() {
                VerticalPanel vp = new VerticalPanel();
                vp.add(new HTMLPanel("Pride GWT Widgets"));

                Protein protein = new Protein() {
                    @Override
                    public Integer getLength() {
                        return getSequence().length();
                    }

                    @Override
                    public String getSequence() {
                        return "" +
                                "MKWVTFISLLFLFSSAYSRGVFRRDAHKSEVAHRFKDLGEENFKAWAVARLSQRFPKAEF" +
                                "AEVSKLVTDLTKVHTECCHGDLLECADDRADLAKYICENQDSISSKLKECCEKPLLEKSH" +
                                "CIAEVENDEMPADLPSLAADFVESKDVCKNYAEAKDVFLGMFLYEYARRHPDYSVVLLLR" +
                                "LAKTYETTLEKCCAAADPHECYAKVFDEFKPLVEEPQNLIKQNCELFEQLGEYKFQNALL" +
                                "VRYTKKVPQVSTPTLVEVSRNLGKVGSKCCKHPEAKRMPCAEDYLSVVLNQLCVLHEKTP" +
                                "VSDRVTKCCTESLVNRRPCFSALEVDETYVPKEFNAETFTFHADICTLSEKERQIKKQTA" +
                                "LVELVKHKPKATKEQLKAVMDDFAAFVEKCCKADDKETCFAEEGKKLVAASQAALGL";
                    }

                    @Override
                    public List<ProteinModification> getModifications() {
                        return new LinkedList<ProteinModification>();
                    }

                    @Override
                    public List<Peptide> getPeptides() {
                        return new LinkedList<Peptide>();
                    }
                };

                SequenceType sequenceType = new Pride();
                vp.add(new SequenceViewer(sequenceType, protein));

                RootPanel.get(PLACE_HOLDER).add(vp);

            }
        });
    }
}
