package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Enemy;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.AnimationSprite;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;

public class Swam extends Enemy {
    private static final float WIDTH = 38 * Metrics.bitmapRatio;
    private static final float HEIGHT = 50 * Metrics.bitmapRatio;

    public Swam(float x, float y) {
        super(R.mipmap.enemy1_move, x, y, WIDTH, HEIGHT, 5, 4);

        speed = 1.0f;
    }


}
