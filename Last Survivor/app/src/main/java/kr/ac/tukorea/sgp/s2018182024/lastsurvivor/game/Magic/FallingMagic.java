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
    protected int particleResIds[];
    protected int landResId = -1;
    protected float particleSize;
    protected int particleStartOffset;
    protected float fps;
    protected float xSpeed, ySpeed;
    protected long createdTime;
    protected Bitmap particleBitmap, landBitmap;
    protected RectF particleRect = new RectF();

    public FallingMagic(int resId, float x, float y, float width, float height) {
        super(resId, x, y, width, height);
        landResId = -1;
    }

    protected void fixParticleRect() {
        float halfSize = particleSize / 2.0f;
        float xOffset = width / 2.0f;
        float yOffset = height / 2.0f;
        particleRect.set(x - halfSize + xOffset, y - halfSize + yOffset,
                x + halfSize + xOffset, y + halfSize + yOffset);
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
            }
        }

        canvas.drawBitmap(bitmap, null, rect, null);

        int startOffset = magicResIds.length - particleStartOffset;
        if(startOffset == index) {
            fixParticleRect();
            setCollisionRect();
        }
        else if(startOffset < index) {

            if(index < magicResIds.length + particleResIds.length - particleStartOffset) {
                particleBitmap = BitmapPool.get(particleResIds[index - startOffset]);
            }
            else{
                MainScene scene = (MainScene) BaseScene.getTopScene();
                scene.removeObject(MainScene.Layer.MAGIC, this, false);
            }

            if(landResId != -1) {
                canvas.drawBitmap(landBitmap, null, particleRect, null);
            }
            canvas.drawBitmap(particleBitmap, null, particleRect, null);
        }

    }

}
