package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.MainScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Player;

public class BlizzardGenerator extends MagicManager {
    private static final String TAG = BlizzardGenerator.class.getSimpleName();
    private static final float START_OFFSET_X =
            Blizzard.X_SPEED * Blizzard.getMoveTime() + Blizzard.getBitmapWidth() / 2.0f;
    private static final float START_OFFSET_Y =
            Blizzard.Y_SPEED * Blizzard.getMoveTime() + Blizzard.getBitmapHeight() / 2.0f;
    // 이동 시간 * 속도 만큼 좌 상단에서 시작해서 떨어짐

    public BlizzardGenerator(Player player) {
        this.player = player;

        setMagicType(MagicType.BLIZZARD);

        genType = GenType.BLIZZARD;
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
                    Blizzard.get(MagicType.BLIZZARD,
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
