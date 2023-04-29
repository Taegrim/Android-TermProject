package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game;

import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.AnimationSprite;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.CollisionObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;

public class Player extends AnimationSprite implements CollisionObject {
    private static final String TAG = Player.class.getSimpleName();
    private static final float PLAYER_WIDTH = 35 * Metrics.bitmapRatio;
    private static final float PLAYER_HEIGHT = 64 * Metrics.bitmapRatio;
    private static final float SPEED = 5.f;
    private int dir;
    private float tx, ty;
    private float dx, dy;
    private RectF collisionRect = new RectF();
    private ArrayList<Magic> magics = new ArrayList<>();

    private static final int[] resId = {
            R.mipmap.player_idle, R.mipmap.player_move_updown, R.mipmap.player_move_left,
            R.mipmap.player_move_on_left, R.mipmap.player_move_right, R.mipmap.player_move_on_right
    };

    public Player() {
        super(resId[0], Metrics.gameWidth / 2, Metrics.gameHeight / 2,
                PLAYER_WIDTH, PLAYER_HEIGHT, 6, 3);
        dir = 0;
        tx = x;
        ty = y;
        dx = dy = 0;
    }

    public void changeResource() {
        dir = (dir + 1) % resId.length;
        super.changeResource(resId[dir]);
    }

    public void setTargetPosition(float tx, float ty) {
        this.tx = tx;
        this.ty = ty;
        float dx = tx - this.x;
        float dy = ty - this.y;
        double radian = Math.atan2(dy, dx);
        this.dx = (float) (SPEED * Math.cos(radian));
        this.dy = (float) (SPEED * Math.sin(radian));
    }

    @Override
    public void update() {
        x += dx * BaseScene.frameTime;
        if((dx > 0 && x > tx) || (dx < 0 & x < tx)){
            x = tx;
            dx = 0;
        }
        y += dy * BaseScene.frameTime;
        if((dy > 0 && y > ty) || (dy < 0 && y < ty)){
            y = ty;
            dy = 0;
        }

        fixRect();
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }
}
