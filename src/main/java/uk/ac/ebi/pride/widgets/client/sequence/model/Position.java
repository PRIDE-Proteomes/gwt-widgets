package uk.ac.ebi.pride.widgets.client.sequence.model;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import uk.ac.ebi.pride.widgets.client.common.handler.PeptideHandler;
import uk.ac.ebi.pride.widgets.client.common.handler.PrideModificationHandler;
import uk.ac.ebi.pride.widgets.client.common.interfaces.Clickable;
import uk.ac.ebi.pride.widgets.client.sequence.utils.CanvasProperties;
import uk.ac.ebi.pride.widgets.client.sequence.utils.CanvasSelection;
import uk.ac.ebi.pride.widgets.client.sequence.utils.Tooltip;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class Position implements DrawableLayers, Clickable {
    public static final String AMINO_ACID_FONT = "normal 11px sans-serif";
    public static final CssColor AMINO_ACID_COLOR = CssColor.make("rgba(0,0,0, 1)");
    public static final CssColor AMINO_ACID_SELECTED_COLOR = CssColor.make("rgba(255,0,0, 1)");

    public static final String AMINO_ACID_MODIFIED_FONT = "bold 11px sans-serif";
    public static final CssColor AMINO_ACID_MODIFIED_COLOR = CssColor.make("rgba(204,51,0, 1)"); //#CC3300
    public static final CssColor AMINO_ACID_MODIFIED_SELECTED_COLOR = CssColor.make("rgba(255,0,255, 1)");
    public static final CssColor AMINO_ACID_MODIFIED_HIGHLIGHTED_COLOR = CssColor.make("rgba(0,255,0, 1)");

    public static final CssColor NON_UNIQUE_PEPTIDE_COLOR = CssColor.make("rgba(46,228,255, .5)");
    public static final CssColor UNIQUE_PEPTIDE_COLOR = CssColor.make("rgba(0,0,175, .5)");

    public static final CssColor HIGHLIGHT_COLOR = CssColor.make("rgba(255,255,0, 0.5)");

    CanvasSelection canvasSelection;

    private String aminoAcid;
    private int position;
    private boolean isInPeptide;
    private boolean isPeptideVisible;
    private CssColor peptideColor;
    private boolean isNonUniquePeptide;
    private boolean isModified;
    private List<PrideModificationHandler> prideModifications;

    private String tooltip;

    // mouse positions relative to canvas
    int mouseX, mouseY;

    private int x, y, xText, yText;
    private int yC;
    private int xMax, yMin;

    public Position(ProteinSummary proteinSummary, CanvasSelection canvasSelection, int position) {
        this.canvasSelection = canvasSelection;
        this.aminoAcid = proteinSummary.getSequence().substring(position, position + 1);
        this.position = position + 1;

        this.isNonUniquePeptide = proteinSummary.getNonUniquePeptidesPositions().contains(this.position);
        boolean uniquePeptide = proteinSummary.getUniquePeptidesPositions().contains(this.position);
        this.isInPeptide = isNonUniquePeptide || uniquePeptide;
        this.isPeptideVisible = true;
        this.peptideColor = isNonUniquePeptide ? NON_UNIQUE_PEPTIDE_COLOR : UNIQUE_PEPTIDE_COLOR;

        this.isModified = proteinSummary.getModificationPositions().contains(this.position);
        this.prideModifications = proteinSummary.getPrideModifications(this.position);

        /* MODIFICATION ADJUSTMENTS */
        if(this.position == 1){
            //Modifications in position 0 are assigned to the position 1
            this.isModified = this.isModified || proteinSummary.getModificationPositions().contains(0);
            this.prideModifications.addAll(proteinSummary.getPrideModifications(0));

        }else if(this.position == proteinSummary.getLength()){
            //Modifications in position length+1 are assigned to position length
            int aux = proteinSummary.getLength()+1;
            this.isModified = this.isModified || proteinSummary.getModificationPositions().contains(aux);
            this.prideModifications.addAll(proteinSummary.getPrideModifications(aux));
        }
        /* MODIFICATION ADJUSTMENTS */

        this.tooltip = String.valueOf(this.position);
        if(isModified){
            this.tooltip += "<br/>" + getModificationTooltip(this.prideModifications);
        }

        CanvasProperties canvasProperties = CanvasProperties.getCanvasProperties();
        this.x = canvasProperties.getNextPosX();
        this.y = canvasProperties.getNextPosY();
        this.xMax = this.x + CanvasProperties.POSITION_WIDTH;
        this.yMin = y - CanvasProperties.POSITION_HEIGHT;
        this.xText = this.x + CanvasProperties.POSITION_WIDTH / 2; // x + 1;
        this.yText = y - 2;
        //this.xC = this.xText + CanvasProperties.POSITION_WIDTH / 2;
        this.yC = this.y - CanvasProperties.POSITION_HEIGHT / 2;

        canvasProperties.reserveHorizontalSpace(CanvasProperties.POSITION_WIDTH);
    }

    public void setVisiblePeptide(PeptideHandler peptide){
        this.isPeptideVisible = (this.position>=peptide.getSite() && this.position<=peptide.getEnd());
        this.peptideColor = peptide.getUniqueness()==1 ? UNIQUE_PEPTIDE_COLOR : NON_UNIQUE_PEPTIDE_COLOR;
    }

    public void resetPeptidesFilter(){
        this.isPeptideVisible = true;
        this.peptideColor = isNonUniquePeptide ? NON_UNIQUE_PEPTIDE_COLOR : UNIQUE_PEPTIDE_COLOR;
    }

    public boolean isMouseOver(){
        return (x<=mouseX & xMax>mouseX & yMin<=mouseY & y>=mouseY);
    }

    @Override
    public void setMousePosition(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    @Override
    public void draw(Context2d ctx) {
        ctx.fillText(this.aminoAcid, xText, yText);
    }

    @Override
    public void drawPeptides(Context2d ctx) {
        if(isInPeptide && isPeptideVisible){
            ctx.setFillStyle(this.peptideColor);
            ctx.fillRect(x, yMin, CanvasProperties.POSITION_WIDTH, CanvasProperties.POSITION_HEIGHT);
        }
    }

    @Override
    public void drawSelection(Context2d ctx) {
        if(this.isSelected()){
            ctx.fillRect(x, yMin, CanvasProperties.POSITION_WIDTH, CanvasProperties.POSITION_HEIGHT);
        }
    }

    @Override
    public void drawModification(Context2d ctx, PrideModificationHandler prideModification) {
        if(isModified){
            if(prideModification!=null){
                boolean modificationMatches = false;
                int i=0;
                while(i<prideModifications.size() && !modificationMatches){
                    PrideModificationHandler modification = prideModifications.get(i++);
                    modificationMatches = ( modificationMatches | ( modification.getId() == prideModification.getId() ) );
                }
                if(modificationMatches){
                    ctx.setFillStyle(AMINO_ACID_MODIFIED_HIGHLIGHTED_COLOR);
                    ctx.beginPath();
                    ctx.arc(this.xText, this.yC, CanvasProperties.POSITION_HEIGHT / 2, 0, Math.PI * 2.0, true);
                    ctx.closePath();
                    ctx.fill();
                    ctx.setFillStyle(AMINO_ACID_MODIFIED_COLOR);
                }
            }
            ctx.fillText(this.aminoAcid, xText, yText);


        }
    }

    @Override
    public void drawPosition(Context2d ctx) {
        if(isMouseOver()){
            Tooltip.getTooltip().show(ctx.getCanvas(), xMax, y, tooltip);

            ctx.setFillStyle(HIGHLIGHT_COLOR);
            ctx.fillRect(x, yMin, CanvasProperties.POSITION_WIDTH, CanvasProperties.POSITION_HEIGHT);

            ctx.setFillStyle(isModified? AMINO_ACID_MODIFIED_SELECTED_COLOR : AMINO_ACID_SELECTED_COLOR);
            ctx.setFont( isModified ? AMINO_ACID_MODIFIED_FONT: AMINO_ACID_FONT);
            ctx.setTextAlign(Context2d.TextAlign.CENTER);
            ctx.fillText(this.aminoAcid, xText, yText);

            if(this.canvasSelection.isMouseDown()){
                this.canvasSelection.setRegionEnd(this.position);
            }else{
                this.canvasSelection.setRegionStart(this.position);
                this.canvasSelection.setRegionEnd(this.position);
            }
        }
    }

    @Override
    public void onMouseUp(int mouseX, int mouseY) {
        this.canvasSelection.setMouseDown(false);
        if(isMouseOver()){
            this.canvasSelection.setRegionEnd(this.position);
        }
    }

    @Override
    public void onMouseDown(int mouseX, int mouseY) {
        this.canvasSelection.setMouseDown(true);
        if(isMouseOver()){
            this.canvasSelection.setRegionStart(this.position);
            this.canvasSelection.setRegionEnd(this.position);
        }
    }

    @Override
    public boolean isSelected() {
        int regionStart = this.canvasSelection.getRegionStart();
        int regionEnd = this.canvasSelection.getRegionEnd();
        if(regionEnd<regionStart){
            int aux = regionStart;
            regionStart = regionEnd;
            regionEnd = aux;
        }
        return (this.position >= regionStart & this.position <= regionEnd);
    }

    private String getModificationTooltip(List<PrideModificationHandler> prideModifications){
        @SuppressWarnings("Convert2Diamond")
        Map<String, Integer> count = new HashMap<String, Integer>();
        for (PrideModificationHandler prideModification : prideModifications) {
            int n = count.containsKey(prideModification.getName()) ? count.get(prideModification.getName()) : 0;
            count.put(prideModification.getName(), n + 1);
        }
        StringBuilder sb = new StringBuilder("Modifications:");
        for (String modificationName : count.keySet()) {
            int c = count.get(modificationName);
            sb.append("<br/>&nbsp;&nbsp;&nbsp;&nbsp;");
            sb.append(modificationName);
            sb.append(":&nbsp;");
            sb.append(c);
            sb.append("&nbsp;observation");
            if(c>1) sb.append("s");
        }
        return sb.toString();
    }
}
