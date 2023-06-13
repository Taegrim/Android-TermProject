package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework;

import android.graphics.Canvas;

public interface GameObject {
    public void update();
    public void draw(Canvas canvas);
    public void onPause();
    public void onResume();
    public void move(float x, float y);
    public float getX();
    public float getY();
}
