package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.RecycleBin;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.MainScene;

public class Cyclone extends Magic {
    private static final String TAG = Cyclone.class.getSimpleName();
    private static final float WIDTH = 1.0f;
    private static final float HEIGHT = 1.0f;
    private static final int FRAME = 10;
    public static final int FRAME_COUNT = 4;
    private long lifeTime;
    private long createdTime;
    private float sizeRatio = 1.0f;
    private float dx, dy;
    private static ValueAnimator animator;

    private static final int resIds[] = {
            R.mipmap.cyclone_a1, R.mipmap.cyclone_a2, R.mipmap.cyclone_a3, R.mipmap.cyclone_a4,
    };

    private int resIndex;

    public static Cyclone get(MagicManager.MagicType type, float x, float y, float dx, float dy,
                              float damage, long lifeTime, MagicManager.AttackType attackType)
    {
        Cyclone cyclone = (Cyclone) RecycleBin.get(Cyclone.class);
        if(cyclone == null) {
            return new Cyclone(type, x, y, dx, dy, damage, lifeTime, attackType);
        }
        cyclone.x = x;
        cyclone.y = y;
        cyclone.init(dx, dy, damage, lifeTime, attackType);
        cyclone.setBitmapResource(resIds[cyclone.resIndex]);
        return cyclone;
    }

    private Cyclone(MagicManager.MagicType type, float x, float y, float dx, float dy, float damage,
                    long lifeTime, MagicManager.AttackType attackType) {
        super(resIds[0], x, y, WIDTH, HEIGHT);
        magicType = type;
        init(dx, dy, damage, lifeTime, attackType);
    }

    public void init(float dx, float dy, float damage, long lifeTime,
                     MagicManager.AttackType attackType)
    {
        this.dx = dx;
        this.dy = dy;
        this.damage = damage;
        this.lifeTime = lifeTime;
        this.attackType = attackType;
        this.createdTime = System.currentTimeMillis();
        this.resIndex = 0;

        createAnimator();
        if(animator != null) {
            if(!animator.isRunning()) {
                animator.setCurrentPlayTime(0);
                animator.start();
            }
        }
        this.width = 1.0f;
        this.height = 1.0f;
        fixRect();
        setCollisionRect();
    }

    @Override
    public void update() {
        x += dx * BaseScene.frameTime;
        y += dy * BaseScene.frameTime;
        fixRect();
        setCollisionRect();

        super.update();
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

        super.draw(canvas);
    }

    private void createAnimator() {
        if(animator == null) {
            animator = ValueAnimator.ofFloat(0.0f, 1.0f);
            animator.setDuration(lifeTime);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                    width = WIDTH * (float)valueAnimator.getAnimatedValue() + 1.0f * sizeRatio;
                    height = width;

                    ArrayList<GameObject> magics = BaseScene.getTopScene().getObjects(MainScene.Layer.MAGIC);
                    for(int i = magics.size() - 1; i >= 0; --i) {
                        Magic magic = (Magic) magics.get(i);
                        if(MagicManager.MagicType.CYCLONE == magic.getMagicType()) {
                            Cyclone cyclone = (Cyclone) magic;
                            cyclone.width = width;
                            cyclone.height = height;
                        }
                    }
                }
            });
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    ArrayList<GameObject> magics = BaseScene.getTopScene().getObjects(MainScene.Layer.MAGIC);
                    for(int i = magics.size() - 1; i >= 0; --i) {
                        Magic magic = (Magic) magics.get(i);
                        if(MagicManager.MagicType.CYCLONE == magic.getMagicType()) {
                            Cyclone cyclone = (Cyclone) magic;
                            cyclone.removeCyclone();
                        }
                    }
                }
            });
        }
        else {
            if(animator.getDuration() != lifeTime) {
                animator.setDuration(lifeTime);
            }
        }
    }

    private void removeCyclone() {
        BaseScene.getTopScene().removeObject(MainScene.Layer.MAGIC, this, false);
    }

    @Override
    public void onRecycle() {
        if(animator != null) {
            animator.end();
        }
    }

    @Override
    public void setCollisionRect() {
        collisionRect.set(rect);
    }
}
