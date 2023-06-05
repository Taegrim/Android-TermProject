package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Magic.Particles;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.RecycleBin;

public class Lightning extends Particle {
    private static final String TAG = Lightning.class.getSimpleName();
    private static final float SIZE = 1.5f;

    private static final int lightningResIds[] = {
            R.mipmap.lightning_01, R.mipmap.lightning_02, R.mipmap.lightning_03,
            R.mipmap.lightning_04, R.mipmap.lightning_05, R.mipmap.lightning_06,
            R.mipmap.lightning_07, R.mipmap.lightning_08, R.mipmap.lightning_09,
    };

    public static Lightning get(float x, float y, int animatedTime, float damage) {
        Lightning lightning = (Lightning) RecycleBin.get(Lightning.class);
        if(lightning == null) {
            return new Lightning(x, y, animatedTime, damage);
        }
        lightning.x = x;
        lightning.y = y;
        lightning.setBitmapResource(lightningResIds[0]);
        lightning.init(lightningResIds.length, animatedTime, damage);
        return lightning;
    }

    private Lightning(float x, float y, int animatedTime, float damage) {
        super(lightningResIds[0], x, y, SIZE);

        resIds = lightningResIds;

        init(lightningResIds.length, animatedTime, damage);
    }

    @Override
    public void setCollisionRect() {
        collisionRect.set(rect);
        collisionRect.inset(0.3f, 0.3f);
    }
}
