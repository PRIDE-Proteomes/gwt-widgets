package uk.ac.ebi.pride.widgets.client.protein.events;

import com.google.gwt.event.shared.GwtEvent;
import uk.ac.ebi.pride.widgets.client.common.handler.ProteinModificationHandler;
import uk.ac.ebi.pride.widgets.client.protein.handlers.ModificationSelectedHandler;

import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("UnusedDeclaration")
public class ModificationSelectedEvent extends GwtEvent<ModificationSelectedHandler> {
    @SuppressWarnings("Convert2Diamond")
    public static Type<ModificationSelectedHandler> TYPE = new Type<ModificationSelectedHandler>();

    private Integer site;
    private List<ProteinModificationHandler> modifications;

    public ModificationSelectedEvent(Integer site, List<ProteinModificationHandler> modification) {
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
    public Type<ModificationSelectedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ModificationSelectedHandler handler) {
        handler.onModificationSelected(this);
    }

    @Override
    public String toString() {
        return "ModificationSelectedEvent{" +
                "site=" + site +
                ", modifications=" + modifications.size() +
                '}';
    }
}
