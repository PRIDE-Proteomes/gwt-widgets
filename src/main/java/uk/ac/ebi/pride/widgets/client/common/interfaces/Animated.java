package uk.ac.ebi.pride.widgets.client.common.interfaces;

import com.google.gwt.canvas.dom.client.Context2d;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public interface Animated {

    public void drawAnimation(Context2d ctx, double progress);
}
