package uk.ac.ebi.pride.widgets.client.disclosure.images;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface DisclosureImages extends ClientBundle {

    public DisclosureImages INSTANCE = GWT.create(DisclosureImages.class);

    @ClientBundle.Source("disclosure_minus.png")
    ImageResource getCollapseImage();

    @ClientBundle.Source("disclosure_plus.png")
    ImageResource getExpandImage();

    @ClientBundle.Source("disclosure_loading.gif")
    ImageResource getLoadingImage();

    @ClientBundle.Source("icon.png")
    ImageResource getIcon();
}
