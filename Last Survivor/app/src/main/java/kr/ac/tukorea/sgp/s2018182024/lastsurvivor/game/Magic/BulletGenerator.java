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
    private float damage;

    public BulletGenerator(Player player) {
        generation_interval = 1.0f;
        generation_number = 1;
        this.player = player;
        speed = 8.0f;
        damage = 10.0f;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public float getDistance(float x1, float y1, float x2, float y2) {
        return (float) (Math.pow((x2 - x1), 2.0) + Math.pow((y2 - y1), 2.0));
    }

    public void getCloseEnemyDir(ArrayList<GameObject> enemies) {
        float playerX = player.getX();
        float playerY = player.getY();
        float dist, min = 100000.0f;
        int index = 0;

        for(int i = enemies.size() - 1; i >= 0; --i) {
            Enemy enemy = (Enemy) enemies.get(i);

            dist = getDistance(playerX, playerY, enemy.getX(), enemy.getY());
            if(dist < min) {
                min = dist;
                index = i;
            }
        }

        Enemy enemy = (Enemy) enemies.get(index);
        float directionX = enemy.getX() - playerX;
        float directionY = enemy.getY() - playerY;
        double radian = Math.atan2(directionY, directionX);
        this.dx = (float) (speed * Math.cos(radian));
        this.dy = (float) (speed * Math.sin(radian));
        this.angle = (float) Math.toDegrees(radian);
    }

    private void generate() {
        MainScene scene = (MainScene) BaseScene.getTopScene();
        ArrayList<GameObject> enemies = scene.getObjects(MainScene.Layer.ENEMY);

        if(enemies.size() == 0) {
            return;
        }
        getCloseEnemyDir(enemies);

        for(int i = 0; i < generation_number; ++i) {
            scene.addObject(MainScene.Layer.MAGIC,
                    Bullet.get(player.getX(), player.getY(), this.dx, this.dy, this.angle,
                            this.damage));
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
