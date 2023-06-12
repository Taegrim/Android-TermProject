package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Scene;

import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.databinding.SelectMagicBinding;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Button;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameView;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Background;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.CollisionChecker;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Generator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Enemy.SwamGenerator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Item.ExpOrbGenerator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Item.HealthOrbGenerator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Magic.Blizzard;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Magic.BlizzardGenerator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Magic.BulletGenerator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Magic.CycloneGenerator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Magic.MagicManager;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Magic.MeteorGenerator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Magic.SatelliteManager;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Magic.ThunderGenerator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Player;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Option;

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

        Option.loadOptions(GameView.view.getContext(), "data.json");

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
//        generator.addGenerator(new ThunderGenerator(player));
        generator.addGenerator(new CycloneGenerator(player));

//        SatelliteManager.init(player);
//        SatelliteManager.generate(this);

//        generator.addGenerator(new MeteorGenerator(player));
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
                            new SelectScene().pushScene();
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
    protected void onPause() {
        for(ArrayList<GameObject> objects  : layers) {
            for(GameObject object : objects) {
                object.onPause();
            }
        }
    }

    @Override
    protected void onResume() {
        for(ArrayList<GameObject> objects  : layers) {
            for(GameObject object : objects) {
                object.onResume();
            }
        }
    }

    @Override
    protected int getTouchLayer() {
        return Layer.TOUCH.ordinal();
    }
}
