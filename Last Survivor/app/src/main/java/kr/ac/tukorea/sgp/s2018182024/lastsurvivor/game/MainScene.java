package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game;

import android.util.Log;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;

public class MainScene extends BaseScene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Player player;

    public MainScene() {
        player = new Player();
        addObject(player);
    }

}
