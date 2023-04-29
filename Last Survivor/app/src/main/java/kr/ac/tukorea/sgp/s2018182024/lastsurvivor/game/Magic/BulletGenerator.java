package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import android.graphics.Canvas;
import android.util.Log;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Generator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.MainScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Player;

public class BulletGenerator extends Generator {
    private static final String TAG = BulletGenerator.class.getSimpleName();
    private float time;
    private Player player;

    public BulletGenerator(Player player) {
        generation_interval = 3.0f;
        generation_number = 1;
        this.player = player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private void generate() {
        MainScene scene = (MainScene) BaseScene.getTopScene();

        for(int i = 0; i < generation_number; ++i) {
            scene.addObject(Bullet.get(player.getX(), player.getY()));
        }
    }

    @Override
    public void update() {
        time += BaseScene.frameTime;
        if(time > generation_interval) {
            generate();
            time -= generation_interval;
        }
    }
}
