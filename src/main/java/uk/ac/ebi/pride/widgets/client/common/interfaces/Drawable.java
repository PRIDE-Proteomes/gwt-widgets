package uk.ac.ebi.pride.widgets.client.common.interfaces;

import com.google.gwt.canvas.dom.client.Context2d;

public interface Drawable {

    public void setMousePosition(int mouseX, int mouseY);

    public void draw(Context2d ctx);

}
