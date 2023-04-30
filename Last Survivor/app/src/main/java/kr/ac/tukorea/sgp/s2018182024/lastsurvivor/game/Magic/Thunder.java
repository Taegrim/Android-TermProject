package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import android.graphics.Canvas;
import android.util.Log;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.RecycleBin;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.MainScene;

public class Thunder extends Magic {
    private static final String TAG = Thunder.class.getSimpleName();
    private static final float WIDTH = 55 * Metrics.bitmapRatio;
    private static final float HEIGHT = 440 * Metrics.bitmapRatio;
    private static final float LIFE_TIME = 2.0f;
    private long createdTime;

    private static final int resIds[] = {
            R.mipmap.thunder_strom_a,R.mipmap.thunder_strom_b, R.mipmap.thunder_strom_c
    };

    public static Thunder get(float x, float y, float damage, int resIndex) {
        Thunder thunder = (Thunder) RecycleBin.get(Thunder.class);
        if(thunder == null) {
            return new Thunder(x, y, damage, resIndex);
        }
        thunder.x = x;
        thunder.y = y;
        thunder.fixRect();
        thunder.setBitmapResource(resIds[resIndex]);
        thunder.init(damage);
        return thunder;
    }

    private Thunder(float x, float y, float damage, int resIndex) {
        super(resIds[resIndex], x, y, WIDTH, HEIGHT);
        init(damage);
    }

    public void init(float damage) {
        this.damage = damage;
        this.createdTime = System.currentTimeMillis();
        setCollisionRect();
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdTime) / 1000.0f;
        if(time > LIFE_TIME) {
            MainScene scene = (MainScene) BaseScene.getTopScene();
            scene.removeObject(MainScene.Layer.MAGIC, this, false);
        }

        canvas.drawBitmap(bitmap, null, rect, null);
    }

    protected void fixRect() {
        setSize(WIDTH, HEIGHT);
    }

    // Thunder 는 적의 x, y 로 떨어져야 하므로
    // x,y 에서 위로 그림
    protected void setSize(float width, float height) {
        float halfWidth = width / 2;
        rect.set(x - halfWidth, y - height, x + halfWidth, y);
    }

    @Override
    public void setCollisionRect() {
        //collisionRect.set(rect);
    }
}
