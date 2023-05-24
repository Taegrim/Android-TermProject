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

public class Meteor extends FallingMagic {
    private static final String TAG = Meteor.class.getSimpleName();
    private static final float WIDTH = 5.0f;
    private static final float HEIGHT = 15.0f;
    private static final float EXPLOSION_WIDTH = 3.0f;
    private static final int EXPLOSION_START_OFFSET = 2;
    private static final float FPS = 20.0f;
    public static final float X_SPEED = 8.0f;
    public static final float Y_SPEED = 16.0f;

    private static final int meteorResIds[] = {
            R.mipmap.meteor_a01, R.mipmap.meteor_a02,R.mipmap.meteor_a03,R.mipmap.meteor_a04,
            R.mipmap.meteor_a05, R.mipmap.meteor_a06,R.mipmap.meteor_a07,R.mipmap.meteor_a08,
            R.mipmap.meteor_a09, R.mipmap.meteor_a10,R.mipmap.meteor_a11,R.mipmap.meteor_a12,
            R.mipmap.meteor_a13, R.mipmap.meteor_a14,R.mipmap.meteor_a15,R.mipmap.meteor_a16,
            R.mipmap.meteor_a17, R.mipmap.transparent_image
    };
    private static final int explosionResIds[] = {
            R.mipmap.explosion_01, R.mipmap.explosion_02, R.mipmap.explosion_03,
            R.mipmap.explosion_04, R.mipmap.explosion_05, R.mipmap.explosion_06,
            R.mipmap.explosion_07, R.mipmap.explosion_08, R.mipmap.explosion_09,
            R.mipmap.explosion_10
    };
    private static final int explosionLandResId = R.mipmap.meteor_land;

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

        setFallingValue();

        magicType = type;
        landBitmap = BitmapPool.get(landResId);
        init();
    }

    public void init() {
        this.createdTime = System.currentTimeMillis();
        this.damage = magicType.damage();
        this.attackType = magicType.attackType();

        particleBitmap = BitmapPool.get(explosionResIds[0]);

        fixRect();
    }

    private void setFallingValue() {
        magicResIds = meteorResIds;
        particleResIds = explosionResIds;
        landResId = explosionLandResId;
        xSpeed = X_SPEED;
        ySpeed = Y_SPEED;
        fps = FPS;
        particleSize = EXPLOSION_WIDTH;
        particleStartOffset = EXPLOSION_START_OFFSET;
    }

    public static float getMoveTime() {
        return (meteorResIds.length - EXPLOSION_START_OFFSET) / FPS;
    }

    public static float getBitmapWidth() {
        return WIDTH;
    }

    public static float getBitmapHeight() {
        return HEIGHT;
    }
}
