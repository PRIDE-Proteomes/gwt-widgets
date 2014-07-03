package uk.ac.ebi.pride.widgets.client.protein.utils;

import com.google.gwt.canvas.dom.client.CssColor;

public abstract class ColorFactory {

    private static int getColorValue(int value){
        return (int) Math.round((100 - value) * 2.55);
    }

    public static CssColor getRedBasedColor(int value){
        int num = getColorValue(value);
        return CssColor.make(255, num, num);
    }

    public static CssColor getGreenBasedColor(int value){
        int num = getColorValue(value);
        return CssColor.make(num, 255, num);
    }

    public static CssColor getBlueBasedColor(int value){
        int num = getColorValue(value);
        return CssColor.make(num, num, 255);
    }
}
