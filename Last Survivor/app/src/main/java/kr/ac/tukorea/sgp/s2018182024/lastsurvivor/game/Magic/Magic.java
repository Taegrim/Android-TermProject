package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import android.graphics.RectF;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.CollisionObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Recyclable;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Sprite;

public class Magic extends Sprite implements CollisionObject, Recyclable {
    protected float damage;
    protected RectF collisionRect = new RectF();

    public Magic(int resId, float x, float y, float width, float height) {
        super(resId, x, y, width, height);
    }

    public float getDamage() {
        return damage;
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }

    @Override
    public void setCollisionRect() {

    }

    @Override
    public void onRecycle() {

    }
}
