package uk.ac.ebi.pride.widgets.client.common.interfaces;

import com.google.gwt.canvas.dom.client.Context2d;

public interface Drawable {

    void setMousePosition(int mouseX, int mouseY);

    void draw(Context2d ctx);

}
