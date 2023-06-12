package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Scene;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;

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
    private static final int MAX_OPTION = 6;
    private static final int OPTION_COUNT = 3;
    public final Player player;
    public final Generator generator;
    private boolean isSelect = false;
    private SelectMagicBinding binding;
    private Random r = new Random();
    private int[] optionNumbers = new int[MAX_OPTION];

    public enum Layer {
        BG, ITEM, ENEMY, PARTICLE, MAGIC, PLAYER, UI, TOUCH, CONTROLLER, COUNT
    }

    public MainScene() {
        initLayers(Layer.COUNT);

        Option.loadOptions(GameView.view.getContext(), "data.json");

        Generator.setRandom(r);

        for(int i = 0; i < MAX_OPTION; ++i) {
            optionNumbers[i] = i;
        }

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
//                            new SelectScene().pushScene();
                            doSelect();
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

    private void pause() {
        GameView.view.pauseGame();
    }

    private void resume() {
        GameView.view.resumeGame();
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
    public boolean onBackPressed() {
        if(isSelect) {
            removeSelectView();
            isSelect = false;
            return true;
        }
        return super.onBackPressed();
    }

    @Override
    protected int getTouchLayer() {
        return Layer.TOUCH.ordinal();
    }

    private void doSelect() {
        isSelect = true;
        pause();

        ViewGroup.LayoutParams linear = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        binding = SelectMagicBinding.inflate(GameView.view.getActivity().getLayoutInflater());

        setContent();

        GameView.view.getActivity().addContentView(binding.getRoot(), linear);
    }

    private View.OnClickListener selectOption = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == binding.firstItem.getId()) {
                Log.d(TAG, "NAME : " + binding.name01.getText());
            }
            else if(view.getId() == binding.secondItem.getId()) {
                Log.d(TAG, "NAME : " + binding.name02.getText());
            }
            else if(view.getId() == binding.thirdItem.getId()){
                Log.d(TAG, "NAME : " + binding.name03.getText());
            }
        }
    };

    private void setContent() {
        for(int i = 0; i < OPTION_COUNT; ++i) {
            int tempIndex = r.nextInt(MAX_OPTION);
            int temp = optionNumbers[tempIndex];
            optionNumbers[tempIndex] = optionNumbers[i];
            optionNumbers[i] = temp;
        }

        Option option = Option.get(optionNumbers[0]);
        binding.image01.setImageBitmap(option.getImage());
        binding.name01.setText(option.name);
        binding.level01.setText("Lv " + option.currentLevel);
        binding.description01.setText(option.description.get(option.currentLevel));

        option = Option.get(optionNumbers[1]);
        binding.image02.setImageBitmap(option.getImage());
        binding.name02.setText(option.name);
        binding.level02.setText("Lv " + option.currentLevel);
        binding.description02.setText(option.description.get(option.currentLevel));


        option = Option.get(optionNumbers[2]);
        binding.image03.setImageBitmap(option.getImage());
        binding.name03.setText(option.name);
        binding.level03.setText("Lv " + option.currentLevel);
        binding.description03.setText(option.description.get(option.currentLevel));

        binding.firstItem.setOnClickListener(selectOption);
        binding.secondItem.setOnClickListener(selectOption);
        binding.thirdItem.setOnClickListener(selectOption);
    }

    private void removeSelectView() {
        ((ViewGroup)binding.content.getParent()).removeView(binding.content);
        resume();
    }
}
