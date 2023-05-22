package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import android.util.Log;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.MainScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Player;

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

        float angle = 0.0f;
        float amount = 360.0f / 2;
        for(int i = 0; i < 2; ++i) {

            scene.addObject(MainScene.Layer.MAGIC,
                    Satellite.get(magicType, player.getX(), player.getY(), angle, radius, speed));
            angle += amount;
        }
    }

}
