package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Enemy;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Recyclable;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.RecycleBin;

public class Swam extends Enemy {
    private static final float WIDTH = 0.85f;
    private static final float HEIGHT = 1.0f;

    public static Swam get(float x, float y, int level) {
        Swam swam = (Swam) RecycleBin.get(Swam.class);
        if(swam == null) {
            return new Swam(x, y, level);
        }
        swam.x = x;
        swam.y = y;
        swam.init(level);
        return swam;
    }

    private Swam(float x, float y, int level) {
        super(R.mipmap.swam, x, y, WIDTH, HEIGHT, 5, 4);
        speed = 1.0f;
        init(level);
    }

    private void init(int level) {
        hp = maxHp = (level + 1) * 50.0f + 10.0f;
        damage = 10.0f;
        exp = (level + 1) * 2.0f;
        fixRect();
        setCollisionRect();
    }

    @Override
    public void onRecycle() {

    }

    @Override
    public void setCollisionRect() {
        collisionRect.set(rect);
        collisionRect.inset(0.15f, 0.15f);
    }
}
