package uk.ac.ebi.pride.widgets.client.protein.utils;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class Tooltip extends PopupPanel {
    public static final CssColor BACKGROUND_COLOR = CssColor.make("rgba(145, 145, 145, 0.95)");
    private HTMLPanel container;

    public Tooltip(){
        super(true);

        this.container = new HTMLPanel("");
        Style style = this.container.getElement().getStyle();
        style.setBackgroundColor(BACKGROUND_COLOR.value());
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
        super.show();

        this.container.clear();
        this.container.add(widget);

        int left = sender.getAbsoluteLeft() + offsetX;
        int top = sender.getAbsoluteTop() + offsetY;
        setPopupPosition(left, top);
    }
}