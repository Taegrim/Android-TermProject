package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game;

import android.graphics.RectF;

import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.AnimationSprite;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.CollisionObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;

public class Player extends AnimationSprite implements CollisionObject {
    private static final float PLAYER_WIDTH = 35 * 0.03f;
    private static final float PLAYER_HEIGHT = 64 * 0.03f;
    private int dir;
    private RectF collisionRect = new RectF();
    private ArrayList<Magic> magics = new ArrayList<>();

    private static final int[] resId = {
            R.mipmap.move_updown, R.mipmap.move_left, R.mipmap.move_right
    };

    public Player() {
        super(resId[0], Metrics.gameWidth / 2, Metrics.gameHeight / 2,
                PLAYER_WIDTH, PLAYER_HEIGHT, 10, 6);
        dir = 0;
    }

    @Override
    public void update() {
        super.update();
        fixRect();
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }
}
