package uk.ac.ebi.pride.widgets.client.protein.model;

import com.google.gwt.canvas.dom.client.Context2d;

import java.util.Set;

public class NonCoveredSequenceRegion extends SequenceRegion {

    public NonCoveredSequenceRegion(int start, ProteinAxis pa) {
        super(start, pa);
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
