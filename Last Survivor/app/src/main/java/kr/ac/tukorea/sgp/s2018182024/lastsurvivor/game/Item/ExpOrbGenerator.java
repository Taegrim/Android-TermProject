package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Item;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Generator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.MainScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Player;

public class ExpOrbGenerator extends Generator {
    private static final int SMALL_RATE = 60;
    private static final int MEDIUM_RATE = 30;
    private static final int LARGE_RATE = 10;
    private Player player;

    public ExpOrbGenerator(Player player) {
        generation_interval = 5.0f;
        generation_number = 1;
        this.player = player;
    }

    private void generate() {
        float x, y;

        MainScene scene = (MainScene) BaseScene.getTopScene();
        for(int i = 0; i < generation_number; ++i) {
            if(r.nextInt(2) == 0) {
//                x = r.nextInt(2) == 0 ? (4 + Metrics.gameWidth) : -4;{
                x = (float)r.nextInt((int)Metrics.gameWidth);
                y = (float)r.nextInt((int)Metrics.gameHeight);
            }
            // 위 아래 에서 생성
            else {
                x = (float)r.nextInt((int)Metrics.gameWidth);
//                y = r.nextInt(2) == 0 ? (4 + Metrics.gameHeight) : -4;
                y = (float)r.nextInt((int)Metrics.gameHeight);
            }

            int random_number = r.nextInt(100);
            ExpOrb.Type type = ExpOrb.Type.SMALL;
            if(0 <= random_number && random_number < SMALL_RATE) {
                type = ExpOrb.Type.SMALL;
            }
            else if(SMALL_RATE <= random_number && random_number < SMALL_RATE + MEDIUM_RATE) {
                type = ExpOrb.Type.MEDIUM;
            }
            else {
                type = ExpOrb.Type.LARGE;
            }

            scene.addObject(MainScene.Layer.ITEM, ExpOrb.get(type, x, y));
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
