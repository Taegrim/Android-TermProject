package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Enemy.Enemy;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.MainScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Player;

public class ThunderGenerator extends MagicManager {
    private static final String TAG = ThunderGenerator.class.getSimpleName();
    private static final int RES_COUNT = 3;
    private static final int MAX_ALPHA = 255;
    private static final float LIFE_TIME = 0.5f;
    private Paint paint;

    public ThunderGenerator(Player player) {
        generation_interval = 2.0f;
        this.player = player;
        paint = new Paint();

        magicType = MagicType.THUNDER;
        magicType.calculateDamage(player);
        magicType.setCooldown(magicType.defaultCooldown());
    }

    private void generate() {
        MainScene scene = (MainScene) BaseScene.getTopScene();
        ArrayList<GameObject> enemies = scene.getObjects(MainScene.Layer.ENEMY);

        if(enemies.size() == 0) {
            return;
        }
        getRandomTargetEnemy(enemies);

        for(int index : enemyIndices) {
            Enemy enemy = (Enemy) enemies.get(index);
            Log.d(TAG, "generate index : " + index);

            scene.addObject(MainScene.Layer.MAGIC,
                    Thunder.get(magicType, enemy.getX(), enemy.getY(), magicType.damage(),
                            r.nextInt(RES_COUNT), LIFE_TIME, magicType.attackType(), paint));
        }
        
        // 비우기
        enemyIndices.clear();
    }

    @Override
    public void update() {
        time += BaseScene.frameTime;

        if(time < LIFE_TIME) {
            paint.setAlpha(MAX_ALPHA - (int)(MAX_ALPHA * (time / LIFE_TIME)));
        }

        if(time > magicType.cooldown()) {
            paint.setAlpha(MAX_ALPHA);
            generate();
            time -= magicType.cooldown();
        }
    }
}
