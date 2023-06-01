package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic.Particles;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.RecycleBin;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Sprite;

public class Explosion extends Particle {
    private static final String TAG = Explosion.class.getSimpleName();
    private static final float SIZE = 3.0f;

    private static int explosionResIds[] = {
            R.mipmap.explosion_01, R.mipmap.explosion_02, R.mipmap.explosion_03,
            R.mipmap.explosion_04, R.mipmap.explosion_05, R.mipmap.explosion_06,
            R.mipmap.explosion_07, R.mipmap.explosion_08, R.mipmap.explosion_09,
            R.mipmap.explosion_10
    };
    private static final int explosionLandResId = R.mipmap.meteor_land;

    public static Explosion get(float x, float y, int animatedTime) {
        Explosion explosion = (Explosion) RecycleBin.get(Explosion.class);
        if(explosion == null) {
            return new Explosion(x, y, animatedTime);
        }
        explosion.x = x;
        explosion.y = y;
        explosion.init(explosionResIds.length, animatedTime);
        return explosion;
    }

    private Explosion(float x, float y, int animatedTime) {
        super(explosionResIds[0], x, y, SIZE);

        resIds = explosionResIds;
        init(explosionResIds.length, animatedTime);
    }

}
