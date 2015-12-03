package uk.ac.ebi.pride.widgets.client.common.interfaces;

public interface Clickable {

    void onMouseUp(int mouseX, int mouseY);

    void onMouseDown(int mouseX, int mouseY);

    boolean isMouseOver();

    boolean isSelected();
}
