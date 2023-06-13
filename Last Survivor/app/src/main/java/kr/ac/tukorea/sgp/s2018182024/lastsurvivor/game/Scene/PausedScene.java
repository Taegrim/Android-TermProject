package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Scene;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Button;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Sprite;

public class PausedScene extends BaseScene {
    private static final String TAG = PausedScene.class.getSimpleName();
    private static final float X_OFFSET = 2.0f;
    private static final float Y_OFFSET = 2.0f;
    private static final float UI_START_SIZE = 3.0f;


    public enum Layer {
        BG, TOUCH, COUNT
    }

    public PausedScene() {
        initLayers(Layer.COUNT);
        addObject(Layer.BG, new Sprite(R.mipmap.sheet_black,
                Metrics.gameWidth / 2.0f, Metrics.gameHeight / 2.0f,
                Metrics.gameWidth - X_OFFSET, Metrics.gameHeight - Y_OFFSET));
        addObject(Layer.TOUCH, new Button(R.mipmap.ui_start,
                Metrics.gameWidth / 2.0f, Metrics.gameHeight / 2.0f,
                UI_START_SIZE, UI_START_SIZE,
                new Button.Callback() {
                    @Override
                    public boolean onTouch(Button.Action action) {
                        popScene();
                        return false;
                    }
                }));
    }

    @Override
    protected boolean isTransparent() {
        return true;
    }

    @Override
    protected int getTouchLayer() {
        return Layer.TOUCH.ordinal();
    }
}
