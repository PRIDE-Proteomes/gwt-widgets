package uk.ac.ebi.pride.widgets.client.disclosure.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.ui.*;
import uk.ac.ebi.pride.widgets.client.disclosure.images.DisclosureImages;

public class DisclosureHeader extends Composite implements OpenHandler<DisclosurePanel>,
        CloseHandler<DisclosurePanel>, MouseOverHandler, MouseOutHandler {

    private boolean iconAlwaysVisible = true;

    private Panel iconContainer = new HTMLPanel("");

    private final Image openedIcon = new Image(DisclosureImages.INSTANCE.getCollapseImage());
    private final Image closedIcon = new Image(DisclosureImages.INSTANCE.getExpandImage());

    private Label message = new Label();

    public DisclosureHeader(DisclosurePanel dp, String text) {
        iconContainer.add(closedIcon);
        setIconVisibility(iconAlwaysVisible);
        final FlexTable flexTable = new FlexTable();
        FlexTable.FlexCellFormatter cellFormatter = flexTable.getFlexCellFormatter();
        flexTable.setWidth("100%");
        flexTable.setCellSpacing(5);
        flexTable.setCellPadding(0);

        HorizontalPanel hp = new HorizontalPanel();
        hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        hp.setSpacing(5);

        Label title = new Label(text);
        //noinspection GWTStyleCheck
        title.addStyleName("pq-module-header-title");
        hp.add(title);
        //noinspection GWTStyleCheck
        message.addStyleName("pq-module-header-message");
        hp.add(message);

        flexTable.setWidget(0, 0, hp);
        cellFormatter.setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);
        cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);

        flexTable.setWidget(0, 1, iconContainer);
        cellFormatter.setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_MIDDLE);
        cellFormatter.setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER);
        cellFormatter.setWidth(0,1, closedIcon.getWidth() + "px");

        dp.addOpenHandler(this);
        dp.addCloseHandler(this);
        addDomHandler(this, MouseOverEvent.getType());
        addDomHandler(this, MouseOutEvent.getType());

        initWidget(flexTable);
        //noinspection GWTStyleCheck
        addStyleName("pq-module-header");
    }

    @Override
    public void onOpen(OpenEvent<DisclosurePanel> event) {
        iconContainer.clear();
        iconContainer.add(openedIcon);
    }

    @Override
    public void onClose(CloseEvent<DisclosurePanel> event) {
        iconContainer.clear();
        iconContainer.add(closedIcon);
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
            iconContainer.getElement().getStyle().setVisibility(Style.Visibility.HIDDEN);
        else
            iconContainer.getElement().getStyle().setVisibility(Style.Visibility.VISIBLE);
    }

    public void setMessage(String text){
        message.setText(text);
    }
}
