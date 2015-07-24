package uk.ac.ebi.pride.widgets.client.protein.utils;

import com.google.gwt.canvas.dom.client.CssColor;
import uk.ac.ebi.pride.widgets.client.common.handler.PeptideHandler;
import uk.ac.ebi.pride.widgets.client.protein.model.CoveredSequenceRegion;
import uk.ac.ebi.pride.widgets.client.protein.model.ModificationBase;
import uk.ac.ebi.pride.widgets.client.protein.model.PeptideBase;

import java.util.LinkedList;
import java.util.List;

import static uk.ac.ebi.pride.widgets.client.common.constants.Colors.*;

public abstract class PeptideBaseFactory {
    public static final int PEPTIDES_Y = CanvasProperties.Y_OFFSET + CoveredSequenceRegion.BOXES_HEIGHT + ModificationBase.MODIFICATION_HEIGHT;
    public static final int PEPTIDE_VERTICAL_OFFSET = 5;

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
                    //TODO Try with enum
                    CssColor color;
                    switch (peptideHandler.getUniqueness()){
                        case 1:
                            color = UNIQUE_TO_PROTEIN_COLOR;
                            break;
                        case 2:
                            color = UNIQUE_TO_UP_ENTRY_COLOR;
                            break;
                        case 3:
                            color = UNIQUE_TO_GENE_COLOR;
                            break;
                        default:
                            color = NON_UNIQUE_PEPTIDE_COLOR;
                    }

                    PeptideBase pepAux = new PeptideBase(canvasProperties, peptideHandler, y, getPeptideTooltip(peptideHandler), color, peptideHandler.getSite(), peptideHandler.getSequence().length());
                    list.add(pepAux);
                } // else: ignore this peptide, as it is outside the protein sequence scope
            }
        }

        return list;
    }

    public static String getPeptideTooltip(PeptideHandler peptide){
        StringBuilder sb = new StringBuilder("<span style=\"font-weight:bold;color:");
        switch (peptide.getUniqueness()){
            case 1:
                sb.append(UNIQUE_TO_PROTEIN_COLOR.value());
                sb.append("\">PEPTIDE UNIQUE TO THIS PROTEIN</span>");
                break;
            case 2:
                sb.append(UNIQUE_TO_UP_ENTRY_COLOR.value());
                sb.append("\">PEPTIDE UNIQUE TO THE UNIPROT ENTRY</span>");
                break;
            case 3:
                sb.append(UNIQUE_TO_GENE_COLOR.value());
                sb.append("\">PEPTIDE UNIQUE TO THE GENE</span>");
                break;
            default:
                sb.append(NON_UNIQUE_PEPTIDE_COLOR.value());
                sb.append("\">PEPTIDE NOT UNIQUE TO THIS PROTEIN</span>");
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
