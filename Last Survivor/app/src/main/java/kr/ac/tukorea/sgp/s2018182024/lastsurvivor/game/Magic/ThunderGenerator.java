package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Enemy.Enemy;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Generator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.MainScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Player;

public class ThunderGenerator extends Generator {
    private static final String TAG = ThunderGenerator.class.getSimpleName();
    private static final int RES_COUNT = 3;
    private static final int MAX_ALPHA = 255;
    private float time, lifeTime = 0.5f;
    private Player player;
    private Paint paint;
    private float damage;
    //private Set<Integer> enemyIndices = new HashSet<>();
    private ArrayList<Integer> enemyIndices = new ArrayList<>();

    public ThunderGenerator(Player player) {
        generation_interval = 2.0f;
        generation_number = 2;
        this.player = player;
        damage = 20.0f;
        paint = new Paint();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void getRandomTargetEnemy(ArrayList<GameObject> enemies) {
        for(int i = enemies.size() - 1; i >= 0; --i) {
            Enemy enemy = (Enemy) enemies.get(i);

            // 화면 내에 있는 적들만 처리
            if(Metrics.isInGameView(enemy.getX(), enemy.getY())) {
               enemyIndices.add(i);
            }
        }

        int size = enemyIndices.size();
        // 화면 내 적이 생성 수보다 많다면 랜덤 뽑기, 적다면 화면 내 모든 적에게 처리
        if(size > generation_number) {
            for(int i = 0; i < generation_number; ++i) {
                int randomIndex = r.nextInt(size - i) + i;
                int enemyIndex = enemyIndices.get(randomIndex);
                enemyIndices.set(randomIndex, enemyIndices.get(i));
                enemyIndices.set(i, enemyIndex);
            }

            for(int i = size - 1; i >= generation_number; --i) {
                enemyIndices.remove(i);
            }
        }
    }

    private void generate() {
        MainScene scene = (MainScene) BaseScene.getTopScene();
        ArrayList<GameObject> enemies = scene.getObjects(MainScene.Layer.ENEMY);

        if(enemies.size() == 0) {
            return;
        }

        paint.setAlpha(MAX_ALPHA);
        getRandomTargetEnemy(enemies);

        for(int index : enemyIndices) {
            Enemy enemy = (Enemy) enemies.get(index);
            Log.d(TAG, "generate index : " + index);

            scene.addObject(MainScene.Layer.MAGIC,
                    Thunder.get(enemy.getX(), enemy.getY(), this.damage, r.nextInt(RES_COUNT), paint));
        }
        
        // 비우기
        enemyIndices.clear();
    }

    @Override
    public void update() {
        time += BaseScene.frameTime;

        if(time < lifeTime) {
            paint.setAlpha(MAX_ALPHA - (int)(MAX_ALPHA * (time / lifeTime)));
        }

        if(time > generation_interval) {
            generate();
            time -= generation_interval;
        }
    }
}
