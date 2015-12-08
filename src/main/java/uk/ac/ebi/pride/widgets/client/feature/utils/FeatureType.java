package uk.ac.ebi.pride.widgets.client.feature.utils;

import com.google.gwt.canvas.dom.client.CssColor;

/**
 * @author ntoro
 * @since 28/07/15 13:27
 */
public enum FeatureType {

    //TODO Move the color to a css an load them using clientBundle

    INIT_MET("initiator methionine", "Initiator methionine", "rgba(204,121,67,0.7)"),
    SIGNAL("signal peptide", "Signal peptide", "rgba(4,90,141,0.7)"), //Dark blue
    PROPEP("propeptide", "Propeptide", "rgba(255,60,255,0.7)"),
    TRANSIT("transit peptide", "Transit peptide", "rgba(213,94,0,0.7)"),
    CHAIN("chain", "Chain", "rgba(215,48,39,0.7)"),
    PEPTIDE("peptide", "Peptide", "rgba(244,109,67,0.7)"),
    TOPO_DOM("topological domain", "Topological domain", "rgba(116,169,207,0.7)"), //Medium-dark blue
    TRANSMEM("transmembrane region", "Transmembrane region", "rgba(54,144,192,0.7)"), //Medium blue
    DOMAIN("domain", "Other domain of interest", "rgba(253,174,97,0.7)"),
    REPEAT("repeat", "Repeat", "rgba(254,224,144,0.7)"),
    CA_BIND("calcium-binding region", "Calcium-binding region", "rgba(255,255,191,0.7)"),
    ZN_FING("zinc finger region", "Zinc finger region", "rgba(224,243,248,0.7)"),
    DNA_BIND("DNA-binding region", "DNA-binding region", "rgba(171,217,233,0.7)"),
    NP_BIND("nucleotide phosphate-binding region", "Nucleotide-binding region", "rgba(116,173,209,0.7)"),
    REGION("region of interest", "Region of interest", "rgba(69,117,180,0.7)"),
    COILED("coiled-coil region", "Coiled-coil region", "rgba(215,48,39,0.7)"),
    MOTIF("short sequence motif", "Short sequence motif", "rgba(244,109,67,0.7)"),
    COMPBIAS("compositionally biased region", "Compositionally biased region", "rgba(253,174,97,0.7)"),
    ACT_SITE("active site", "Active site", "rgba(254,224,144,0.7)"),
    METAL("metal ion-binding site", "Metal ion-binding site", "rgba(255,255,191,0.7)"),
    BINDING("binding site", "Other binding site", "rgba(224,243,248,0.7)"),
    SITE("site", "Other site of interest", "rgba(151,200,255,0.7)"),
    NON_STD("non-standard amino acid", "Non-standard amino acid", "rgba(116,173,209,0.7)"),
    MOD_RES("modified residue", "Post-translationally modified residue", "rgba(69,117,180,0.7)"),
    LIPID("lipid moiety-binding region", "Lipid moiety-binding region", "rgba(215,48,39,0.7)"),
    CARBOHYD("glycosylation site", "Glycosylation site", "rgba(244,109,67,0.7)"),
    DISULFID("disulfide bond", "Disulfide bond", "rgba(253,174,97,0.7)"),
    CROSSLNK("cross-link", "Cross-link", "rgba(254,224,144,0.7)"),
    VAR_SEQ("splice variant", "Splice variant", "rgba(255,255,191,0.7)"),
    VARIANT("sequence variant", "Sequence variation", "rgba(224,243,248,0.7)"),
    MUTAGEN("mutagenesis site", "Mutagenesis site", "rgba(171,217,233,0.7)"),
    UNSURE("unsure residue", "Uncertainty in sequence", "rgba(116,173,209,0.7)"),
    CONFLICT("sequence conflict", "Sequence conflict", "rgba(69,117,180,0.7)"),
    NON_CONS("non-consecutive residues", "Non-consecutive residues", "rgba(215,48,39,0.7)"),
    NON_TER("non-terminal residue", "Non-terminal residue", "rgba(244,109,67,0.7)"),
    HELIX("helix", "Helix", "rgba(253,174,97,0.7)"),
    TURN("turn", "Turn", "rgba(254,224,144,0.7)"),
    STRAND("strand", "Strand", "rgba(255,255,191,0.7)"),
    INTRAMEM("intramembrane region", "Intramembrane region", "rgba(208,209,230,0.7)"); //Light purple

    private String description;
    private String displayName;
    private String color;


    FeatureType(String description, String displayName, String color) {
        this.description = description;
        this.displayName = displayName;
        this.color = color;
    }

    FeatureType(String description, String color) {
        this(description, description, color);
    }

    public String getDescription() {
        return this.description;
    }

    public String getName() {
        return this.name();
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getColor() {
        return this.color;
    }

    public CssColor getCssColor() {
        return CssColor.make(this.color);
    }

    public static FeatureType typeOf(String name) {
        for (FeatureType featureType : values()) {
            if (featureType.getName().equalsIgnoreCase(name)) {
                return featureType;
            }
        }

        throw new IllegalArgumentException("The feature with name " + name + " doesn\'t exist.");
    }
}
