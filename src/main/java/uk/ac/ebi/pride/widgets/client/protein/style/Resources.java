package uk.ac.ebi.pride.widgets.client.protein.style;

/**
 * @author ntoro
 * @since 18/09/15 11:46
 */

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;


public interface Resources extends ClientBundle {

    Resources INSTANCE =  GWT.create(Resources.class);


    interface PeptideColors extends CssResource {

        String UNIQUE_TO_PROTEIN();
        String UNIQUE_TO_GENE ();
        String NON_UNIQUE_PEPTIDE();

        String UNIQUE_TO_PROTEIN_DARKER();
        String UNIQUE_TO_GENE_DARKER();
        String NON_UNIQUE_PEPTIDE_DARKER();
    }

    @Source("PeptideColours.gss")
    PeptideColors color();
}
