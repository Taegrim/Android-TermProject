package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Enemy;

import java.util.Random;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.MainScene;

public class SwamGenerator extends EnemyGenerator{
    private float time;
    private int wave;
    private Random r = new Random();

    public SwamGenerator() {
        generation_interval = 5.0f;
        generation_number = 1;
    }

    private void generate() {
        ++wave;
        float x, y;
        MainScene scene = (MainScene) BaseScene.getTopScene();
        for(int i = 0; i < generation_number; ++i) {
            x = (float)r.nextInt(10);
            y = (float)r.nextInt(10);

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
