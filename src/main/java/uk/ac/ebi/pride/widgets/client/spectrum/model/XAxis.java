package uk.ac.ebi.pride.widgets.client.spectrum.model;

import com.google.gwt.canvas.dom.client.Context2d;

import java.math.BigDecimal;

public class XAxis extends Axis {
    private final static int LEFT_OFFSET = 15;
    private final static int TICK_HEIGHT = 5;
    public static final int BIN_WIDTH = 2;

    private final int width;
    private double delta;

    private int bins;
    private double binMZWidth;

    // mouse positions relative to canvas
    int mouseX, mouseY;

    public XAxis(int x, int y, int width, Rank rank) {
        super(x,y, rank);
        this.width = width;
        bins = (int) Math.ceil(width / (float) BIN_WIDTH);
        update();
    }

    @Override
    public double getPixelFromValue(double value){
        return delta * (value - rank.getFrom()) + x + LEFT_OFFSET;
    }

    @Override
    public double getValueFromPixel(int pixel) {
        return rank.getFrom() + ( (pixel- x - LEFT_OFFSET) / delta );
    }

    @Override
    public void update() {
        delta = width / (rank.getTo() - rank.getFrom());
        binMZWidth = ( rank.getTo() - rank.getFrom() ) / bins;
    }

    public double getBinX(int bin){
        return x + LEFT_OFFSET + bin * BIN_WIDTH;
    }

    public int getBinsNumber(){
        return bins + 1;
    }

    public int getBinFromValue(double value){
        return (int)  Math.floor((value - rank.getFrom()) / binMZWidth);
    }

    @Override
    public void draw(Context2d ctx){
        ctx.beginPath();
        ctx.setStrokeStyle(AXIS_COLOR);
        ctx.setLineWidth(AXIS_WIDTH);
        ctx.moveTo(x, y);
        ctx.lineTo(x + width + ( 2 * LEFT_OFFSET ), y);
        ctx.closePath();
        ctx.stroke();

        drawTicks(ctx);
    }

    public int getWidth() {
        return width;
    }

    @Override
    public void setMousePosition(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    private void drawTicks(Context2d ctx){
        double textY = y + TICK_HEIGHT + 15 ;
        ctx.setTextAlign(Context2d.TextAlign.CENTER);
        for (Double value : rank) {
            double tickX = getPixelFromValue(value);
            ctx.beginPath();
            ctx.setStrokeStyle(AXIS_COLOR);
            ctx.setLineWidth(AXIS_TICK_WIDTH);
            ctx.moveTo(tickX, y);
            ctx.lineTo(tickX, y + TICK_HEIGHT);
            ctx.closePath();
            ctx.stroke();

            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(2, BigDecimal.ROUND_UP);
            String text = bd.toString();

            ctx.fillText(text, tickX, textY);
        }
    }

}
