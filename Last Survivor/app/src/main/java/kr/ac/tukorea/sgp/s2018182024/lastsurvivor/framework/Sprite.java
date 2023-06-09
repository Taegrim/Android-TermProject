package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Sprite implements GameObject {
    protected Bitmap bitmap;
    protected RectF rect = new RectF();
    protected float x, y, width, height;

    protected Sprite() {}

    public Sprite(int resId, float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        if(resId != 0){
            setBitmapResource(resId);
        }
        fixRect();
    }

    public void setBitmapResource(int resId) {
        bitmap = BitmapPool.get(resId);
    }

    protected void fixRect() {
        setSize(width, height);
    }

    protected void setSize(float width, float height) {
        float halfWidth = width / 2;
        float halfHeight = height / 2;
        rect.set(x - halfWidth, y - halfHeight, x + halfWidth, y + halfHeight);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }


    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, rect, null);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void move(float x, float y) {
        this.x += x * BaseScene.frameTime;
        this.y += y * BaseScene.frameTime;
        fixRect();
        fixCollisionRect();
    }

    protected void fixCollisionRect() {

    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }
}
