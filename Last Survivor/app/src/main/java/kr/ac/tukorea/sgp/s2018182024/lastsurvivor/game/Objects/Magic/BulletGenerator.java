package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Magic;

import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Scene.MainScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Player;

public class BulletGenerator extends MagicManager {
    private static final String TAG = BulletGenerator.class.getSimpleName();

    public BulletGenerator(Player player) {
        this.player = player;
        speed = 8.0f;

        setMagicType(MagicType.BULLET);

        genType = GenType.BULLET;
    }

    private void generate() {
        MainScene scene = (MainScene) BaseScene.getTopScene();
        ArrayList<GameObject> enemies = scene.getObjects(MainScene.Layer.ENEMY);

        if(enemies.size() == 0)
            return;

        if(!getCloseEnemyDir(enemies))
            return;

        for(int i = 0; i < magicType.count(); ++i) {
            scene.addObject(MainScene.Layer.MAGIC,
                    Bullet.get(magicType, player.getX(), player.getY(), this.dx, this.dy, this.angle));
        }
    }

    @Override
    public void update() {
        time += BaseScene.frameTime;
        if(time > magicType.cooldown()) {
            generate();
            time -= magicType.cooldown();
        }
    }

}
