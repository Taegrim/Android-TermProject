package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic.Particles;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.util.Log;

import androidx.annotation.NonNull;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Recyclable;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.RecycleBin;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Sprite;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.MainScene;

public class Particle extends Sprite implements Recyclable {
    private static final String TAG = Particle.class.getSimpleName();
    protected ValueAnimator animator;
    protected int resIds[];

    protected Particle(int resId, float x, float y, float size) {
        super(resId, x, y, size, size);
    }

    protected void createAnimator(int frame, int animatedTime) {
        animator = ValueAnimator
                .ofInt(0, frame)
                .setDuration(animatedTime);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                int index = (Integer) valueAnimator.getAnimatedValue();
                if(index == (frame - 1)) {
                    removeParticle();
                }
                else {
                    setBitmapResource(resIds[index]);
                }
            }
        });
    }

    protected void init(int frame, int animatedTime) {
        createAnimator(frame, animatedTime);
        animator.start();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public void onRecycle() {
        animator.cancel();
        animator.setCurrentPlayTime(0);
    }

    private void removeParticle() {
        MainScene scene = (MainScene) BaseScene.getTopScene();
        scene.removeObject(MainScene.Layer.PARTICLE, this, false);
    }
}
