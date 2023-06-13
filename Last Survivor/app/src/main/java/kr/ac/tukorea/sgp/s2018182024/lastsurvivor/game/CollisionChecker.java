package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.CollisionHelper;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Enemy.Enemy;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Item.ExpOrb;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Item.Item;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Magic.Magic;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Magic.MagicManager;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Player;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Scene.MainScene;

public class CollisionChecker implements GameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();

    @Override
    public void update() {
        MainScene scene = (MainScene) BaseScene.getTopScene();
        ArrayList<GameObject> enemies = scene.getObjects(MainScene.Layer.ENEMY);
        ArrayList<GameObject> magics = scene.getObjects(MainScene.Layer.MAGIC);
        Player player = (Player) scene.getObjects(MainScene.Layer.PLAYER).get(0);
        ArrayList<GameObject> items = scene.getObjects(MainScene.Layer.ITEM);

        // item 에 대한 처리
        for(int i = items.size() - 1; i >= 0; --i) {
            Item item = (Item) items.get(i);

            // ExpOrb 의 경우 이벤트 충돌을 따로 검사
            if(item instanceof ExpOrb) {
                ExpOrb orb = (ExpOrb) item;

                if(CollisionHelper.eventCollide(orb, player)) {
                    boolean absorption = orb.setAbsorption(true);
                    if(absorption)
                        orb.setTarget(player);
                }
            }

            if(CollisionHelper.collide(item, player)) {
                item.onCollision(player);
                scene.removeObject(MainScene.Layer.ITEM, item, false);
            }
        }

        // enemy 에 대한 처리
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
                    MagicManager.AttackType attackType = magic.getAttackType();
                    MagicManager.MagicType magicType = magic.getMagicType();

                    if(MagicManager.AttackType.NORMAL == attackType) {
                        scene.removeObject(MainScene.Layer.MAGIC, magic, false);
                    }
                    else if(MagicManager.AttackType.PENETRATING == attackType ||
                            MagicManager.AttackType.CONTINUOUS == attackType)
                    {
                        if(!enemy.checkCollisionValidation(magic)) {
                            continue;
                        }
                    }

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

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void move(float x, float y) {

    }

    @Override
    public float getX() {
        return 0;
    }

    @Override
    public float getY() {
        return 0;
    }
}
