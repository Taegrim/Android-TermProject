package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import android.graphics.Canvas;
import android.util.Log;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.RecycleBin;

public class Blizzard extends Magic {
    private static final String TAG = Blizzard.class.getSimpleName();
    private static final float WIDTH = 2.0f;
    private static final float HEIGHT = 10.0f;

    private static final float fps = 5.0f;
    public static final float SPEED = 5.0f;
    private long createdTime;

    private static final int blizzardResIds[] = {
            R.mipmap.blizzard_a01, R.mipmap.blizzard_a02, R.mipmap.blizzard_a03, R.mipmap.blizzard_a04,
            R.mipmap.blizzard_a05, R.mipmap.blizzard_a06, R.mipmap.blizzard_a07, R.mipmap.blizzard_a08,
            R.mipmap.blizzard_a09, R.mipmap.blizzard_a10, R.mipmap.blizzard_a11, R.mipmap.blizzard_a12
    };

    public static Blizzard get(MagicManager.MagicType type, float x, float y) {
        Blizzard blizzard = (Blizzard) RecycleBin.get(Blizzard.class);
        if(blizzard == null) {
            return new Blizzard(type, x, y);
        }
        blizzard.x = x;
        blizzard.y = y;
        blizzard.setBitmapResource(blizzardResIds[0]);
        blizzard.init();
        return blizzard;
    }

    private Blizzard(MagicManager.MagicType type, float x, float y) {
        super(blizzardResIds[0], x, y, WIDTH, HEIGHT);
        magicType = type;
        init();
    }

    private void init() {
        this.createdTime = System.currentTimeMillis();
        this.damage = magicType.damage();
        this.attackType = magicType.attackType();

        fixRect();
        setCollisionRect();
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdTime) / 1000.0f;

        int index = Math.round(time * fps);
        if(index < blizzardResIds.length) {
            setBitmapResource(blizzardResIds[index]);
        }

        canvas.drawBitmap(bitmap, null, rect, null);
    }
}
