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
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Magic.MagicManager;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Player;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Option;

public class MainScene extends BaseScene implements Player.Listener {
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
        player = new Player(this);
        addObject(Layer.PLAYER, player);
        MagicManager.setPlayer(player);

        // generators
        generator = new Generator();
        addObject(Layer.CONTROLLER, generator);

        // Enemy Generator
        generator.addGenerator(new SwamGenerator());

        // Magic Generator
        generator.addGenerator(MagicManager.Create(MagicManager.MagicType.BULLET));

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
                            selectMagic();
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
            return true;
        }
        return super.onBackPressed();
    }

    @Override
    protected int getTouchLayer() {
        return Layer.TOUCH.ordinal();
    }

    @Override
    public void onLevelUp() {
        selectMagic();
    }
    
    private void selectMagic() {
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
                Option option = (Option) binding.firstOption.getTag();
                Log.d(TAG, "NAME : " + option.magicType.name());
                magicLevelUp(option.magicType);
            }
            else if(view.getId() == binding.secondItem.getId()) {
                Option option = (Option) binding.secondOption.getTag();
                Log.d(TAG, "NAME : " + option.magicType.name());
                magicLevelUp(option.magicType);
            }
            else if(view.getId() == binding.thirdItem.getId()){
                Option option = (Option) binding.thirdOption.getTag();
                Log.d(TAG, "NAME : " + option.magicType.name());
                magicLevelUp(option.magicType);
            }

            removeSelectView();
        }
    };

    private View.OnClickListener closeOption = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            removeSelectView();
        }
    };

    private void magicLevelUp(MagicManager.MagicType magicType) {
        if(magicType.level() >= magicType.maxLevel()) {
            return;
        }

        MagicManager.onLevelUp(magicType, player);
        if (magicType.level() == 1) {
            Generator gen = MagicManager.Create(magicType);
            if(gen == null)
                return;
            generator.addGenerator(gen);
        }
    }

    private void setContent() {
        for(int i = 0; i < OPTION_COUNT; ++i) {
            int tempIndex = r.nextInt(MAX_OPTION);
            int temp = optionNumbers[tempIndex];
            optionNumbers[tempIndex] = optionNumbers[i];
            optionNumbers[i] = temp;
        }

        // 선택지 옵션 설정
        Option option = Option.get(optionNumbers[0]);
        binding.image01.setImageBitmap(option.getImage());
        binding.name01.setText(option.name);

        int level = option.magicType.level();
        binding.level01.setText("Lv " + (level + 1));
        binding.description01.setText(option.description.get(level));
        binding.firstOption.setTag(option);


        option = Option.get(optionNumbers[1]);
        binding.image02.setImageBitmap(option.getImage());
        binding.name02.setText(option.name);

        level = option.magicType.level();
        binding.level02.setText("Lv " + (level + 1));
        binding.description02.setText(option.description.get(level));
        binding.secondOption.setTag(option);


        option = Option.get(optionNumbers[2]);
        binding.image03.setImageBitmap(option.getImage());
        binding.name03.setText(option.name);

        level = option.magicType.level();
        binding.level03.setText("Lv " + (level + 1));
        binding.description03.setText(option.description.get(level));
        binding.thirdOption.setTag(option);


        // 클릭 시 호출될 함수 설정
        binding.firstItem.setOnClickListener(selectOption);
        binding.secondItem.setOnClickListener(selectOption);
        binding.thirdItem.setOnClickListener(selectOption);

        // 현재 레벨 텍스트 설정
        binding.currentLevel.setText("현재 레벨 : " + player.getLevel());

        // 선택하지 않고 종료 함수 연결
        binding.closeSelect.setOnClickListener(closeOption);
    }

    private void removeSelectView() {
        ((ViewGroup)binding.content.getParent()).removeView(binding.content);
        resume();
        isSelect = false;
    }
}
