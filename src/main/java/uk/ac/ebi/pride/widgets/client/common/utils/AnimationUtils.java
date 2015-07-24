package uk.ac.ebi.pride.widgets.client.common.utils;

public abstract class AnimationUtils {

    public static double getProgress(double init, double end, double progress){
        if(progress < init){
            return 0.0;
        }else if (progress <= end){
            return (progress - init) / (end - init);
        }else{
            return 1.0;
        }
    }
}
