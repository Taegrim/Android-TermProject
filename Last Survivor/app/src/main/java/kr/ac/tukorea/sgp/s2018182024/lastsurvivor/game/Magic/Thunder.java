package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import android.graphics.Canvas;
import android.graphics.Paint;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.RecycleBin;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.MainScene;

public class Thunder extends Magic {
    private static final String TAG = Thunder.class.getSimpleName();
    private static final float WIDTH = 1.0f;
    private static final float HEIGHT = Metrics.gameHeight + 2.0f;
    private static final float COLLISION_TIME = 0.15f;
    private float lifeTime;
    private boolean isCollision;
    private float size = WIDTH;
    private long createdTime;
    private Paint sharedPaint;

    private static final int resIds[] = {
            R.mipmap.thunder_strom_a,R.mipmap.thunder_strom_b, R.mipmap.thunder_strom_c
    };

    public static Thunder get(MagicManager.MagicType type, float x, float y, int resIndex, float lifeTime,
                              Paint paint)
    {
        Thunder thunder = (Thunder) RecycleBin.get(Thunder.class);
        if(thunder == null) {
            return new Thunder(type, x, y, resIndex, lifeTime, paint);
        }
        thunder.x = x;
        thunder.y = y;
        thunder.setBitmapResource(resIds[resIndex]);
        thunder.init(lifeTime, paint);
        return thunder;
    }

    private Thunder(MagicManager.MagicType type, float x, float y, int resIndex, float lifeTime,
                    Paint paint)
    {
        super(resIds[resIndex], x, y, WIDTH, HEIGHT);
        magicType = type;
        init(lifeTime, paint);
    }

    public void init(float lifeTime, Paint paint) {
        this.createdTime = System.currentTimeMillis();
        this.damage = magicType.damage();
        this.lifeTime = lifeTime;
        this.attackType = magicType.attackType();
        this.sharedPaint = paint;
        this.isCollision = true;
        fixRect();
        setCollisionRect();
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdTime) / 1000.0f;

        if(time > COLLISION_TIME) {
            collisionRect.setEmpty();
            isCollision = false;
        }

        if(time > lifeTime) {
            MainScene scene = (MainScene) BaseScene.getTopScene();
            scene.removeObject(MainScene.Layer.MAGIC, this, false);
        }

        canvas.drawBitmap(bitmap, null, rect, sharedPaint);
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
        if(!isCollision)
            return;
        float halfSize = size / 2;
        collisionRect.set(x - halfSize, y - halfSize,
                x + halfSize, y + halfSize);
    }
}
