package uk.ac.ebi.pride.widgets.client.sequence.model;

import com.google.gwt.canvas.dom.client.Context2d;
import uk.ac.ebi.pride.widgets.client.common.handler.PrideModificationHandler;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Drawable;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public interface DrawableLayers extends Drawable {

    void drawPeptides(Context2d ctx);

    void drawSelection(Context2d ctx);

    void drawModification(Context2d ctx, PrideModificationHandler prideModification);

    void drawModification(Context2d ctx, int modPosition);

    void drawPosition(Context2d ctx);
}
