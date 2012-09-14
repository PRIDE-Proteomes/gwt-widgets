package uk.ac.ebi.pride.widgets.client.biojs.sequence.client;


import uk.ac.ebi.pride.widgets.client.biojs.common.BioJsPanel;
import uk.ac.ebi.pride.widgets.client.biojs.sequence.handlers.SequenceAnnotationClickedEventHandler;
import uk.ac.ebi.pride.widgets.client.biojs.sequence.handlers.SequenceSelectionChangeEventHandler;
import uk.ac.ebi.pride.widgets.client.biojs.sequence.handlers.SequenceSelectionChangedEventHandler;
import uk.ac.ebi.pride.widgets.client.biojs.sequence.options.*;

import java.util.ArrayList;

/**
 *
 */
public class BioJsSequence extends BioJsPanel implements BioJsSequenceInterface {
    protected boolean hideFormatSelector = false;
    private BioJsSequenceWrapper jsWrapper;
    protected boolean jsWrapperVisible = true;
    private SequenceAnnotationClickedEventHandler sequenceAnnotationClickedEventHandler;
    private SequenceSelectionChangeEventHandler sequenceSelectionChangeEventHandler;
    private SequenceSelectionChangedEventHandler sequenceSelectionChangedEventHandler;

    public BioJsSequence(SequenceParameters parameters) {
        super(parameters);
    }

    public BioJsSequence(String sequence){
        this(new SequenceParameters(sequence));
    }


    @Override
    protected void initializeBioJsObject() {
        if(jsWrapper != null) return;

        jsWrapper = BioJsSequenceWrapper.create(parameters);
        jsWrapper._initialize();

        if(sequenceAnnotationClickedEventHandler != null)
            jsWrapper.addAnnotationClickedEventHandler(sequenceAnnotationClickedEventHandler);

        if(sequenceSelectionChangeEventHandler != null)
            jsWrapper.addSelectionChangeEventHandler(sequenceSelectionChangeEventHandler);

        if(sequenceSelectionChangedEventHandler != null)
            jsWrapper.addSelectionChangedEventHandler(sequenceSelectionChangedEventHandler);

        if(hideFormatSelector)
            jsWrapper.hideFormatSelector();

        if(!jsWrapperVisible)
            jsWrapper.hide();
    }

    @Override
    public void addAnnotation(SequenceAnnotation annotation) {
        if(jsWrapper!=null)
            jsWrapper.addAnnotation(annotation);
        else
            parameters.addAnnotation(annotation);
    }

    @Override
    public void addAnnotationClickedEventHandler(SequenceAnnotationClickedEventHandler handler) {
        if(jsWrapper != null)
            jsWrapper.addAnnotationClickedEventHandler(handler);
        else
            sequenceAnnotationClickedEventHandler = handler;
    }

    @Override
    public void addSelectionChangeEventHandler(SequenceSelectionChangeEventHandler handler) {
        if(jsWrapper != null)
            jsWrapper.addSelectionChangeEventHandler(handler);
        else
            sequenceSelectionChangeEventHandler = handler;
    }

    @Override
    public void addSelectionChangedEventHandler(SequenceSelectionChangedEventHandler handler) {
        if(jsWrapper != null)
            jsWrapper.addSelectionChangedEventHandler(handler);
        else
            sequenceSelectionChangedEventHandler = handler;
    }

    @Override
    public int addHighlight(SequenceRegion region) {
        if(jsWrapper != null)
            return jsWrapper.addHighlight(region);
        else{
            parameters.addHighlights(region);
            return -1;
        }
    }

    @Override
    public void formatSelectorVisible(boolean visible) {
        hideFormatSelector = !visible;
        if(jsWrapper != null)
            jsWrapper.formatSelectorVisible(visible);

    }

    @Override
    public void hide() {
        jsWrapperVisible = false;
        if(jsWrapper != null)
            jsWrapper.hide();
    }

    @Override
    public void hideFormatSelector() {
        hideFormatSelector = true;
        if(jsWrapper!=null)
            jsWrapper.hideFormatSelector();
    }

    @Override
    public void removeAllAnnotations() {
        if(jsWrapper != null)
            jsWrapper.removeAllAnnotations();
        else
            parameters.setAnnotations(new ArrayList<SequenceAnnotation>());

    }

    @Override
    public void removeAllHighlights() {
        if(jsWrapper != null)
            jsWrapper.removeAllHighlights();
        else
            parameters.setHighlights(new SequenceHighlights());

    }

    @Override
    public void removeAnnotation(String name) {
        if(jsWrapper != null)
            jsWrapper.removeAnnotation(name);
        else
            parameters.removeAnnotation(name);
    }

    @Override
    public void removeHighlight(int id) {
        if(jsWrapper != null)
            jsWrapper.removeHighlight(id);
    }

    @Override
    public void setFormat(String format) {
        if(jsWrapper != null)
            jsWrapper.setFormat(format);
        else
            parameters.setFormat(format);
    }

    @Override
    public void setColumns(int numCols, int spaceEach) {
        if(jsWrapper != null)
            jsWrapper.setColumns(numCols, spaceEach);
        else
            parameters.setColumns(numCols, spaceEach);
    }

    @Override
    public void setSelection(int start, int end) {
        if(jsWrapper != null)
            jsWrapper.setSelection(start,end);
        else
            parameters.setSelection(new SequenceSelection(start, end));
    }

    @Override
    public void setSequence(String sequence) {
        if(jsWrapper != null)
            jsWrapper.setSequence(sequence);
    }

    @Override
    public void setSequence(String sequence, String identifier) {
        if(jsWrapper != null)
            jsWrapper.setSequence(sequence, identifier);
    }

    @Override
    public void show() {
        jsWrapperVisible = true;
        if(jsWrapper != null)
            jsWrapper.show();
    }
}
