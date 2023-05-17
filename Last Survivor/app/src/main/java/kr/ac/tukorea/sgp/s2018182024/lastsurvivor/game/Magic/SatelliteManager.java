package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.MainScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Player;

public class SatelliteManager extends MagicManager {
    private static final String TAG = SatelliteManager.class.getSimpleName();
    private static float radius = 2.0f;

    public SatelliteManager(Player player) {
        this.player = player;
        this.speed = 10.0f;

        // Satellite 로 변경 필요
        magicType = MagicType.BULLET;
        magicType.calculateDamage(player);
        magicType.setCooldown(magicType.defaultCooldown());

        genType = GenType.BULLET;
        //-------------------
        Satellite.setPlayer(player);
    }

    public static void init(Player player) {
        SatelliteManager.player = player;
        SatelliteManager.speed = 10.0f;

        magicType = MagicType.BULLET;
        magicType.calculateDamage(player);
        magicType.setCooldown(magicType.defaultCooldown());

        //-------------------
        Satellite.setPlayer(player);
    }

    public static void generate() {
        MainScene scene = (MainScene) BaseScene.getTopScene();

        float angle = 0.0f;
        float amount = 360.0f / 2;
        for(int i = 0; i < 2; ++i) {


            scene.addObject(MainScene.Layer.MAGIC,
                    Satellite.get(SatelliteManager.magicType, player.getX(), player.getY(), angle, radius,
                            SatelliteManager.magicType.damage(), speed, SatelliteManager.magicType.attackType()));
            angle += amount;
        }
    }



}
