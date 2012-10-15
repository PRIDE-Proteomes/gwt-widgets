package uk.ac.ebi.pride.widgets.client.protein.utils;

import uk.ac.ebi.pride.widgets.client.common.handler.PeptideHandler;
import uk.ac.ebi.pride.widgets.client.protein.model.CoveredSequenceRegion;
import uk.ac.ebi.pride.widgets.client.protein.model.ModificationBase;
import uk.ac.ebi.pride.widgets.client.protein.model.PeptideBase;

import java.util.LinkedList;
import java.util.List;

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
                PeptideBase pepAux = new PeptideBase(canvasProperties, peptideHandler, y);
                list.add(pepAux);
            }
        }

        return list;
    }
}
