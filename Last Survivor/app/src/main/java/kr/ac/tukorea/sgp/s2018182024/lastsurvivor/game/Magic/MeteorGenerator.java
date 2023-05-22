package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.MainScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Player;

public class MeteorGenerator extends MagicManager {
    private static final String TAG = MeteorGenerator.class.getSimpleName();

    public MeteorGenerator(Player player) {
        this.player = player;

        setMagicType(MagicType.METEOR);

        genType = GenType.METEOR;
    }

    private void generate() {
        MainScene scene = (MainScene) BaseScene.getTopScene();
        ArrayList<GameObject> enemies = scene.getObjects(MainScene.Layer.ENEMY);

        if(enemies.size() == 0)
            return;

        if(!isEvenOneOnScreen(enemies))
            return;

        for(int i = 0; i < magicType.count(); ++i) {
            setRandomPosition();

            scene.addObject(MainScene.Layer.MAGIC,
                    Meteor.get(MagicManager.MagicType.METEOR, dx, dy));
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
