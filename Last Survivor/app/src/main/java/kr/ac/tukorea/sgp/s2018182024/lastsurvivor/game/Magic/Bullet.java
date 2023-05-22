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

    public static Bullet get(MagicManager.MagicType type, float x, float y, float dx, float dy,
                             float angle)
    {
        Bullet bullet = (Bullet) RecycleBin.get(Bullet.class);
        if(bullet == null) {
            return new Bullet(type, x, y, dx, dy, angle);
        }
        bullet.x = x;
        bullet.y = y;
        bullet.init(dx, dy, angle);
        return bullet;
    }


    private Bullet(MagicManager.MagicType type, float x, float y, float dx, float dy, float angle)
    {
        super(R.mipmap.bullet, x, y, WIDTH, HEIGHT);
        magicType = type;
        init(dx, dy, angle);
    }

    public void init(float dx, float dy, float angle) {
        this.dx = dx;
        this.dy = dy;
        this.angle = angle;
        this.damage = magicType.damage();
        this.attackType = magicType.attackType();
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
        canvas.save();
        canvas.rotate(angle, x, y);
        super.draw(canvas);
        canvas.restore();

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
