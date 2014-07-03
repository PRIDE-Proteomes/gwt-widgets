package uk.ac.ebi.pride.widgets.client.protein.utils;

import com.google.gwt.canvas.dom.client.CssColor;
import uk.ac.ebi.pride.widgets.client.common.handler.PeptideHandler;
import uk.ac.ebi.pride.widgets.client.protein.model.CoveredSequenceRegion;
import uk.ac.ebi.pride.widgets.client.protein.model.ModificationBase;
import uk.ac.ebi.pride.widgets.client.protein.model.PeptideBase;

import java.util.LinkedList;
import java.util.List;

public abstract class PeptideBaseFactory {
    public static final int PEPTIDES_Y = CanvasProperties.Y_OFFSET + CoveredSequenceRegion.BOXES_HEIGHT + ModificationBase.MODIFICATION_HEIGHT;
    public static final int PEPTIDE_VERTICAL_OFFSET = 5;

    public static final CssColor NON_UNIQUE_PEPTIDE_COLOR = CssColor.make("rgba(255,0,0, 1)"); // red
    public static final CssColor UNIQUE_PEPTIDE_COLOR = CssColor.make("rgba(0,255,0, 1)"); // green


    public static List<PeptideBase> getPeptideBaseList(CanvasProperties canvasProperties){
        @SuppressWarnings("Convert2Diamond")
        List<PeptideBase> list = new LinkedList<PeptideBase>();

        PeptideLevelCollection plc = new PeptideLevelCollection(canvasProperties);
        int y = PEPTIDES_Y;
        for (PeptideLevel peptideLevel : plc.getPeptideLevels()) {
            y += PeptideBase.PEPTIDE_HEIGHT + PEPTIDE_VERTICAL_OFFSET;
            for (PeptideHandler peptideHandler : peptideLevel.getPeptideHandlers()) {
                if (peptideHandler.getSite() > 0 && peptideHandler.getEnd() <= canvasProperties.getProteinLength()) {
//                    PeptideBase pepAux = new PeptideBase(canvasProperties, peptideHandler, y);
                    CssColor color;
                    if (peptideHandler.getUniqueness() == 1) {
                        color = UNIQUE_PEPTIDE_COLOR;
                    } else {
                        color = NON_UNIQUE_PEPTIDE_COLOR;
                    }
                    PeptideBase pepAux = new PeptideBase(canvasProperties, getPeptideTooltip(peptideHandler), y, color, peptideHandler.getSite(), peptideHandler.getSequence().length());
                    list.add(pepAux);
                } // else: ignore this peptide, as it is outside the protein sequence scope
            }
        }

        return list;
    }

    private static String getPeptideTooltip(PeptideHandler peptide){
        StringBuilder sb = new StringBuilder("<span style=\"font-weight:bold;color:");
        if(peptide.getUniqueness()!=1){
            sb.append(NON_UNIQUE_PEPTIDE_COLOR.value());
            sb.append("\">NON MATCHING PEPTIDE</span>");
        }else{
            sb.append(UNIQUE_PEPTIDE_COLOR.value());
            sb.append("\">MATCHING PEPTIDE</span>");
        }
        sb.append("<br/>");
        sb.append("&nbsp;&nbsp;&nbsp;&nbsp;Sequence: ");
        sb.append(peptide.getSequence());
        sb.append("<br/>");
        sb.append("&nbsp;&nbsp;&nbsp;&nbsp;Start: ");
        sb.append(peptide.getSite());
        sb.append(";&nbsp;&nbsp;&nbsp;End: ");
        sb.append(peptide.getEnd());
        return sb.toString();
    }

}
