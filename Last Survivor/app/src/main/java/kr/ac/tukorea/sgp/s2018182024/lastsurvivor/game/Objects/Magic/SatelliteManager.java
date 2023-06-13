package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Magic;

import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Scene.MainScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Player;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Scene.SelectScene;

public class SatelliteManager extends MagicManager {
    private static final String TAG = SatelliteManager.class.getSimpleName();
    private static float radius = 2.0f;
    private static float speed;
    private static MagicType magicType;

    public SatelliteManager(Player player) {
        init(player);
    }

    public static void init(Player player) {
        SatelliteManager.player = player;
        SatelliteManager.speed = 90.0f;

        magicType = MagicType.SATELLITE;
        magicType.calculateDamage(player);
        magicType.setCooldown(magicType.defaultCooldown());

        genType = GenType.SATELLITE;

        Satellite.setPlayer(player);
    }

    public void generate() {
        MainScene scene = (MainScene) BaseScene.getTopScene();

        generate(scene);
    }

    public static void generate(BaseScene baseScene) {
        MainScene scene = (MainScene) baseScene;

        if(magicType.level() > 1)
            remove(scene);

        float angle = 0.0f;
        float amount = 360.0f / magicType.count();
        for(int i = 0; i < magicType.count(); ++i) {

            scene.addObject(MainScene.Layer.MAGIC,
                    Satellite.get(magicType, player.getX(), player.getY(), angle, radius, speed));
            angle += amount;
        }
    }

    private static void remove(MainScene scene) {
        ArrayList<GameObject> magics = scene.getObjects(MainScene.Layer.MAGIC);
        for(int i = magics.size() - 1; i >= 0; --i) {
            Magic magic = (Magic) magics.get(i);
            MagicType magicType = magic.getMagicType();

            if(MagicType.SATELLITE == magicType) {
                scene.removeObject(MainScene.Layer.MAGIC, magic, false);
            }
        }

    }

}
