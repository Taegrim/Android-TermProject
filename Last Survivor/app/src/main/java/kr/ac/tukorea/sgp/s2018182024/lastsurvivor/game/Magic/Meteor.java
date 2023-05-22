package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BitmapPool;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.RecycleBin;

public class Meteor extends Magic {
    private static final String TAG = Meteor.class.getSimpleName();
    private static final float WIDTH = 1.5f;
    private static final float HEIGHT = 10.0f;
    private static final int EXPLOSION_START_OFFSET = 2;
    private int resIndex;
    private static final float fps = 11.0f;
    private long createdTime;
    private Bitmap explosionBitmap;
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
        init();
    }

    public void init() {
        this.createdTime = System.currentTimeMillis();
        this.damage = magicType.damage();
        this.attackType = magicType.attackType();

        explosionBitmap = BitmapPool.get(explosionResIds[0]);
        landBitmap = BitmapPool.get(landResId);

        fixRect();
        setCollisionRect();
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdTime) / 1000.0f;

        int index = Math.round(time * fps);
        if(index < meteorResIds.length) {
            setBitmapResource(meteorResIds[index]);
        }

        canvas.drawBitmap(bitmap, null, rect, null);

        // 폭발, 바닥 그리기
        int startOffset = meteorResIds.length - EXPLOSION_START_OFFSET;
        if(startOffset < index) {

            if(index < meteorResIds.length + explosionResIds.length - EXPLOSION_START_OFFSET) {
                explosionBitmap = BitmapPool.get(explosionResIds[index - startOffset]);
            }
            canvas.drawBitmap(landBitmap, null, rect, null);
            canvas.drawBitmap(explosionBitmap, null, rect, null);
        }

    }
}
