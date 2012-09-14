package uk.ac.ebi.pride.widgets.biojs.sequence.client;


import uk.ac.ebi.pride.widgets.biojs.sequence.handlers.SequenceAnnotationClickedEventHandler;
import uk.ac.ebi.pride.widgets.biojs.sequence.handlers.SequenceSelectionChangeEventHandler;
import uk.ac.ebi.pride.widgets.biojs.sequence.handlers.SequenceSelectionChangedEventHandler;
import uk.ac.ebi.pride.widgets.biojs.sequence.options.SequenceAnnotation;
import uk.ac.ebi.pride.widgets.biojs.sequence.options.SequenceRegion;

@SuppressWarnings("UnusedDeclaration")
public interface BioJsSequenceInterface {

    void addAnnotation(SequenceAnnotation annotation);

    void addAnnotationClickedEventHandler(SequenceAnnotationClickedEventHandler handler);

    void addSelectionChangeEventHandler(SequenceSelectionChangeEventHandler handler);

    void addSelectionChangedEventHandler(SequenceSelectionChangedEventHandler handler);

    int addHighlight(SequenceRegion region);

    void formatSelectorVisible(boolean visible);

    void hide();

    void hideFormatSelector();

    void removeAllAnnotations();

    void removeAllHighlights();

    void removeAnnotation(String name);

    //TODO: Doesn't work :(
    void removeHighlight(int id);

    void setFormat(String format);

    void setColumns(int numCols, int spaceEach);

    void setSelection(int start, int end);

    void setSequence(String sequence);

    void setSequence(String sequence, String identifier);

    void show();

}
