package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game;

import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.app.MainActivity;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Button;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameView;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Enemy.MineGenerator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Enemy.RampageGenerator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Enemy.SwamGenerator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Item.ExpOrbGenerator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Item.HealthOrbGenerator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic.Blizzard;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic.BlizzardGenerator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic.BulletGenerator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic.Cyclone;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic.CycloneGenerator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic.Magic;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic.MagicManager;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic.Meteor;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic.MeteorGenerator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic.Satellite;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic.SatelliteManager;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic.ThunderGenerator;

public class MainScene extends BaseScene {
    private static final String TAG = MainScene.class.getSimpleName();
    private static final float UI_STOP_SIZE = 0.7f;
    public final Player player;
    public final Generator generator;

    public enum Layer {
        BG, ITEM, ENEMY, PARTICLE, MAGIC, PLAYER, UI, TOUCH, CONTROLLER, COUNT
    }

    public MainScene() {
        initLayers(Layer.COUNT);

        // player
        player = new Player();
        addObject(Layer.PLAYER, player);

        // generators
        generator = new Generator();
        addObject(Layer.CONTROLLER, generator);

        // Enemy Generator
        generator.addGenerator(new SwamGenerator());

        // Magic Generator
//        generator.addGenerator(new BulletGenerator(player));
        generator.addGenerator(new ThunderGenerator(player));
//        generator.addGenerator(new CycloneGenerator(player));
//
//        SatelliteManager.init(player);
//        SatelliteManager.generate(this);

//        generator.addGenerator(new MeteorGenerator(player));

//        addObject(Layer.MAGIC, Blizzard.get(MagicManager.MagicType.BULLET, -2, -2));

//        generator.addGenerator(new BlizzardGenerator(player));


        // Item Generator
        generator.addGenerator(new ExpOrbGenerator(player));
        generator.addGenerator(new HealthOrbGenerator());

        // collision
        addObject(Layer.CONTROLLER, new CollisionChecker());

        // bg
        addObject(Layer.BG, new Background(R.mipmap.land_a));

        // touch ui
        addObject(Layer.TOUCH, new Button(R.mipmap.ui_stop,
                Metrics.gameWidth - UI_STOP_SIZE, UI_STOP_SIZE, UI_STOP_SIZE, UI_STOP_SIZE,
                new Button.Callback() {
                    @Override
                    public boolean onTouch(Button.Action action) {
                        if(Button.Action.PRESSED == action) {
                            Log.d(TAG, "PAUSE");
                        }
                        return true;
                    }
                }));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(super.onTouchEvent(event)){
            return true;
        }

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
        return false;
    }

    @Override
    protected int getTouchLayer() {
        return Layer.TOUCH.ordinal();
    }
}
