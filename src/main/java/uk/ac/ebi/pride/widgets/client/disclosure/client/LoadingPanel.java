package uk.ac.ebi.pride.widgets.client.disclosure.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import uk.ac.ebi.pride.widgets.client.disclosure.images.DisclosureImages;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 * @author Javier Contell <jcontell@ebi.ac.uk>
 */
public class LoadingPanel extends HTMLPanel {

    /**
     * A ClientBundle of resources used by this widget.
     */
    public interface Resources extends ClientBundle {
        /**
         * The styles used in this widget.
         */
        @Source(Style.DEFAULT_CSS)
        Style loadingPanelStyle();
    }

    /**
     * Styles used by this widget.
     */
    @CssResource.ImportedWithPrefix("pride-LoadingPanel")
    public interface Style extends CssResource {
        /**
         * The path to the default CSS styles used by this resource.
         */
        String DEFAULT_CSS = "uk/ac/ebi/pride/widgets/client/disclosure/css/LoadingPanel.css";

        /**
         * Applied to the widget.
         */
        String loadingPanelContainer();

        /**
         * Applied to the loading image
         */
        String loadingImage();

        /**
         * Applied to the loading text label
         */
        String loadingMessage();
    }

    private static Resources DEFAULT_RESOURCES;

    /**
     * Get the default {@link Resources} for this widget.
     */
    private static Resources getDefaultResources() {
        if (DEFAULT_RESOURCES == null) {
            DEFAULT_RESOURCES = GWT.create(Resources.class);
        }
        return DEFAULT_RESOURCES;
    }

    public LoadingPanel() {
        this(getDefaultResources());
    }

    public LoadingPanel(Resources resources) {
        super("");

        // Inject the styles used by this widget.
        Style style = resources.loadingPanelStyle();
        style.ensureInjected();
        setStyleName(style.loadingPanelContainer());
        //noinspection GWTStyleCheck
        addStyleName("clearfix");

        Image loadingImage = new Image(DisclosureImages.INSTANCE.getLoadingImage());
        loadingImage.setStyleName(style.loadingImage());
        add(loadingImage);

        InlineLabel loadingMessage = new InlineLabel("Loading...");
        loadingMessage.setStyleName(style.loadingMessage());
        add(loadingMessage);
    }
}
