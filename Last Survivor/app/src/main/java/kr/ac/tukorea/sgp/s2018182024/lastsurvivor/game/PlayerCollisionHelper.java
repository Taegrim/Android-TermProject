package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game;

import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.CollisionHelper;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Enemy.Enemy;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Magic.Particles.Particle;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Scene.MainScene;

public class PlayerCollisionHelper {
    public static void particleCollision(Particle particle, ArrayList<GameObject> enemies)
    {
        MainScene scene = (MainScene) BaseScene.getTopScene();

        for(int i = enemies.size() - 1; i >= 0; --i) {
            Enemy enemy = (Enemy) enemies.get(i);
            if(CollisionHelper.collide(particle, enemy)) {
                boolean death = enemy.decreaseHp(particle.getDamage());
                if(death) {
                    scene.removeObject(MainScene.Layer.ENEMY, enemy, false);
                    scene.player.increaseExp(enemy.getExp());
                }
            }
        }
    }
}
