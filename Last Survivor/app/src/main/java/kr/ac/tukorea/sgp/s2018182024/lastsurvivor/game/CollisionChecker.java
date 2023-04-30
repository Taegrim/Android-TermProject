package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.CollisionHelper;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Enemy.Enemy;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic.Bullet;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic.Magic;

public class CollisionChecker implements GameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();

    @Override
    public void update() {
        MainScene scene = (MainScene) BaseScene.getTopScene();
        ArrayList<GameObject> enemies = scene.getObjects(MainScene.Layer.ENEMY);
        ArrayList<GameObject> magics = scene.getObjects(MainScene.Layer.MAGIC);
        Player player = (Player) scene.getObjects(MainScene.Layer.PLAYER).get(0);


        for(int i = enemies.size() - 1; i >= 0; --i) {
            Enemy enemy = (Enemy) enemies.get(i);

            // 플레이어와 적의 충돌 처리
            if(CollisionHelper.collide(enemy, player)) {
                if(!player.getIsInvincible()){
                    player.decreaseHp(enemy.getDamage());
                }
            }
            
            // 마법과 적의 충돌 처리
            for(int j = magics.size() - 1; j >= 0; --j) {
                Magic magic = (Magic) magics.get(j);

                if(CollisionHelper.collide(enemy, magic)) {
//                    scene.removeObject(MainScene.Layer.MAGIC, magic, false);
                    boolean death = enemy.decreaseHp(magic.getDamage());
                    if(death) {
                        scene.removeObject(MainScene.Layer.ENEMY, enemy, false);
                        scene.player.increaseExp(enemy.getExp());
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
