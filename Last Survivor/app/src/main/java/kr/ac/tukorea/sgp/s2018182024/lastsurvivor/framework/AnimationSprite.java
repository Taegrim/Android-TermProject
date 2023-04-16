package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework;

import android.graphics.Canvas;
import android.graphics.Rect;

public class AnimationSprite extends Sprite {
    protected Rect srcRect = new Rect();
    protected int frameCount;
    protected int frameWidth, frameHeight;
    protected float fps;
    protected long createdTime;

    public AnimationSprite(int resId, float x, float y, float width, float height, float fps, int frameCount) {
        super(resId, x, y, width, height);
        this.fps = fps;
        this.frameCount = frameCount;
        setRectFrame(frameCount);
        createdTime = System.currentTimeMillis();
    }

    private void setRectFrame(int frameCount) {
        int imageWidth = bitmap.getWidth();
        frameHeight = bitmap.getHeight();
        if(frameCount == 0){
            frameWidth = frameHeight;
            this.frameCount = imageWidth / frameWidth;
        } else {
            frameWidth = imageWidth / frameCount;
            this.frameCount = frameCount;
        }
        srcRect.set(0, 0, frameWidth, frameHeight);
    }

    public void changeResource(int resId) {
        changeResource(resId, this.frameCount);
    }

    public void changeResource(int resId, int frameCount) {
        setBitmapResource(resId);
        setRectFrame(frameCount);
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdTime) / 1000.0f;
        int frameIndex = Math.round(time * fps) % frameCount;
        srcRect.set(frameIndex * frameWidth, 0, (frameIndex + 1) * frameWidth, frameHeight);
        canvas.drawBitmap(bitmap, srcRect, rect, null);
    }
}
