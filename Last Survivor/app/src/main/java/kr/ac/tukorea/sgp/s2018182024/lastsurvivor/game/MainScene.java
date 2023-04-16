package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game;

import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;

public class MainScene extends BaseScene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Player player;

    public MainScene() {
        player = new Player();
        addObject(player);

        Enemy enemy = new Enemy(38 * 0.02f, 50 * 0.02f);
        addObject(enemy);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                float x = Metrics.toGameX(event.getX());
                float y = Metrics.toGameY(event.getY());
                player.setTargetPosition(x, y);
//                player.changeResource();
                return true;
        }
        return super.onTouchEvent(event);
    }
}
