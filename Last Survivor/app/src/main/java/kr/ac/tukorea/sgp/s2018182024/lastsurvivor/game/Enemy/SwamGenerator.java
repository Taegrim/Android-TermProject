package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Enemy;

import java.util.Random;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Generator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.MainScene;

public class SwamGenerator extends Generator {
    private float time;
    private int wave;
    private Random r = new Random();

    public SwamGenerator() {
        generation_interval = 3.0f;
        generation_number = 3;
    }

    private void generate() {
        ++wave;
        float x, y;
        MainScene scene = (MainScene) BaseScene.getTopScene();
        for(int i = 0; i < generation_number; ++i) {
            // 좌우 사이드 에서 생성
            if(r.nextInt(2) == 0) {
                x = r.nextInt(2) == 0 ? (4 + Metrics.gameWidth) : -4;
                y = (float)r.nextInt((int)Metrics.gameHeight);
            }
            // 위 아래 에서 생성
            else {
                x = (float)r.nextInt((int)Metrics.gameWidth);
                y = r.nextInt(2) == 0 ? (4 + Metrics.gameHeight) : -4;
            }

            int level = (wave + 10) / 10 - r.nextInt(2);
            if(level < 0)
                level = 0;
            scene.addObject(Swam.get(x, y, level));
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
