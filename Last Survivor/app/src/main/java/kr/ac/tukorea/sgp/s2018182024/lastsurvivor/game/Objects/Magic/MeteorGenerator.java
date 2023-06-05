package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Magic;

import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Scene.MainScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Player;

public class MeteorGenerator extends MagicManager {
    private static final String TAG = MeteorGenerator.class.getSimpleName();
    private static final float START_OFFSET_X =
            Meteor.X_SPEED * Meteor.getMoveTime() + Meteor.getBitmapWidth() / 2.0f;
    private static final float START_OFFSET_Y =
            Meteor.Y_SPEED * Meteor.getMoveTime() + Meteor.getBitmapHeight() / 2.0f;
    // 이동 시간 * 속도 만큼 좌 상단에서 시작해서 떨어짐


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
                    Meteor.get(MagicManager.MagicType.METEOR,
                            dx - START_OFFSET_X, dy - START_OFFSET_Y));
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
