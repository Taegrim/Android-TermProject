package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Enemy.Enemy;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.MainScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Player;

public class CycloneGenerator extends MagicManager {
    private static final String TAG = CycloneGenerator.class.getSimpleName();
    private static long LIFE_TIME = 2000;   // ms 단위

    public CycloneGenerator(Player player) {
        generation_interval = 7.0f;
        this.player = player;
        speed = 3.0f;

        magicType = MagicType.CYCLONE;
        magicType.calculateDamage(player);
        magicType.setCooldown(magicType.defaultCooldown());

        genType = GenType.CYCLONE;
    }

    private void generate() {
        MainScene scene = (MainScene) BaseScene.getTopScene();
        ArrayList<GameObject> enemies = scene.getObjects(MainScene.Layer.ENEMY);

        if(enemies.size() == 0)
            return;

        if(!isEvenOneOnScreen(enemies))
            return;

        for(int i = 0; i < magicType.count(); ++i) {
            getRandomDir();

            scene.addObject(MainScene.Layer.MAGIC,
                    Cyclone.get(magicType, player.getX(), player.getY(), this.dx, this.dy,
                            magicType.damage(), LIFE_TIME, magicType.attackType()));
        }

        // 비우기
        enemyIndices.clear();
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
