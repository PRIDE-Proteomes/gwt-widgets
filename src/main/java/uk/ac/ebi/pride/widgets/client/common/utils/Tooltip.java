package uk.ac.ebi.pride.widgets.client.common.utils;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class Tooltip extends PopupPanel {

    public static final CssColor TOOLTIP_BACKGROUND_COLOR = CssColor.make("#FFFFFF");
    Logger logger = Logger.getLogger(Tooltip.class.getName());

    private HTMLPanel container;

    public Tooltip(){
        super(true);

        //Close the tooltip when the mouse moves out of it, this allows clicking in a hyperlink if it exists
        addDomHandler(new MouseOutHandler() {
            @Override
            public void onMouseOut(MouseOutEvent event) {
                hide();
            }

        }, MouseOutEvent.getType());


        this.container = new HTMLPanel("");
        Style style = this.container.getElement().getStyle();
        style.setBackgroundColor(TOOLTIP_BACKGROUND_COLOR.value());
        style.setPadding(7, Style.Unit.PX);
        style.setProperty("borderRadius", 5, Style.Unit.PX);
        style.setBorderStyle(Style.BorderStyle.SOLID);
        style.setBorderWidth(2, Style.Unit.PX);
        style.setBorderColor("black");
        style.setFontSize(10, Style.Unit.PX);
        style.setFontWeight(Style.FontWeight.BOLD);
        style.setColor("black");
        setAnimationEnabled(true);

        add(this.container);
    }

    public void show(CanvasElement sender, int offsetX, int offsetY, final String text) {
        show(sender, offsetX, offsetY, new HTML(text));
    }

    public void show(CanvasElement sender, int offsetX, int offsetY, final Widget widget) {

        this.container.clear();
        this.container.add(widget);

        int left = sender.getAbsoluteLeft() + offsetX;
        int top = sender.getAbsoluteTop() + offsetY;
        setPopupPosition(left, top);
        int t_left = getAbsoluteLeft();
        int t_right = (getAbsoluteLeft() + getOffsetWidth());
        int t_top = getAbsoluteTop();
        int t_bottom = (getAbsoluteTop() + getOffsetHeight());
        logger.log(Level.INFO, "Tooltip (" +
                t_left + "," +
                t_right + "," +
                t_top + "," +
                t_bottom + ")");

        super.show();

    }
}