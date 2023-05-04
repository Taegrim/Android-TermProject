package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.MainScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Player;

public class BulletGenerator extends MagicManager {
    private static final String TAG = BulletGenerator.class.getSimpleName();

    public BulletGenerator(Player player) {
        generation_interval = 1.0f;
        generation_number = 1;
        this.player = player;
        speed = 8.0f;
        attackType = Magic.AttackType.NORMAL;

        magicType = MagicType.BULLET;
        magicType.calculateDamage(player);
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
                            magicType.damage(), attackType));
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
