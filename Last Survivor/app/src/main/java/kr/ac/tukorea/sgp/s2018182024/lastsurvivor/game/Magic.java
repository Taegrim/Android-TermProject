package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game;

import android.graphics.RectF;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.AnimationSprite;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.CollisionObject;

public class Magic extends AnimationSprite implements CollisionObject, MagicObject {
    protected RectF collisionRect = new RectF();
    protected float damage;
    protected float interval = 0.5f;
    protected float elapsedTime = 0.f;

    public Magic(int resId, float x, float y, float width, float height, int frameCount) {
        super(resId, x, y, width, height, 10, frameCount);
    }

    @Override
    public void update() {
        super.update();

        elapsedTime += BaseScene.frameTime;
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }

    public float getDamage() {
        return damage;
    }

    @Override
    public boolean checkCast() {
        if(elapsedTime < interval) {
            return false;
        }
        elapsedTime -= interval;
        return true;
    }
}
