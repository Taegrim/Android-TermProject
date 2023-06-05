package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Magic.Particles;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.RecycleBin;

public class Ice extends Particle {
    private static final String TAG = Ice.class.getSimpleName();
    private static final float SIZE = 1.5f;

    private static final int iceResIds[] = {
            R.mipmap.transparent_image, R.mipmap.blizzard_particle_02, R.mipmap.blizzard_particle_03,
            R.mipmap.blizzard_particle_04, R.mipmap.blizzard_particle_05, R.mipmap.blizzard_particle_06,
            R.mipmap.blizzard_particle_07,
    };

    public static Ice get(float x, float y, int animatedTime, float damage) {
        Ice ice = (Ice) RecycleBin.get(Ice.class);
        if(ice == null) {
            return new Ice(x, y, animatedTime, damage);
        }
        ice.x = x;
        ice.y = y;
        ice.setBitmapResource(iceResIds[0]);
        ice.init(iceResIds.length, animatedTime, damage);
        return ice;
    }

    private Ice(float x, float y, int animatedTime, float damage) {
        super(iceResIds[0], x, y, SIZE);

        resIds = iceResIds;

        init(iceResIds.length, animatedTime, damage);
    }

    @Override
    public void setCollisionRect() {
        collisionRect.set(rect);
        collisionRect.inset(0.25f, 0.25f);
    }
}
