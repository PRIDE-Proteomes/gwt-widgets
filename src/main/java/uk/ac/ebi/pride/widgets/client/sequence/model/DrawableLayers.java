package uk.ac.ebi.pride.widgets.client.sequence.model;

import com.google.gwt.canvas.dom.client.Context2d;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Drawable;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public interface DrawableLayers extends Drawable {

    void drawPeptides(Context2d ctx);

    void drawSelection(Context2d ctx);

    void drawModifications(Context2d ctx);

    void drawPosition(Context2d ctx);
}
