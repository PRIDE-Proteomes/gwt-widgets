package uk.ac.ebi.pride.widgets.client.protein.events;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.pride.widgets.client.common.handler.ProteinModificationHandler;
import uk.ac.ebi.pride.widgets.client.protein.handlers.ModificationHighlightedHandler;

import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("UnusedDeclaration")
public class ModificationHighlightedEvent extends GwtEvent<ModificationHighlightedHandler> {
    @SuppressWarnings("Convert2Diamond")
    public static Type<ModificationHighlightedHandler> TYPE = new Type<ModificationHighlightedHandler>();

    private Integer site;
    private List<ProteinModificationHandler> modifications;

    public ModificationHighlightedEvent(Integer site, List<ProteinModificationHandler> modification) {
        this.site = site;
        this.modifications = modification;
    }

    public Integer getSite() {
        return site;
    }

    public List<ProteinModificationHandler> getModificationList() {
        return modifications;
    }

    @Override
    public Type<ModificationHighlightedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ModificationHighlightedHandler handler) {
        handler.onModificationHighlighted(this);
    }

    @Override
    public String toString() {
        return "ModificationHighlightedEvent{" +
                "site=" + site +
                ", modifications=" + modifications.size() +
                '}';
    }
}
