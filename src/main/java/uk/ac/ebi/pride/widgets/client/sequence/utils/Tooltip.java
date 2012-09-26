package uk.ac.ebi.pride.widgets.client.sequence.utils;

import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class Tooltip extends PopupPanel {
    private static Tooltip tooltip;

    private HTMLPanel container;
    private Timer timer;

    private Tooltip(){
        super(true);

        this.container = new HTMLPanel("");
        Style style = this.container.getElement().getStyle();
        style.setBackgroundColor("black");
        style.setPadding(2, Style.Unit.PX);
        style.setFontSize(10, Style.Unit.PX);
        style.setFontWeight(Style.FontWeight.BOLD);
        style.setColor("white");

        this.timer = new Timer() {
            @Override
            public void run() {
                hide();
            }
        };

        add(this.container);
    }

    public static Tooltip getTooltip(){
        if(tooltip==null){
            tooltip = new Tooltip();
        }
        return tooltip;
    }

    public void show(CanvasElement sender, int offsetX, int offsetY, final String text) {
        show(sender, offsetX, offsetY, new HTML(text));

        timer.cancel();
        timer.schedule(1000);
    }

    public void show(CanvasElement sender, int offsetX, int offsetY, final Widget widget) {
        super.show();

        this.container.clear();
        this.container.add(widget);

        int left = sender.getAbsoluteLeft() + offsetX + 5;
        int top = sender.getAbsoluteTop() + offsetY + 5;
        setPopupPosition(left, top);
    }
}