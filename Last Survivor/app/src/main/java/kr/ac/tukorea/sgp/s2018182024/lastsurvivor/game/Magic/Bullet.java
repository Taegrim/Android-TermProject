package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.RecycleBin;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.MainScene;

public class Bullet extends Magic {
    private static final float WIDTH = 43 * Metrics.bitmapRatio;
    private static final float HEIGHT = 24 * Metrics.bitmapRatio;
    private float dx, dy, angle;

    public static Bullet get(float x, float y, float dx, float dy, float angle, float damage) {
        Bullet bullet = (Bullet) RecycleBin.get(Bullet.class);
        if(bullet == null) {
            return new Bullet(x, y, dx, dy, angle, damage);
        }
        bullet.init(dx, dy, angle, damage);
        bullet.x = x;
        bullet.y = y;
        bullet.fixRect();
        return bullet;
    }


    private Bullet(float x, float y, float dx, float dy, float angle, float damage) {
        super(R.mipmap.bullet, x, y, WIDTH, HEIGHT);
        init(dx, dy, angle, damage);
    }

    public void init(float dx, float dy, float angle, float damage) {
        this.dx = dx;
        this.dy = dy;
        this.angle = angle;
        this.damage = damage;
        setCollisionRect();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(angle, x, y);
        super.draw(canvas);
        fixRect();
        setCollisionRect();
        canvas.restore();

        x += dx * BaseScene.frameTime;
        y += dy * BaseScene.frameTime;

        if(rect.top > Metrics.gameHeight) {
            BaseScene.getTopScene().removeObject(MainScene.Layer.MAGIC, this, false);
        }
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
