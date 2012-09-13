package uk.ac.ebi.pride.widgets.client.spectrum.model;

import java.util.Iterator;

public class Rank implements Iterable<Double>, Iterator<Double> {
    private static int DEFAULT_ITEMS = 10;

    private double from;
    private double to;
    private double step;
    private double current;
    private int items;

    public Rank(double from, double to, int items) {
        this.from = from;
        this.to = to;
        setItems(items);
    }

    public Rank(double from, double to) {
        this(from, to, DEFAULT_ITEMS);
    }

    public double getFrom() {
        return from;
    }

    public double getTo() {
        return to;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
        step = Math.abs(to - from) / (items - 1);
        current = from - step;
    }

    @Override
    public Iterator<Double> iterator() {
        current = from - step;
        return this;
    }

    @Override
    public boolean hasNext() {
        return current < to;
    }

    @Override
    public Double next() {
        current += step;
        return current;
    }

    @Override
    public void remove() {
        //Nothing here
    }

    @Override
    public String toString() {
        return "Rank{" +
                "from=" + from +
                ", step=" + step +
                ", to=" + to +
                '}';
    }
}