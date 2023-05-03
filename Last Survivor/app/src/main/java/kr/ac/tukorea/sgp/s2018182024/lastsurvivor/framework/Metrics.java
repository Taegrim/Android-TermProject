package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework;

public class Metrics {
    public static float scale = 1.0f;
    public static float gameWidth = 9.0f;
    public static float gameHeight = 16.0f;
    public static int xOffset = 0, yOffset = 0;
    public static float bitmapRatio = 0.02f;

    public static void setGameSize(float width, float height){
        gameWidth = width;
        gameHeight = height;
    }

    public static boolean isInGameView(float x, float y) {
        if(x > gameWidth) return false;
        if(x < 0) return false;
        if(y > gameHeight) return false;
        if(y < 0) return false;

        return true;
    }

    public static float toGameX(float x){
        return (x - xOffset) / scale;
    }

    public static float toGameY(float y){
        return (y - yOffset) / scale;
    }
}
