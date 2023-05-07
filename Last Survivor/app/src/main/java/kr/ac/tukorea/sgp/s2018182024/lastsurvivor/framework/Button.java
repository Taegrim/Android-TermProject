package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework;

import android.util.Log;
import android.view.MotionEvent;

public class Button extends Sprite implements Touchable {
    private static final String TAG = Button.class.getSimpleName();



    public enum Action {
        PRESSED, RELEASED;
    }

    public Button(int resId, float x, float y, float width, float height) {
        super(resId, x, y, width, height);
        fixRect();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = Metrics.toGameX(event.getX());
        float y = Metrics.toGameY(event.getY());
        if(!rect.contains(x, y)) {
            return false;
        }

        int action = event.getAction();
        if(MotionEvent.ACTION_DOWN == action) {
            Log.d(TAG, "pressed!");
        }
        else if(MotionEvent.ACTION_UP == action) {
            Log.d(TAG, "released!");
        }

        return true;
    }
}
