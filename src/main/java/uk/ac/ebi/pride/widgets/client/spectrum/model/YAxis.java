package uk.ac.ebi.pride.widgets.client.spectrum.model;

import com.google.gwt.canvas.dom.client.Context2d;

import java.math.BigDecimal;

public class YAxis extends Axis {
    private final static int TICK_WIDTH = 5;

    private final int height;
    private double delta;

    // mouse positions relative to canvas
    int mouseX, mouseY;

    public YAxis(int x, int y, int height, Rank rank) {
        super(x,y, rank);
        this.height = height;

        update();
    }

    @Override
    public double getPixelFromValue(double value){
        return delta * (value - rank.getFrom()) + y;
    }

    @Override
    public double getValueFromPixel(int pixel) {
        double value = rank.getFrom() + ( (pixel- y) / delta );
        return (value < 0.0) ? 0.0 : value;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void update() {
        this.delta = -height / (rank.getTo() - rank.getFrom());
    }

    @Override
    public void draw(Context2d ctx){
        ctx.beginPath();
        ctx.setStrokeStyle(AXIS_COLOR);
        ctx.setLineWidth(AXIS_WIDTH);
        ctx.moveTo(x, y);
        ctx.lineTo(x, y - height);
        ctx.closePath();
        ctx.stroke();

        drawTicks(ctx);
    }

    @Override
    public void setMousePosition(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    private void drawTicks(Context2d ctx){
        double textX = x + TICK_WIDTH - 15 ;
        ctx.setTextAlign(Context2d.TextAlign.RIGHT);
        ctx.setStrokeStyle(AXIS_COLOR);
        ctx.setLineWidth(AXIS_TICK_WIDTH);
        for (Double value : rank) {
            double tickY = getPixelFromValue(value);
            ctx.beginPath();
            ctx.moveTo(x, tickY);
            ctx.lineTo(x - TICK_WIDTH, tickY);
            ctx.closePath();
            ctx.stroke();

            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(2, BigDecimal.ROUND_UP);
            String text = bd.toString();
            ctx.fillText(text, textX, tickY);
        }
    }
}
