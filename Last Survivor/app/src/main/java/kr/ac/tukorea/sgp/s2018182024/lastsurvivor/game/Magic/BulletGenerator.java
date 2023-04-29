package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Enemy.Enemy;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Generator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.MainScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Player;

public class BulletGenerator extends Generator {
    private static final String TAG = BulletGenerator.class.getSimpleName();
    private float time;
    private Player player;
    private float dx, dy, angle, speed;

    public BulletGenerator(Player player) {
        generation_interval = 1.0f;
        generation_number = 1;
        this.player = player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public float getDistance(float x1, float y1, float x2, float y2) {
        return (float) (Math.pow((x2 - x1), 2.0) + Math.pow((y2 - y1), 2.0));
    }

    private void generate() {
        MainScene scene = (MainScene) BaseScene.getTopScene();
        ArrayList<GameObject> enemies = scene.getObjects(MainScene.Layer.ENEMY);

        float playerX = player.getX();
        float playerY = player.getY();
        float enemyX, enemyY;
        float dist, min = 100000.0f;
        int index = 0;

        // 가장 가까운 적 구하기
        for(int i = enemies.size() - 1; i >= 0; --i) {
            Enemy enemy = (Enemy) enemies.get(i);

            enemyX = enemy.getX();
            enemyY = enemy.getY();
            dist = getDistance(playerX, playerY, enemyX, enemyY);
            if(dist < min) {
                min = dist;
                index = i;
            }
        }
        
        // 가장 가까운 적의 방향으로 마법 발사
        if(enemies.size() == 0) {
            return;
        }
        Enemy enemy = (Enemy) enemies.get(index);
        float directionX = playerX - enemy.getX();
        float directionY = playerY - enemy.getY();
        double radian = Math.atan2(directionY, directionX);
        this.dx = (float) Math.cos(radian);
        this.dx = (float) Math.cos(radian);
        this.angle = (float) Math.toDegrees(radian);

        Log.d(TAG, "dx : " + dx + " dy : " + dy);

        for(int i = 0; i < generation_number; ++i) {
            scene.addObject(MainScene.Layer.MAGIC,
                    Bullet.get(playerX, playerY, this.dx, this.dy, this.angle, 8.0f));
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
