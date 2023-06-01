package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic.Particles;

import android.animation.ValueAnimator;

import androidx.annotation.NonNull;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.RecycleBin;

public class Ice extends Particle {
    private static final String TAG = Ice.class.getSimpleName();
    private static final float SIZE = 1.5f;
    private static final float SMALL_SIZE = 0.4f;

    private static final int iceResIds[] = {
            R.mipmap.blizzard_particle_01, R.mipmap.blizzard_particle_02, R.mipmap.blizzard_particle_03,
            R.mipmap.blizzard_particle_04, R.mipmap.blizzard_particle_05, R.mipmap.blizzard_particle_06,
            R.mipmap.blizzard_particle_07,
    };

    public static Ice get(float x, float y, int animatedTime) {
        Ice ice = (Ice) RecycleBin.get(Ice.class);
        if(ice == null) {
            return new Ice(x, y, animatedTime);
        }
        ice.x = x;
        ice.y = y;
        ice.setBitmapResource(iceResIds[0]);
        ice.init(iceResIds.length, animatedTime);
        return ice;
    }

    private Ice(float x, float y, int animatedTime) {
        super(iceResIds[0], x, y, SIZE);

        resIds = iceResIds;

        init(iceResIds.length, animatedTime);
    }

    protected void createAnimator(int frame, int animatedTime) {
        animator = ValueAnimator
                .ofInt(0, frame)
                .setDuration(animatedTime);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                int index = (Integer) valueAnimator.getAnimatedValue();

                if(index == 0) {
                    setSize(SMALL_SIZE, SMALL_SIZE);
                }
                else {
                    setSize(SIZE, SIZE);
                }

                if(index == (frame - 1)) {
                    removeParticle();
                }
                else {
                    setBitmapResource(resIds[index]);
                }
            }
        });
    }
}
