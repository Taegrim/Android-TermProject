package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Enemy;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Recyclable;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.RecycleBin;

public class Swam extends Enemy implements Recyclable {
    private static final float WIDTH = 38 * Metrics.bitmapRatio;
    private static final float HEIGHT = 50 * Metrics.bitmapRatio;

    public static Swam get(float x, float y, int level) {
        Swam swam = (Swam) RecycleBin.get(Swam.class);
        if(swam == null) {
            return new Swam(x, y, level);
        }
        swam.init(level);
        swam.x = x;
        swam.y = y;
        swam.fixRect();
        return swam;
    }

    public Swam(float x, float y, int level) {
        super(R.mipmap.enemy1_move, x, y, WIDTH, HEIGHT, 5, 4);
        speed = 1.0f;
    }

    private void init(int level) {
        hp = maxHp = (level + 1) * 10;
    }

    @Override
    public void onRecycle() {

    }
}
