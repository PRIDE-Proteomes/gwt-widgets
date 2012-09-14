package uk.ac.ebi.pride.widgets.biojs.sequence.client;

import com.google.gwt.core.client.JavaScriptObject;
import uk.ac.ebi.pride.widgets.biojs.common.BioJsObject;
import uk.ac.ebi.pride.widgets.biojs.sequence.events.SequenceAnnotationClickedEvent;
import uk.ac.ebi.pride.widgets.biojs.sequence.events.SequenceSelectionEvent;
import uk.ac.ebi.pride.widgets.biojs.sequence.handlers.SequenceAnnotationClickedEventHandler;
import uk.ac.ebi.pride.widgets.biojs.sequence.handlers.SequenceSelectionChangeEventHandler;
import uk.ac.ebi.pride.widgets.biojs.sequence.handlers.SequenceSelectionChangedEventHandler;
import uk.ac.ebi.pride.widgets.biojs.sequence.options.SequenceAnnotation;
import uk.ac.ebi.pride.widgets.biojs.sequence.options.SequenceParameters;
import uk.ac.ebi.pride.widgets.biojs.sequence.options.SequenceRegion;

public class BioJsSequenceWrapper extends JavaScriptObject implements BioJsSequenceInterface, BioJsObject {

    protected BioJsSequenceWrapper(){}

    public static native BioJsSequenceWrapper create(SequenceParameters parameters) /*-{
        var toJSON = $wnd.jQuery.parseJSON;
        var params = toJSON(parameters.@uk.ac.ebi.pride.widgets.biojs.sequence.options.SequenceParameters::getJSON()());
        return new $wnd.Biojs.Sequence(params);
    }-*/;

    @Override
    public final native int addHighlight(SequenceRegion region)/*-{
        var toJSON = $wnd.jQuery.parseJSON;
        var r = toJSON(region.@uk.ac.ebi.pride.widgets.biojs.sequence.options.SequenceRegion::getJSON()());
        return this.addHighlight(r);
    }-*/;

    @Override
    public final native void formatSelectorVisible(boolean visible)/*-{
        this.formatSelectorVisible(visible);
    }-*/;

    @Override
    public final native void hide()/*-{
        this.hide();
    }-*/;

    @Override
    public final native void hideFormatSelector()/*-{
        this.hideFormatSelector();
    }-*/;

    @Override
    public final native void _initialize() /*-{
        var _this = this;

        //Selection Change Event
        var _onSelectionChange = function(e){
            var start = e.start; var end = e.end;
            @uk.ac.ebi.pride.widgets.biojs.sequence.client.BioJsSequenceWrapper::_onSelectionChange(Luk/ac/ebi/pride/widgets/biojs/sequence/client/BioJsSequenceWrapper;II)(_this, start, end);
        };
        this.onSelectionChange(_onSelectionChange);

        //Selection Changed Event
        var _onSelectionChanged = function(e){
            var start = e.start; var end = e.end;
            @uk.ac.ebi.pride.widgets.biojs.sequence.client.BioJsSequenceWrapper::_onSelectionChanged(Luk/ac/ebi/pride/widgets/biojs/sequence/client/BioJsSequenceWrapper;II)(_this, start, end);

        };
        this.onSelectionChanged(_onSelectionChanged);

        //Annotation Clicked Event
        var _onAnnotationClicked = function(e){
            var name = e.name; var pos = e.pos;
            @uk.ac.ebi.pride.widgets.biojs.sequence.client.BioJsSequenceWrapper::_onAnnotationClicked(Luk/ac/ebi/pride/widgets/biojs/sequence/client/BioJsSequenceWrapper;Ljava/lang/String;I)(_this, name, pos);
        };
        this.onAnnotationClicked(_onAnnotationClicked);
    }-*/;

    @Override
    public final native void removeAllAnnotations()/*-{
        this.removeAllAnnotations();
    }-*/;

    @Override
    public final native void removeAllHighlights()/*-{
        this.removeAllHighlights();
    }-*/;

