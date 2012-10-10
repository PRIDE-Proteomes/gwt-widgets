package uk.ac.ebi.pride.widgets.client.protein.utils;

import uk.ac.ebi.pride.widgets.client.common.handler.PeptideHandler;
import uk.ac.ebi.pride.widgets.client.common.handler.ProteinHandler;
import uk.ac.ebi.pride.widgets.client.protein.model.PeptideBase;
import uk.ac.ebi.pride.widgets.client.protein.model.ProteinAxis;

import java.util.LinkedList;
import java.util.List;

public abstract class PeptideBaseFactory {
    public static final int PEPTIDES_Y = ProteinAxis.Y_OFFSET + ProteinAxis.BOXES_HEIGHT;
    public static final int PEPTIDE_VERTICAL_OFFSET = 5;

    public static List<PeptideBase> getPeptideBaseList(ProteinAxis pa, ProteinHandler proteinHandler){
        List<PeptideBase> list = new LinkedList<PeptideBase>();

        PeptideLevelCollection plc = new PeptideLevelCollection(proteinHandler);
        int y = PEPTIDES_Y;
        for (PeptideLevel peptideLevel : plc.getPeptideLevels()) {
            y += PeptideBase.PEPTIDE_HEIGHT + PEPTIDE_VERTICAL_OFFSET;
            for (PeptideHandler peptideHandler : peptideLevel.getPeptideHandlers()) {
                PeptideBase pepAux = new PeptideBase(pa, peptideHandler, y);
                list.add(pepAux);
            }
        }

        return list;
    }
}
