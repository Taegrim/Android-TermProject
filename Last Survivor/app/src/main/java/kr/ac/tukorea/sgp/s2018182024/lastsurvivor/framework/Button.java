package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework;

import android.util.Log;
import android.view.MotionEvent;

import javax.security.auth.callback.Callback;

public class Button extends Sprite implements Touchable {
    private static final String TAG = Button.class.getSimpleName();
    private final Callback callback;

    public enum Action {
        PRESSED, RELEASED;
    }

    public interface Callback {
        public boolean onTouch(Action action);
    }

    public Button(int resId, float x, float y, float width, float height, Callback callback) {
        super(resId, x, y, width, height);
        this.callback = callback;
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
            callback.onTouch(Action.PRESSED);
        }
        else if(MotionEvent.ACTION_UP == action) {
            callback.onTouch(Action.RELEASED);
        }

        return true;
    }
}
