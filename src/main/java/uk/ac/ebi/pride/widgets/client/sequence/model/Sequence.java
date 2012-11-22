package uk.ac.ebi.pride.widgets.client.sequence.model;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.shared.HandlerManager;
import uk.ac.ebi.pride.widgets.client.common.handler.PeptideHandler;
import uk.ac.ebi.pride.widgets.client.common.handler.PrideModificationHandler;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Clickable;
import uk.ac.ebi.pride.widgets.client.sequence.events.ProteinPositionHighlightedEvent;
import uk.ac.ebi.pride.widgets.client.sequence.events.ProteinRegionHighlightedEvent;
import uk.ac.ebi.pride.widgets.client.sequence.events.ProteinRegionResetEvent;
import uk.ac.ebi.pride.widgets.client.sequence.events.ProteinRegionSelectionEvent;
import uk.ac.ebi.pride.widgets.client.sequence.type.SequenceType;
import uk.ac.ebi.pride.widgets.client.sequence.utils.CanvasProperties;
import uk.ac.ebi.pride.widgets.client.sequence.utils.CanvasSelection;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class Sequence implements DrawableLayers, Clickable {
    private HandlerManager handlerManager;

    private List<SequenceLine> sequenceLineList;

    //canvasSelection will be shared between all the Position class instances to keep track of the selection process
    private CanvasSelection canvasSelection;
    //canvasHighlight contains the region that is currently highlighted
    private CanvasSelection canvasHighlight;

    //lastRegionSelected keeps track of what was the last regionSelectedEvent throws
    private CanvasSelection lastRegionSelected;

    public Sequence(SequenceType sequenceType, ProteinSummary proteinSummary, HandlerManager handlerManager) {
        this.handlerManager = handlerManager;
        //noinspection Convert2Diamond
        this.sequenceLineList = new LinkedList<SequenceLine>();

        this.canvasSelection = new CanvasSelection();
        this.canvasHighlight = new CanvasSelection();
        this.lastRegionSelected = new CanvasSelection();

        int lineStart = 0;
        int lineLength = sequenceType.getBlockSize() * sequenceType.getNumOfBlocks();

        double lineSize = sequenceType.getBlockSize() * sequenceType.getNumOfBlocks();
        int numberOfLines = (int) Math.ceil(proteinSummary.getLength() / lineSize);
        for(int i=1; i<=numberOfLines; ++i){
            int aux = lineStart + lineLength;
            int end = (aux < proteinSummary.getLength() ? aux : proteinSummary.getLength());
            this.sequenceLineList.add(new SequenceLine(sequenceType, this.canvasSelection, lineStart, end, proteinSummary));
            CanvasProperties.getCanvasProperties().setNewRow();
            lineStart = end;
        }
    }

    public void resetSelection(){
        if(this.canvasSelection.containsSelection()){
            this.lastRegionSelected.resetSelection();
            this.canvasSelection.resetSelection();
            //System.out.println("Selection reset!");
            handlerManager.fireEvent(new ProteinRegionResetEvent());
        }
    }

    public void selectRegion(int start, int end){
        CanvasSelection aux = new CanvasSelection(start, end);
        if(!this.canvasSelection.equals(aux)){
            this.lastRegionSelected.resetSelection();
            this.canvasSelection.setRegionStart(start);
            this.canvasSelection.setRegionEnd(end);
        }
    }

    public void setVisiblePeptide(PeptideHandler peptide){
        for (SequenceLine sequenceLine : sequenceLineList) {
            sequenceLine.setVisiblePeptide(peptide);
        }
    }

    public void resetPeptidesFilter(){
        for (SequenceLine sequenceLine : sequenceLineList) {
            sequenceLine.resetPeptidesFilter();
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
        ctx.setFillStyle(Position.AMINO_ACID_COLOR);
        ctx.setFont(Position.AMINO_ACID_FONT);
        //TextAlign depends on the part of the sequence line (on level deep)
        //PositionIdentification -> ALIGN.LEFT
        //PositionBlock -> ALIGN.CENTER
        for (SequenceLine sequenceLine : sequenceLineList) {
            sequenceLine.draw(ctx);
        }
    }

    @Override
    public void drawPeptides(Context2d ctx) {
        for (SequenceLine sequenceLine : sequenceLineList) {
            sequenceLine.drawPeptides(ctx);
        }
    }

    @Override
    public void drawSelection(Context2d ctx) {
        ctx.setFillStyle(Position.HIGHLIGHT_COLOR);
        for (SequenceLine sequenceLine : sequenceLineList) {
            sequenceLine.drawSelection(ctx);
            for (PositionIdentification positionIdentification : sequenceLine.getPositionIdentificationList()) {
                positionIdentification.drawSelection(ctx);
            }
        }
    }

    @Override
    public void drawModification(Context2d ctx, PrideModificationHandler prideModification) {
        ctx.setFillStyle(Position.AMINO_ACID_MODIFIED_COLOR);
        ctx.setFont(Position.AMINO_ACID_MODIFIED_FONT);
        ctx.setTextAlign(Context2d.TextAlign.CENTER);
        for (SequenceLine sequenceLine : sequenceLineList) {
            sequenceLine.drawModification(ctx, prideModification);
        }
    }

    @Override
    public void drawPosition(Context2d ctx) {
        for (SequenceLine sequenceLine : sequenceLineList) {
            sequenceLine.drawPosition(ctx);
        }
        checkHighlighting();
    }

    @Override
    public void onMouseUp(int mouseX, int mouseY) {
        for (SequenceLine sequenceLine : sequenceLineList) {
            sequenceLine.onMouseUp(mouseX, mouseY);
        }
        checkSelection();
    }

    @Override
    public void onMouseDown(int mouseX, int mouseY) {
        if(!this.canvasSelection.equals(this.lastRegionSelected))
            resetSelection();

        for (SequenceLine sequenceLine : sequenceLineList) {
            sequenceLine.onMouseDown(mouseX, mouseY);
        }
    }

    @Override
    public boolean isMouseOver() {
        return false;
    }

    @Override
    public boolean isSelected() {
        boolean isSelected = false;
        for (SequenceLine sequenceLine : sequenceLineList) {
            isSelected = isSelected || sequenceLine.isSelected();
        }
        return isSelected;
    }

    private void checkHighlighting(){
        if(this.canvasSelection.containsSelection() && !this.canvasSelection.equals(this.canvasHighlight)){
            //This is like cloning
            this.canvasHighlight = new CanvasSelection(this.canvasSelection);
            if(this.canvasSelection.isMouseDown()){
                //System.out.println("Region Highlighted: " + this.canvasSelection );
                handlerManager.fireEvent(new ProteinRegionHighlightedEvent(this.canvasSelection));
            }else{
                //System.out.println("Position Highlighted: " + this.canvasSelection );
                handlerManager.fireEvent(new ProteinPositionHighlightedEvent(this.canvasSelection.getRegionStart()));
            }
        }
    }

    private void checkSelection(){
        if(!this.lastRegionSelected.equals(this.canvasHighlight)){
            //NEXT TWO LINES! DO NOT USE THE CONSTRUCTOR! canvasSelection is shared with all the Drawable objects!!
            this.canvasSelection.setRegionStart(this.canvasHighlight.getRegionStart());
            this.canvasSelection.setRegionEnd(this.canvasHighlight.getRegionEnd());

            //System.out.println("Region Selected: " + this.canvasSelection.toString());
            handlerManager.fireEvent(new ProteinRegionSelectionEvent(this.canvasSelection));

            //HERE we need to create a new object every time
            this.lastRegionSelected = new CanvasSelection(this.canvasSelection);
        }
    }
}
