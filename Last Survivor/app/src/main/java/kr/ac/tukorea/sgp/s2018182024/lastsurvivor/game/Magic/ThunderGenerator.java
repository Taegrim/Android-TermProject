package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Enemy.Enemy;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Generator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.MainScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Player;

public class ThunderGenerator extends Generator {
    private static final String TAG = ThunderGenerator.class.getSimpleName();
    private static final int RES_COUNT = 3;
    private float time;
    private Player player;
    private float damage;
    private Set<Integer> enemyIndices = new HashSet<>();

    public ThunderGenerator(Player player) {
        generation_interval = 1.0f;
        generation_number = 1;
        this.player = player;
        damage = 20.0f;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void getRandomTargetEnemy(ArrayList<GameObject> enemies) {
        int size = enemies.size();
        for(int i = 0; i < generation_number; ++i) {
            int index = r.nextInt(size);
            if(enemyIndices.contains(index)) {
                --i;
            }
            else {
                enemyIndices.add(index);
            }
        }
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
                    Thunder.get(enemy.getX(), enemy.getY(), this.damage, r.nextInt(RES_COUNT)));
        }
        
        // 비우기
        enemyIndices.clear();
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
