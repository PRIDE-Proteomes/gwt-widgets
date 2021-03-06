package uk.ac.ebi.pride.widgets.client.common.interfaces;

public interface Clickable {

    public void onMouseUp(int mouseX, int mouseY);

    public void onMouseDown(int mouseX, int mouseY);

    public boolean isMouseOver();

    public boolean isSelected();
}
