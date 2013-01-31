package uk.ac.ebi.pride.widgets.client.disclosure.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.*;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Redrawable;

@SuppressWarnings("UnusedDeclaration")
public class ModuleContainer extends Composite implements OpenHandler<DisclosurePanel> {

    /**
     * A ClientBundle of resources used by this widget.
     */
    public interface Resources extends ClientBundle {
        /**
         * The styles used in this widget.
         */
        @Source(Style.DEFAULT_CSS)
        Style moduleContainerStyle();
    }

    /**
     * Styles used by this widget.
     */
    @CssResource.ImportedWithPrefix("pride-ModuleContainer")
    public interface Style extends CssResource {
        /**
         * The path to the default CSS styles used by this resource.
         */
        String DEFAULT_CSS = "uk/ac/ebi/pride/widgets/client/disclosure/css/ModuleContainer.css";

        /**
         * Applied to the widget.
         */
        String moduleContainer();
    }

    private static Resources DEFAULT_RESOURCES;

    DisclosureHeader dh;
    DisclosurePanel dp;


    /**
     * Get the default {@link Resources} for this widget.
     */
    private static Resources getDefaultResources() {
        if (DEFAULT_RESOURCES == null) {
            DEFAULT_RESOURCES = GWT.create(Resources.class);
        }
        return DEFAULT_RESOURCES;
    }

    public ModuleContainer(String text, Widget iconOpen, Widget iconClose) {
        this(getDefaultResources(), text, iconOpen, iconClose);
    }

    private ModuleContainer(Resources resources, String text, Widget iconOpen, Widget iconClose) {
        dp = new DisclosurePanel();

        // Inject the styles used by this widget.
        Style style = resources.moduleContainerStyle();
        style.ensureInjected();
        dp.setStyleName(style.moduleContainer());
        dp.setAnimationEnabled(true);

        if(iconOpen==null || iconClose==null){
            dh = new DisclosureHeader(dp, text);
        }else{
            dh = new DisclosureHeader(dp, text, iconOpen, iconClose);
        }
        dp.setHeader(dh);

        dp.setContent(ModuleContainer.getLoadingPanel());
        dp.addOpenHandler(this);

        initWidget(dp);
    }

    public static ModuleContainer getAdvancedDisclosurePanel(String text){
        return new ModuleContainer(text, null, null);
    }

    public static ModuleContainer getAdvancedDisclosurePanel(String text, Widget iconOpen, Widget iconClose){
        return new ModuleContainer(text, iconOpen, iconClose);
    }

    public static Widget getLoadingPanel(){
        return new LoadingPanel();
    }

    public static DisclosurePanel getDisclosurePanel(String text){
        DisclosurePanel dp = new DisclosurePanel(text);
        dp.setAnimationEnabled(true);

        dp.setContent(ModuleContainer.getLoadingPanel());

        return dp;
    }

    public HandlerRegistration addCloseHandler(CloseHandler<DisclosurePanel> handler) {
        return dp.addCloseHandler(handler);
    }

    public HandlerRegistration addOpenHandler(OpenHandler<DisclosurePanel> handler) {
        return dp.addOpenHandler(handler);
    }

    public void setPrimaryMessage(String text){
        dh.setPrimaryMessage(text);
    }

    public void clearPrimaryMessage(){
        dh.setPrimaryMessage("");
    }

    public void setSecondaryMessage(String text){
        dh.setSecondaryMessage(text);
    }

    public void clearSecondaryMessage(){
        dh.setSecondaryMessage("");
    }

    public Widget getContent(){
        return dp.getContent();
    }

    public void setContent(Widget content){
        dp.setContent(content);
    }

    public void setOpen(boolean isOpen){
        dp.setOpen(isOpen);
    }

    @Override
    public void onOpen(OpenEvent<DisclosurePanel> event) {
        Widget content = this.getContent();
        if(content!= null){
            if(content instanceof Redrawable){
                ((Redrawable) content).redraw();
            }
        }
    }
}
