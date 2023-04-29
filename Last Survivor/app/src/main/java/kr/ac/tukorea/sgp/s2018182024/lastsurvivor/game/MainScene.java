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

    public enum Layer {
        BG, ENEMY, MAGIC, PLAYER, UI,CONTROLLER, COUNT
    }

    public MainScene() {
        initLayers(Layer.COUNT);

        // player
        player = new Player();
        addObject(Layer.PLAYER, player);

        // generator
        generator = new Generator();
        generator.addGenerator(new SwamGenerator());
        addObject(Layer.CONTROLLER, generator);

        generator.addGenerator(new BulletGenerator(player));

        // collision
        addObject(Layer.CONTROLLER, new CollisionChecker());
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
