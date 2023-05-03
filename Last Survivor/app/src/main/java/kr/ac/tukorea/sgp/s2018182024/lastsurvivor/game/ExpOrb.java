package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.RecycleBin;

public class ExpOrb extends Item {
    private static final float WIDTH = 1.0f;
    private static final float HEIGHT = 1.0f;

    public static ExpOrb get(float x, float y) {
        ExpOrb orb = (ExpOrb) RecycleBin.get(ExpOrb.class);
        if(orb == null) {
            return new ExpOrb(x, y);
        }
        orb.x = x;
        orb.y = y;
        orb.init();
        return orb;
    }

    private ExpOrb(float x, float y) {
        super(R.mipmap.exp_orb_a, x, y, WIDTH, HEIGHT);
        init();
    }

    public void init() {
        fixRect();
        setCollisionRect();
    }

    @Override
    public void setCollisionRect() {
        collisionRect.set(rect);
        collisionRect.inset(0.22f, 0.22f);
    }
}
