package uk.ac.ebi.pride.widgets.client.protein.utils;

import com.google.gwt.canvas.dom.client.CssColor;
import uk.ac.ebi.pride.widgets.client.common.handler.PeptideHandler;
import uk.ac.ebi.pride.widgets.client.protein.model.CoveredSequenceRegion;
import uk.ac.ebi.pride.widgets.client.protein.model.ModificationBase;
import uk.ac.ebi.pride.widgets.client.protein.model.PeptideBase;

import java.util.LinkedList;
import java.util.List;

import static uk.ac.ebi.pride.widgets.client.protein.constants.Colors.*;

public abstract class PeptideBaseFactory {
    public static final int PEPTIDES_Y = CanvasProperties.Y_OFFSET + CoveredSequenceRegion.BOXES_HEIGHT + ModificationBase.MODIFICATION_HEIGHT;
    public static final int PEPTIDE_VERTICAL_OFFSET = 5;

    public static List<PeptideBase> getPeptideBaseList(CanvasProperties canvasProperties) {
        @SuppressWarnings("Convert2Diamond")
        List<PeptideBase> list = new LinkedList<PeptideBase>();

        PeptideLevelCollection plc = new PeptideLevelCollection(canvasProperties);
        int y = PEPTIDES_Y;
        for (PeptideLevel peptideLevel : plc.getPeptideLevels()) {
            y += PeptideBase.PEPTIDE_HEIGHT + PEPTIDE_VERTICAL_OFFSET;
            for (PeptideHandler peptideHandler : peptideLevel.getPeptideHandlers()) {
                if (peptideHandler.getSite() > 0 && peptideHandler.getEnd() <= canvasProperties.getProteinLength()) {
                    //TODO Try with enum
                    CssColor color;
                    switch (peptideHandler.getUniqueness()) {
                        case 1:
                            color = UNIQUE_TO_PROTEIN_CSS_COLOR;
                            break;
                        case 2:
                            color = UNIQUE_TO_UP_ENTRY_CSS_COLOR;
                            break;
                        case 3:
                            color = UNIQUE_TO_GENE_CSS_COLOR;
                            break;
                        default:
                            color = NON_UNIQUE_PEPTIDE_CSS_COLOR;
                    }

                    PeptideBase pepAux = new PeptideBase(canvasProperties, peptideHandler, y, getPeptideTooltip(peptideHandler), color, peptideHandler.getSite(), peptideHandler.getSequence().length());
                    list.add(pepAux);
                } // else: ignore this peptide, as it is outside the protein sequence scope
            }
        }

        return list;
    }

    public static String getPeptideTooltip(PeptideHandler peptide) {
        StringBuilder sb = new StringBuilder();

        sb.append("<span style=\"font-weight:bold\">");
        sb.append(peptide.getSequence());
        sb.append("</span>");
        sb.append("&nbsp[ ");
        sb.append(peptide.getSite());
        sb.append(" - ");
        sb.append(peptide.getEnd());
        sb.append(" ]");
        sb.append("<br/>");

        sb.append("<span style=\"color:");
        switch (peptide.getUniqueness()) {
            case 1:
                sb.append(UNIQUE_TO_PROTEIN_CSS_DARKER_COLOR.value());
                sb.append("\">UNIQUE PEPTIDE TO THIS PROTEIN</span>");
                break;
            case 2:
                sb.append(UNIQUE_TO_UP_ENTRY_CSS_DARKER_COLOR.value());
                sb.append("\">UNIQUE PEPTIDE TO PROTEIN ISOFORM GROUP</span>");
                sb.append("<br/>");
                for (String upEntry : peptide.getSharedUpEntries()) {
                    sb.append("&nbsp;&nbsp;&nbsp;&nbsp;<a href =\"#group=").append(upEntry).append("\">").append(upEntry).append("</a>");
                }
                sb.append("<br/>");
                appendLinks(peptide, sb);
                break;
            case 3:
                sb.append(UNIQUE_TO_GENE_CSS_DARKER_COLOR.value());
                sb.append("\">UNIQUE PEPTIDE TO PRODUCTS OF THE GENE</span>");
                sb.append("<br/>");
                for (String gene : peptide.getSharedGenes()) {
                    sb.append("&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"#group=").append(gene).append("\">").append(gene).append("</a>");
                }
                sb.append("<br/>");
                appendLinks(peptide, sb);
                break;
            default:
                sb.append(NON_UNIQUE_PEPTIDE_DARKER_CSS_COLOR.value());
                sb.append("\">NON UNIQUE PEPTIDE TO THIS PROTEIN</span>");
                sb.append("<br/>");
                appendLinks(peptide, sb);
        }


        return sb.toString();
    }

    public static void appendLinks(PeptideHandler peptide, StringBuilder sb) {

        if (peptide.getSharedProteins() != null && !peptide.getSharedProteins().isEmpty()) {
            if (peptide.getSharedProteins().size() > 1) {
                sb.append("Shared proteins: ");
                sb.append("<br/>");
                for (String sharedProtein : peptide.getSharedProteins()) {
                    sb.append("&nbsp;&nbsp;&nbsp;&nbsp;<a href =\"#protein=").append(sharedProtein).append("\">").append(sharedProtein).append("</a>");
                    sb.append("<br/>");
                }
            }
        }

        if (peptide.getSharedUpEntries() != null && !peptide.getSharedUpEntries().isEmpty()) {
            if (peptide.getSharedUpEntries().size() > 1) {
                sb.append("Shared protein isoform groups: ");
                sb.append("<br/>");
                for (String upEntry : peptide.getSharedUpEntries()) {
                    sb.append("&nbsp;&nbsp;&nbsp;&nbsp;<a href =\"#group=").append(upEntry).append("\">").append(upEntry).append("</a>");
                    sb.append("<br/>");
                }
            }
        }

        if (peptide.getSharedGenes() != null && !peptide.getSharedGenes().isEmpty()) {
            if (peptide.getSharedGenes().size() > 1) {
                sb.append("Shared genes: ");
                sb.append("<br/>");
                for (String gene : peptide.getSharedGenes()) {
                    sb.append("&nbsp;&nbsp;&nbsp;&nbsp;<a href =\"#group=").append(gene).append("\">").append(gene).append("</a>");
                    sb.append("<br/>");
                }
            }
        }
    }
}