package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.AnimationSprite;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.CollisionObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Recyclable;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.RecycleBin;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Sprite;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.MainScene;

public class Bullet extends Sprite implements CollisionObject, Recyclable {
    private float speed = 8.0f;
    private float damage = 10.0f;
    private static final float WIDTH = 43 * Metrics.bitmapRatio;
    private static final float HEIGHT = 24 * Metrics.bitmapRatio;

    private RectF collisionRect = new RectF();
    private float dx, dy, angle;

    public static Bullet get(float x, float y, float dx, float dy, float angle, float speed) {
        Bullet bullet = (Bullet) RecycleBin.get(Bullet.class);
        if(bullet == null) {
            return new Bullet(x, y, dx, dy, angle, speed);
        }
        bullet.init(dx, dy, angle, speed);
        bullet.x = x;
        bullet.y = y;
        bullet.fixRect();
        return bullet;
    }


    private Bullet(float x, float y, float dx, float dy, float angle, float speed) {
        super(R.mipmap.bullet, x, y, WIDTH, HEIGHT);
        init(dx, dy, angle, speed);
    }

    public void init(float dx, float dy, float angle, float speed) {
        setCollisionRect();
        this.dx = dx;
        this.dy = dy;
        this.angle = angle;
        this.speed = speed;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(angle, x, y);
        super.draw(canvas);
        fixRect();
        setCollisionRect();
        canvas.restore();

        x += dx * speed * BaseScene.frameTime;
        y += dy * speed * BaseScene.frameTime;

        if(rect.top > Metrics.gameHeight) {
            BaseScene.getTopScene().removeObject(MainScene.Layer.MAGIC, this, false);
        }
    }

    public float getDamage() {
        return damage;
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }

    @Override
    public void setCollisionRect() {
        collisionRect.set(rect);
        collisionRect.inset(0.2f, 0.2f);
    }

    @Override
    public void onRecycle() {

    }
}
