package uk.ac.ebi.pride.widgets.client.disclosure.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.*;
import uk.ac.ebi.pride.widgets.client.disclosure.images.DisclosureImages;

public class DisclosureHeader extends HTMLPanel implements OpenHandler<DisclosurePanel>,
        CloseHandler<DisclosurePanel>, MouseOverHandler, MouseOutHandler {

    /**
     * A ClientBundle of resources used by this widget.
     */
    public interface Resources extends ClientBundle {
        /**
         * The styles used in this widget.
         */
        @Source(Style.DEFAULT_CSS)
        Style disclosureHeaderStyle();
    }

    /**
     * Styles used by this widget.
     */
    @CssResource.ImportedWithPrefix("pride-DisclosureHeader")
    public interface Style extends CssResource {
        /**
         * The path to the default CSS styles used by this resource.
         */
        String DEFAULT_CSS = "uk/ac/ebi/pride/widgets/client/disclosure/css/DisclosureHeader.css";

        /**
         * Applied to the widget.
         */
        String disclosureHeaderContainer();

        /**
         * Applied to the left icon
         */
        String disclosureHeaderIcon();

        /**
         * Applied to the text label
         */
        String disclosureHeaderTitle();

        /**
         * Applied to the selection text labels
         */
        String disclosureHeaderPrimaryMessage();

        /**
         * Applied to the number of items labels
         */
        String disclosureHeaderSecondaryMessage();

        /**
         * Applied to the widget button
         */
        String disclosureHeaderButton();
    }

    private static Resources DEFAULT_RESOURCES;

    private boolean iconAlwaysVisible = true;

    private Panel buttonContainer = new HTMLPanel("");

    private final Widget openedIcon;
    private final Widget closedIcon;

    private InlineLabel primaryMessage = new InlineLabel();
    private InlineLabel numberMessage = new InlineLabel();

    /**
     * Get the default {@link Resources} for this widget.
     */
    private static Resources getDefaultResources() {
        if (DEFAULT_RESOURCES == null) {
            DEFAULT_RESOURCES = GWT.create(Resources.class);
        }
        return DEFAULT_RESOURCES;
    }

    public DisclosureHeader(DisclosurePanel dp, String text) {
        this(getDefaultResources(), dp, text);
    }

    public DisclosureHeader(DisclosurePanel dp, String text, Widget openIcon, Widget closeIcon){
        this(getDefaultResources(), dp, text, openIcon, closeIcon);
    }

    public DisclosureHeader(Resources resources, DisclosurePanel dp, String text){
        this(resources, dp, text, new Image(DisclosureImages.INSTANCE.getCollapseImage()), new Image(DisclosureImages.INSTANCE.getExpandImage()));
    }

    public DisclosureHeader(Resources resources, DisclosurePanel dp, String text, Widget openIcon, Widget closeIcon) {
        super("");
        this.openedIcon = openIcon;
        this.closedIcon = closeIcon;
        buttonContainer.add(closedIcon);

        setIconVisibility(iconAlwaysVisible);

        // Inject the styles used by this widget.
        Style style = resources.disclosureHeaderStyle();
        style.ensureInjected();
        setStyleName(style.disclosureHeaderContainer());
        //noinspection GWTStyleCheck
        addStyleName("clearfix");

        Image loadingImage = new Image(DisclosureImages.INSTANCE.getIcon());
        loadingImage.setWidth("10px");
        loadingImage.setHeight("10px");
        loadingImage.setStyleName(style.disclosureHeaderIcon());
        add(loadingImage);

        InlineLabel title = new InlineLabel(text);
        title.setStyleName(style.disclosureHeaderTitle());
        add(title);

        primaryMessage.setStyleName(style.disclosureHeaderPrimaryMessage());
        add(primaryMessage);

        //
        buttonContainer.setStyleName(style.disclosureHeaderButton());
        add(buttonContainer);

        numberMessage.setStyleName(style.disclosureHeaderSecondaryMessage());
        add(numberMessage);

        dp.addOpenHandler(this);
        dp.addCloseHandler(this);
        addDomHandler(this, MouseOverEvent.getType());
        addDomHandler(this, MouseOutEvent.getType());
    }

    @Override
    public void onOpen(OpenEvent<DisclosurePanel> event) {
        buttonContainer.clear();
        buttonContainer.add(openedIcon);
    }

    @Override
    public void onClose(CloseEvent<DisclosurePanel> event) {
        buttonContainer.clear();
        buttonContainer.add(closedIcon);
    }

    @Override
    public void onMouseOver(MouseOverEvent event) {
        setIconVisibility(true);
    }

    @Override
    public void onMouseOut(MouseOutEvent event) {
        setIconVisibility(false);
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setIconAlwaysVisible(boolean alwaysVisible) {
        this.iconAlwaysVisible = alwaysVisible;
    }

    private void setIconVisibility(boolean visibility){
        if(!iconAlwaysVisible && !visibility)
            buttonContainer.getElement().getStyle().setVisibility(com.google.gwt.dom.client.Style.Visibility.HIDDEN);
        else
            buttonContainer.getElement().getStyle().setVisibility(com.google.gwt.dom.client.Style.Visibility.VISIBLE);
    }

    public void setPrimaryMessage(String text){
        primaryMessage.setText(text);
    }

    public void setSecondaryMessage(String text){
        numberMessage.setText(text);
    }
}
