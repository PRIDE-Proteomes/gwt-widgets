package uk.ac.ebi.pride.widgets.client.disclosure.client;

import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.*;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Redrawable;
import uk.ac.ebi.pride.widgets.client.disclosure.images.DisclosureImages;

@SuppressWarnings("UnusedDeclaration")
public class ModuleContainer extends Composite implements OpenHandler<DisclosurePanel> {
    DisclosureHeader dh;
    DisclosurePanel dp;

    private ModuleContainer(String text, Widget iconOpen, Widget iconClose) {
        dp = new DisclosurePanel();
        //noinspection GWTStyleCheck
        dp.addStyleName("pq-module-body");
        dp.setAnimationEnabled(true);

        if(iconOpen==null || iconClose==null){
            dh = new DisclosureHeader(dp, text);
        }else{
            dh = new DisclosureHeader(dp, text, iconOpen, iconClose);
        }
        dp.setHeader(dh);

        HorizontalPanel hp = new HorizontalPanel();
        hp.add(new Image(DisclosureImages.INSTANCE.getLoadingImage()));
        hp.add(new HTMLPanel("Loading..."));
        hp.setSpacing(5);

        dp.setContent(hp);
        dp.addOpenHandler(this);

        initWidget(dp);
    }

    public static ModuleContainer getAdvancedDisclosurePanel(String text){
        return new ModuleContainer(text, null, null);
    }

    public static ModuleContainer getAdvancedDisclosurePanel(String text, Widget iconOpen, Widget iconClose){
        return new ModuleContainer(text, iconOpen, iconClose);
    }

    public static DisclosurePanel getDisclosurePanel(String text){
        DisclosurePanel dp = new DisclosurePanel(text);
        dp.setAnimationEnabled(true);

        HorizontalPanel hp = new HorizontalPanel();
        hp.add(new Image(DisclosureImages.INSTANCE.getLoadingImage()));
        hp.add(new HTMLPanel("Loading..."));
        hp.setSpacing(5);

        dp.setContent(hp);

        return dp;
    }

    public HandlerRegistration addCloseHandler(CloseHandler<DisclosurePanel> handler) {
        return dp.addCloseHandler(handler);
    }

    public HandlerRegistration addOpenHandler(OpenHandler<DisclosurePanel> handler) {
        return dp.addOpenHandler(handler);
    }

    public void setMessage(String text){
        dh.setMessage(text);
    }

    public void clearMessage(){
        dh.setMessage("");
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
