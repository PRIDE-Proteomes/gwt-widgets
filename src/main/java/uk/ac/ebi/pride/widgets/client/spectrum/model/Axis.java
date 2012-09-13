package uk.ac.ebi.pride.widgets.client.spectrum.model;

import uk.ac.ebi.pride.widgets.client.common.Drawable;

public abstract class Axis implements Drawable {
    final static String AXIS_COLOR = "black";
    final static int AXIS_WIDTH = 2;
    final static int AXIS_TICK_WIDTH = 1;

    final int x;
    final int y;
    Rank rank;

    protected Axis(int x, int y, Rank rank) {
        this.x = x;
        this.y = y;
        this.rank = rank;
    }

    public abstract double getPixelFromValue(double value);

    public abstract double getValueFromPixel(int pixel);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
        update();
    }

    public abstract void update();
}
