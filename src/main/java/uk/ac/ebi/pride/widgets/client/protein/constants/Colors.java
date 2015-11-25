package uk.ac.ebi.pride.widgets.client.protein.constants;

import com.google.gwt.canvas.dom.client.CssColor;
import uk.ac.ebi.pride.widgets.client.protein.style.Resources;

/**
 * @author ntoro
 * @since 18/09/15 12:34
 */
public class Colors {
    // We load the colors in a String from a css using the ClientBundle.
    // In this way is easier to modified the colors and shared them in other GWT apps.

    public static final String UNIQUE_TO_PROTEIN_COLOR = Resources.INSTANCE.color().UNIQUE_TO_PROTEIN();
    public static final String UNIQUE_TO_GENE_COLOR = Resources.INSTANCE.color().UNIQUE_TO_GENE();
    public static final String NON_UNIQUE_PEPTIDE_COLOR = Resources.INSTANCE.color().NON_UNIQUE_PEPTIDE();

    //Darker colors. They can be used for gradients (e. g. PRIDE proteomes webapp) those need to match the peptide colors.
    public static final String UNIQUE_TO_PROTEIN_DARKER_COLOR = Resources.INSTANCE.color().UNIQUE_TO_PROTEIN_DARKER();
    public static final String UNIQUE_TO_GENE_DARKER_COLOR  = Resources.INSTANCE.color().UNIQUE_TO_GENE_DARKER();
    public static final String NON_UNIQUE_PEPTIDE_DARKER_COLOR  = Resources.INSTANCE.color().NON_UNIQUE_PEPTIDE_DARKER();

    //Css colors used in the canvas
    public static final CssColor UNIQUE_TO_PROTEIN_CSS_COLOR = CssColor.make(UNIQUE_TO_PROTEIN_COLOR);
    public static final CssColor UNIQUE_TO_GENE_CSS_COLOR = CssColor.make(UNIQUE_TO_GENE_COLOR);
    public static final CssColor NON_UNIQUE_PEPTIDE_CSS_COLOR = CssColor.make(NON_UNIQUE_PEPTIDE_COLOR);

    public static final CssColor UNIQUE_TO_PROTEIN_CSS_DARKER_COLOR = CssColor.make(UNIQUE_TO_PROTEIN_DARKER_COLOR);
    public static final CssColor UNIQUE_TO_GENE_CSS_DARKER_COLOR = CssColor.make(UNIQUE_TO_GENE_DARKER_COLOR);
    public static final CssColor NON_UNIQUE_PEPTIDE_DARKER_CSS_COLOR = CssColor.make(NON_UNIQUE_PEPTIDE_DARKER_COLOR);

    public static final CssColor PEPTIDE_HIGHLIGTED_COLOR = CssColor.make("rgb(255,255,0)");
    public static final CssColor PEPTIDE_SELECTED_COLOR = CssColor.make("rgb(255,255,0)");

    public static final CssColor MODIFICATION_COLOR = CssColor.make("rgba(255,0,0, 1)");
    public static final CssColor MODIFICATION_SELECTED_COLOR = CssColor.make("rgba(255,255,0, 0.75)");
    public static final CssColor MODIFICATION_HIGHLIGHTED_COLOR = CssColor.make("rgba(0,255,0, 1)");

    public static final CssColor PROTEIN_AREA_NON_SELECTED_COLOR = CssColor.make("rgba(255,255,255, 0.75)");
    public static final CssColor PROTEIN_AREA_SELECTED_COLOR = CssColor.make("rgb(255,255,0)");

    public static final CssColor PROTEIN_REGION_BACKGROUND_COLOR = CssColor.make("rgba(255,255,255, 1)");
    public static final CssColor PROTEIN_LINE_COLOR = CssColor.make("rgba(89,89,89, 1)");

    public static final CssColor PROTEIN_BORDER_COLOR = CssColor.make("rgba(0,0,0, 1)");
    public static final CssColor PROTEIN_BORDER_SELECTED_COLOR = CssColor.make("rgba(0,0,255, 1)");

    public static final CssColor SEQUENCE_BORDER_COLOR = CssColor.make("rgba(0,0,0, 1)");
    public static final CssColor SEQUENCE_BORDER_SELECTED_COLOR = CssColor.make("rgba(0,0,255, 1)");

    public static final CssColor PROTEIN_CANVAS_BORDER_BG_COLOR = CssColor.make("rgba(255,255,255, 1)");
    public static final CssColor PROTEIN_CANVAS_NO_BORDER_BG_COLOR = CssColor.make("rgba(248,248,248, 1)");

    public Colors() {
        Resources.INSTANCE.color().ensureInjected();
    }
}