    @Override
    public final native void removeAnnotation(String name)/*-{
        this.removeAnnotation(name);
    }-*/;

    @Override
    public final native void removeHighlight(int id)/*-{
        this.removeHighlight(id);
    }-*/;

    @Override
    public final native void setFormat(String format)/*-{
        this.setFormat(format);
    }-*/;

    @Override
    public final native void setColumns(int numCols, int spaceEach)/*-{
        this.setNumCols(numCols);
        //this.setSpaceEach(spaceEach);
    }-*/;

    @Override
    public final native void setSelection(int start, int end)/*-{
        this.setSelection(start, end);
    }-*/;

    @Override
    public final native void addAnnotation(SequenceAnnotation annotation)/*-{
        var toJSON = $wnd.jQuery.parseJSON;
        var a = toJSON(annotation.@uk.ac.ebi.pride.widgets.biojs.sequence.options.SequenceAnnotation::getJSON()());
        this.addAnnotation(a);
    }-*/;

    //Annotation Clicked Event
    @Override
    public final native void addAnnotationClickedEventHandler(SequenceAnnotationClickedEventHandler handler)/*-{
        this.sequenceAnnotationClickedEventHandler = handler;
    }-*/;

    protected final native SequenceAnnotationClickedEventHandler getSequenceAnnotationClickedEventHandler()/*-{
        return this.sequenceAnnotationClickedEventHandler;
    }-*/;

    @SuppressWarnings("UnusedDeclaration")
    public static void _onAnnotationClicked(BioJsSequenceWrapper seq, String name, int pos){
        seq.fireAnnotationClickedEvent(new SequenceAnnotationClickedEvent(name, pos));
    }

    protected final void fireAnnotationClickedEvent(SequenceAnnotationClickedEvent event) {
        SequenceAnnotationClickedEventHandler handler = this.getSequenceAnnotationClickedEventHandler();
        if(handler!=null){
            handler.onAnnotationClicked(event);
        }
    }

    //Selection Change Event
    @Override
    public final native void addSelectionChangeEventHandler(SequenceSelectionChangeEventHandler handler) /*-{
        this.sequenceSelectionChangeEventHandler = handler;
    }-*/;

    protected final native SequenceSelectionChangeEventHandler getSequenceSelectionChangeEventHandler()/*-{
        return this.sequenceSelectionChangeEventHandler;
    }-*/;

    @SuppressWarnings("UnusedDeclaration")
    public static void _onSelectionChange(BioJsSequenceWrapper seq, int start, int end){
        seq.fireSelectionChangeEvent(new SequenceSelectionEvent(start, end));
    }

    protected final void fireSelectionChangeEvent(SequenceSelectionEvent event) {
        SequenceSelectionChangeEventHandler handler = this.getSequenceSelectionChangeEventHandler();
        if(handler!=null){
            handler.onSelectionChange(event);
        }
    }

    //Selection Changed Event
    @Override
    public final native void addSelectionChangedEventHandler(SequenceSelectionChangedEventHandler handler) /*-{
        this.sequenceSelectionChangedEventHandler = handler;
    }-*/;

    protected final native SequenceSelectionChangedEventHandler getSequenceSelectionChangedEventHandler()/*-{
        return this.sequenceSelectionChangedEventHandler;
    }-*/;

    @SuppressWarnings("UnusedDeclaration")
    public static void _onSelectionChanged(BioJsSequenceWrapper seq, int start, int end){
        seq.fireSelectionChangedEvent(new SequenceSelectionEvent(start, end));
    }

    protected final void fireSelectionChangedEvent(SequenceSelectionEvent event) {
        SequenceSelectionChangedEventHandler handler = this.getSequenceSelectionChangedEventHandler();
        if(handler!=null){
            handler.onSelectionChanged(event);
        }
    }

    @Override
    public final native void setSequence(String sequence)/*-{
        this.setSequence(sequence);
    }-*/;

    @Override
    public final native void setSequence(String sequence, String identifier)/*-{
        this.setSequence(sequence, identifier);
    }-*/;

    @Override
    public final native void show()/*-{
        this.show();
    }-*/;
}