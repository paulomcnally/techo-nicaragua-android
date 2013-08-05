package com.techo.nicaragua.utils;

import android.util.DisplayMetrics;

public class Density {

    private String density;
    
    private int defaultWidth;
    private int defaultHeight;
    
    private int finalWidth;
    private int finalHeight;
    
    private int myDpi;

    public Density() {
            super();
    }
    
    public void setDpi( int dpi ){
            myDpi = dpi;
    }
    
    public void calcule(){
            
            
            
            switch (myDpi) {

            case DisplayMetrics.DENSITY_LOW:
            //case 120:
                    density = "low";
                    finalWidth = (int) (defaultWidth * 0.75f);
                    finalHeight = (int) ( defaultHeight * 0.75f );
                    break;
            case DisplayMetrics.DENSITY_MEDIUM:
            //case 160:
                    density = "medium";
                    finalWidth = (int) (( defaultWidth * 1.0f ));
                    finalHeight = (int) (( defaultHeight * 1.0f ) );
                    break;
            case DisplayMetrics.DENSITY_HIGH:
            //case 240:
                    density = "high";
                    finalWidth = (int) (( defaultWidth * 1.5f ));
                    finalHeight = (int) (( defaultHeight * 1.5f ) );
                    break;
            case DisplayMetrics.DENSITY_XHIGH:
            //case 360:
                    density = "xhigh";
                    finalWidth = (int) (( defaultWidth * 2.0f ));
                    finalHeight = (int) (( defaultHeight * 2.0f ) );
                    break;
            case DisplayMetrics.DENSITY_XXHIGH:
	            	density = "xxhigh";
	                finalWidth = (int) (( defaultWidth * 3.0f ));
	                finalHeight = (int) (( defaultHeight * 3.0f ) );
	                break;
            }
    }

    public String getString() {
            return density;
    }
    
    public void setDefaultWidth( int width ){
            defaultWidth = width;
    }
    
    public void setDefaultHeight( int height ){
            defaultHeight = height;
    }
    
    public int getWidth( ){
            return finalWidth;
    }
    
    public int getHeight(  ){
            return finalHeight;
    }
    
    /**
     * This method convets dp unit to equivalent device specific value in pixels. 
     * 
     * @param dp A value in dp(Device independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent Pixels equivalent to dp according to device
     */
    public  float convertDpToPixel(float dp){
        float px = dp * (myDpi / 160f);
        return px;
    }
    /**
     * This method converts device specific pixels to device independent pixels.
     * 
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent db equivalent to px value
     */
    public  float convertPixelsToDp(float px){
        float dp = px / (myDpi / 160);
        return dp;

    }

}