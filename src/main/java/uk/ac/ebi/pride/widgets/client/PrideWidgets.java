package uk.ac.ebi.pride.widgets.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PrideWidgets implements EntryPoint {
    // IMPORTANT! ATTENTION!
    // Do NOT use the class name for the place holder ( but it is case sensitive :D )
    private static final String PLACE_HOLDER = "pridew";

    @Override
    public void onModuleLoad() {
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {

            @Override
            public void execute() {

                RootPanel.get(PLACE_HOLDER).add(new HTMLPanel("Pride GWT Widgets"));

            }
        });
    }
}
