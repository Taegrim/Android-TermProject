package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game;

import android.view.MotionEvent;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Enemy.SwamGenerator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Item.ExpOrb;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Item.ExpOrbGenerator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Item.HealthOrb;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Item.HealthOrbGenerator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic.BulletGenerator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic.MagicManager;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic.ThunderGenerator;

public class MainScene extends BaseScene {
    private static final String TAG = MainScene.class.getSimpleName();
    public final Player player;
    public final Generator generator;

    public enum Layer {
        BG, ITEM, ENEMY, MAGIC, PLAYER, UI, CONTROLLER, COUNT
    }

    public MainScene() {
        initLayers(Layer.COUNT);

        // player
        player = new Player();
        addObject(Layer.PLAYER, player);

        // generators
        // Enemy Generator
        generator = new Generator();
        generator.addGenerator(new SwamGenerator());
        addObject(Layer.CONTROLLER, generator);

        // Magic Generator
        generator.addGenerator(new BulletGenerator(player));
        generator.addGenerator(new ThunderGenerator(player));

        // Item Generator
        generator.addGenerator(new ExpOrbGenerator(player));
        generator.addGenerator(new HealthOrbGenerator());

        // collision
        addObject(Layer.CONTROLLER, new CollisionChecker());

        // bg
        addObject(Layer.BG, new Background(R.mipmap.land_a));
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
