package uk.ac.ebi.pride.widgets.client.sequence.model;

import com.google.gwt.canvas.dom.client.Context2d;
import uk.ac.ebi.pride.widgets.client.common.Clickable;
import uk.ac.ebi.pride.widgets.client.common.Drawable;
import uk.ac.ebi.pride.widgets.client.sequence.data.Protein;
import uk.ac.ebi.pride.widgets.client.sequence.type.SequenceType;
import uk.ac.ebi.pride.widgets.client.sequence.utils.CanvasProperties;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class Sequence implements Drawable, DrawablePosition, DrawableSelection, Clickable {
    private static final int LINES_VISIBLE = 15;

    private SequenceType sequenceType;
    private Protein protein;
    private List<SequenceLine> sequenceLineList;

    public Sequence(SequenceType sequenceType, Protein protein) {
        this.sequenceType = sequenceType;
        this.protein = protein;
        initialize();
    }

    private void initialize(){
        this.sequenceLineList = new LinkedList<SequenceLine>();
        int lineStart = 0;
        int lineLength = sequenceType.getBlockSize() * sequenceType.getNumOfBlocks();

        double lineSize = sequenceType.getBlockSize() * sequenceType.getNumOfBlocks();
        int numberOfLines = (int) Math.ceil(this.protein.getLength() / lineSize);
        for(int i=1; i<=numberOfLines; ++i){
            int aux = lineStart + lineLength;
            int end = (aux < protein.getLength() ? aux : protein.getLength());
            this.sequenceLineList.add(new SequenceLine(sequenceType, lineStart, end, this.protein));
            CanvasProperties.getCanvasProperties().setNewRow();
            lineStart = end;
        }
    }

    @Override
    public void setMousePosition(int mouseX, int mouseY) {
        for (SequenceLine sequenceLine : sequenceLineList) {
            sequenceLine.setMousePosition(mouseX, mouseY);
        }
    }

    @Override
    public void draw(Context2d ctx) {
        for (SequenceLine sequenceLine : sequenceLineList) {
            sequenceLine.draw(ctx);
        }
    }

    @Override
    public void drawSelection(Context2d ctx) {
        for (SequenceLine sequenceLine : sequenceLineList) {
            sequenceLine.drawSelection(ctx);
        }
    }

    @Override
    public void drawPosition(Context2d ctx) {
        for (SequenceLine sequenceLine : sequenceLineList) {
            sequenceLine.drawPosition(ctx);
        }
    }

    @Override
    public void onMouseUp(int mouseX, int mouseY) {
        for (SequenceLine sequenceLine : sequenceLineList) {
            sequenceLine.onMouseUp(mouseX, mouseY);
        }
    }

    @Override
    public void onMouseDown(int mouseX, int mouseY) {
        for (SequenceLine sequenceLine : sequenceLineList) {
            sequenceLine.onMouseDown(mouseX, mouseY);
        }
    }
}
