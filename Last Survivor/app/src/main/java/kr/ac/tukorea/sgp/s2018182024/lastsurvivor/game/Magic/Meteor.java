package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BitmapPool;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.RecycleBin;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.MainScene;

public class Meteor extends Magic {
    private static final String TAG = Meteor.class.getSimpleName();
    private static final float WIDTH = 5.0f;
    private static final float HEIGHT = 15.0f;
    private static final float EXPLOSION_WIDTH = 3.0f;
    private static final int EXPLOSION_START_OFFSET = 4;
    private static final float fps = 20.0f;
    public static final float SPEED = 8.0f;
    private long createdTime;
    private Bitmap explosionBitmap;
    private RectF explosionRect = new RectF();
    private Bitmap landBitmap;


    private static final int meteorResIds[] = {
            R.mipmap.meteor_a01, R.mipmap.meteor_a02,R.mipmap.meteor_a03,R.mipmap.meteor_a04,
            R.mipmap.meteor_a05, R.mipmap.meteor_a06,R.mipmap.meteor_a07,R.mipmap.meteor_a08,
            R.mipmap.meteor_a09, R.mipmap.meteor_a10,R.mipmap.meteor_a11,R.mipmap.meteor_a12,
            R.mipmap.meteor_a13, R.mipmap.meteor_a14,R.mipmap.meteor_a15,R.mipmap.meteor_a16,
            R.mipmap.meteor_a17
    };
    private static final int explosionResIds[] = {
            R.mipmap.explosion_01, R.mipmap.explosion_02, R.mipmap.explosion_03,
            R.mipmap.explosion_04, R.mipmap.explosion_05, R.mipmap.explosion_06,
            R.mipmap.explosion_07, R.mipmap.explosion_08, R.mipmap.explosion_09,
            R.mipmap.explosion_10
    };
    private static final int landResId = R.mipmap.meteor_land;

    public static Meteor get(MagicManager.MagicType type, float x, float y) {
        Meteor meteor = (Meteor) RecycleBin.get(Meteor.class);
        if(meteor == null) {
            return new Meteor(type, x, y);
        }
        meteor.x = x;
        meteor.y = y;
        meteor.setBitmapResource(meteorResIds[0]);
        meteor.init();
        return meteor;
    }

    private Meteor(MagicManager.MagicType type, float x, float y) {
        super(meteorResIds[0], x, y, WIDTH, HEIGHT);
        magicType = type;
        landBitmap = BitmapPool.get(landResId);
        init();
    }

    public void init() {
        this.createdTime = System.currentTimeMillis();
        this.damage = magicType.damage();
        this.attackType = magicType.attackType();

        explosionBitmap = BitmapPool.get(explosionResIds[0]);

        fixRect();
        setCollisionRect();
        fixExplosionRect();
    }

    private void fixExplosionRect() {
        float halfWidth = EXPLOSION_WIDTH / 2.0f;
        float xOffset = WIDTH / 2.0f;
        float yOffset = HEIGHT / 2.0f;
        explosionRect.set(x - halfWidth + xOffset, y - halfWidth + yOffset,
                x + halfWidth + xOffset, y + halfWidth + yOffset);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdTime) / 1000.0f;

        int index = Math.round(time * fps);
        if(index < meteorResIds.length) {
            setBitmapResource(meteorResIds[index]);

            if(index < meteorResIds.length - EXPLOSION_START_OFFSET) {
                x += SPEED * BaseScene.frameTime;
                y += SPEED * 2.0f * BaseScene.frameTime;

                fixRect();
                setCollisionRect();
                fixExplosionRect();
            }
        }

        canvas.drawBitmap(bitmap, null, rect, null);

        // 폭발, 바닥 그리기
        int startOffset = meteorResIds.length - EXPLOSION_START_OFFSET;
        if(startOffset < index) {

            if(index < meteorResIds.length + explosionResIds.length - EXPLOSION_START_OFFSET) {
                explosionBitmap = BitmapPool.get(explosionResIds[index - startOffset]);
            }
            else{
                MainScene scene = (MainScene) BaseScene.getTopScene();
                scene.removeObject(MainScene.Layer.MAGIC, this, false);
            }
            canvas.drawBitmap(landBitmap, null, explosionRect, null);
            canvas.drawBitmap(explosionBitmap, null, explosionRect, null);
        }

    }

    public static float getMoveTime() {
        return (meteorResIds.length - EXPLOSION_START_OFFSET) / fps;
    }

    public static float getBitmapWidth() {
        return WIDTH;
    }

    public static float getBitmapHeight() {
        return HEIGHT;
    }
}
