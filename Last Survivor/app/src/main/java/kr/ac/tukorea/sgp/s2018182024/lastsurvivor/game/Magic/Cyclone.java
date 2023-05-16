package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import android.animation.ValueAnimator;
import android.graphics.Canvas;

import androidx.annotation.NonNull;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.RecycleBin;

public class Cyclone extends Magic {
    private static final String TAG = Cyclone.class.getSimpleName();
    private static final float WIDTH = 1.0f;
    private static final float HEIGHT = 1.0f;
    private static final int FRAME = 10;
    public static final int FRAME_COUNT = 4;
    private long lifeTime;
    private long createdTime;
    private float sizeRatio = 1.0f;
    private static ValueAnimator animator;

    private static final int resIds[] = {
            R.mipmap.cyclone_a1, R.mipmap.cyclone_a2, R.mipmap.cyclone_a3, R.mipmap.cyclone_a4,
    };

    private int resIndex;

    public static Cyclone get(MagicManager.MagicType type, float x, float y, float damage,
                              long lifeTime, MagicManager.AttackType attackType)
    {
        Cyclone cyclone = (Cyclone) RecycleBin.get(Cyclone.class);
        if(cyclone == null) {
            return new Cyclone(type, x, y, damage, lifeTime, attackType);
        }
        cyclone.x = x;
        cyclone.y = y;
        cyclone.init(damage, lifeTime, attackType);
        cyclone.setBitmapResource(resIds[cyclone.resIndex]);
        return cyclone;
    }

    private Cyclone(MagicManager.MagicType type, float x, float y, float damage,
                    long lifeTime, MagicManager.AttackType attackType) {
        super(resIds[0], x, y, WIDTH, HEIGHT);
        magicType = type;
        createAnimator();
        init(damage, lifeTime, attackType);
    }

    public void init(float damage, long lifeTime, MagicManager.AttackType attackType) {
        this.damage = damage;
        this.lifeTime = lifeTime;
        this.attackType = attackType;
        this.createdTime = System.currentTimeMillis();
        this.resIndex = 0;
        fixRect();
        setCollisionRect();

        if(animator != null) {
            animator.start();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdTime) / 1000.0f;
        int frameIndex = Math.round(time * FRAME) % FRAME_COUNT;
        if(frameIndex != resIndex) {
            resIndex = frameIndex;
            setBitmapResource(resIds[resIndex]);
        }

        canvas.drawBitmap(bitmap, null, rect, null);
    }

    private void createAnimator() {
        if(animator == null) {
            animator = ValueAnimator.ofFloat(1.0f, 2.0f);
            animator.setDuration(lifeTime);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                    width = WIDTH * (float)valueAnimator.getAnimatedValue() * sizeRatio;
                    height = width;
                    fixRect();
                    setCollisionRect();
                }
            });
        }
    }

    @Override
    public void onRecycle() {
        if(animator != null) {
            animator.cancel();
            animator = null;
        }
    }
}
