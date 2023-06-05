package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Magic;

import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Scene.MainScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Player;

public class CycloneGenerator extends MagicManager {
    private static final String TAG = CycloneGenerator.class.getSimpleName();
    private static long LIFE_TIME = 2000;   // ms 단위

    public CycloneGenerator(Player player) {
        this.player = player;
        speed = 3.0f;

        setMagicType(MagicType.CYCLONE);

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
                    Cyclone.get(magicType, player.getX(), player.getY(), this.dx, this.dy, LIFE_TIME));
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
