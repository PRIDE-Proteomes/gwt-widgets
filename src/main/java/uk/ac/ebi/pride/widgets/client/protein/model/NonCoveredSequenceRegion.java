package uk.ac.ebi.pride.widgets.client.protein.model;

import com.google.gwt.canvas.dom.client.Context2d;
import uk.ac.ebi.pride.widgets.client.protein.utils.CanvasProperties;

import java.util.Set;

public class NonCoveredSequenceRegion extends SequenceRegion {

    public NonCoveredSequenceRegion(int start, CanvasProperties canvasProperties) {
        super(start, canvasProperties);
    }

    @Override
    public boolean isMouseOver() {
        return false;
    }

    @Override
    public void draw(Context2d context) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
