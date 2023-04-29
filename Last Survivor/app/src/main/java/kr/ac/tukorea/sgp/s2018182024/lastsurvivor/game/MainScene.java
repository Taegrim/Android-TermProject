package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game;

import android.view.MotionEvent;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Enemy.SwamGenerator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic.BulletGenerator;

public class MainScene extends BaseScene {
    private static final String TAG = MainScene.class.getSimpleName();
    public final Player player;
    public final Generator generator;

    public MainScene() {
        player = new Player();
        addObject(player);

        generator = new Generator();
        generator.addGenerator(new SwamGenerator());
        addObject(generator);

        generator.addGenerator(new BulletGenerator(player));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch(action){
            case MotionEvent.ACTION_MOVE:
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
