package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import androidx.annotation.NonNull;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BitmapPool;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.MainScene;

public class FallingMagic extends Magic {
    private static final String TAG = FallingMagic.class.getSimpleName();
    protected int magicResIds[];
    protected int particleStartOffset;
    protected float fps;
    protected float xSpeed, ySpeed;
    protected long createdTime;

    public FallingMagic(int resId, float x, float y, float width, float height) {
        super(resId, x, y, width, height);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdTime) / 1000.0f;

        int index = Math.round(time * fps);
        if(index < magicResIds.length) {
            setBitmapResource(magicResIds[index]);

            if(index < magicResIds.length - particleStartOffset) {
                x += this.xSpeed * BaseScene.frameTime;
                y += this.ySpeed * BaseScene.frameTime;

                fixRect();
                resizeMagicByIndex(index);
            }
        }

        canvas.drawBitmap(bitmap, null, rect, null);

        int startOffset = magicResIds.length - particleStartOffset;
        if(index == startOffset) {
            createParticle();
        }
        else if(index == magicResIds.length) {
            MainScene scene = (MainScene) BaseScene.getTopScene();
            scene.removeObject(MainScene.Layer.MAGIC, this, false);
        }

    }

    protected void resizeMagicByIndex(int index) {

    }

}
