package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Item;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.FLOAT;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Generator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Scene.MainScene;

public class HealthOrbGenerator extends Generator {
    private static final float DEFAULT_RESTORE_AMOUNT = 20;

    private FLOAT restoreAmount = new FLOAT();

    public HealthOrbGenerator() {
        generation_interval = 12.0f;
        generation_number = 1;
        restoreAmount.set(DEFAULT_RESTORE_AMOUNT);
    }

    private void generate() {
        float x, y;

        MainScene scene = (MainScene) BaseScene.getTopScene();
        for(int i = 0; i < generation_number; ++i) {
            if(r.nextInt(2) == 0) {
//                x = r.nextInt(2) == 0 ? (4 + Metrics.gameWidth) : -4;{
                x = (float)r.nextInt((int) Metrics.gameWidth);
                y = (float)r.nextInt((int)Metrics.gameHeight);
            }
            // 위 아래 에서 생성
            else {
                x = (float)r.nextInt((int)Metrics.gameWidth);
//                y = r.nextInt(2) == 0 ? (4 + Metrics.gameHeight) : -4;
                y = (float)r.nextInt((int)Metrics.gameHeight);
            }

            scene.addObject(MainScene.Layer.ITEM, HealthOrb.get(x, y, restoreAmount.get()));
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
