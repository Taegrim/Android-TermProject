package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic.Particles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BitmapPool;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.RecycleBin;

public class Explosion extends Particle {
    private static final String TAG = Explosion.class.getSimpleName();
    private static final float SIZE = 3.0f;

    private static int explosionResIds[] = {
            R.mipmap.explosion_01, R.mipmap.explosion_02, R.mipmap.explosion_03,
            R.mipmap.explosion_04, R.mipmap.explosion_05, R.mipmap.explosion_06,
            R.mipmap.explosion_07, R.mipmap.explosion_08, R.mipmap.explosion_09,
            R.mipmap.explosion_10
    };
    private static final int explosionLandResId = R.mipmap.meteor_land;
    private Bitmap landBitmap;
    private RectF landRect = new RectF();

    public static Explosion get(float x, float y, int animatedTime, float damage) {
        Explosion explosion = (Explosion) RecycleBin.get(Explosion.class);
        if(explosion == null) {
            return new Explosion(x, y, animatedTime, damage);
        }
        explosion.x = x;
        explosion.y = y;
        explosion.setBitmapResource(explosionResIds[0]);
        explosion.init(explosionResIds.length, animatedTime, damage);
        return explosion;
    }

    private Explosion(float x, float y, int animatedTime, float damage) {
        super(explosionResIds[0], x, y, SIZE);

        resIds = explosionResIds;
        landBitmap = BitmapPool.get(explosionLandResId);

        init(explosionResIds.length, animatedTime, damage);
    }

    @Override
    protected void init(int frame, int animatedTime, float damage) {
        super.init(frame, animatedTime, damage);
        landRect.set(rect);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(landBitmap, null, landRect, null);
        super.draw(canvas);
    }

    @Override
    public void setCollisionRect() {
        collisionRect.set(rect);
        collisionRect.inset(0.7f, 0.7f);
    }
}
