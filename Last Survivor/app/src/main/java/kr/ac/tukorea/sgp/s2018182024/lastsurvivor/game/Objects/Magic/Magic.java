package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Magic;

import android.graphics.RectF;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.CollisionObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Recyclable;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Sprite;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Scene.MainScene;

public class Magic extends Sprite implements CollisionObject, Recyclable {
    protected float damage;
    protected MagicManager.AttackType attackType;
    protected MagicManager.MagicType magicType;
    protected RectF collisionRect = new RectF();
    protected boolean createsParticle = false;

    public Magic(int resId, float x, float y, float width, float height) {
        super(resId, x, y, width, height);
    }

    protected void createParticle() {
        
    }

    public float getDamage() {
        return damage;
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }

    @Override
    protected void fixCollisionRect() {
        setCollisionRect();
    }

    @Override
    public void setCollisionRect() {

    }

    public MagicManager.AttackType getAttackType() {
        return this.attackType;
    }

    public MagicManager.MagicType getMagicType() {
        return this.magicType;
    }

    @Override
    public void onRecycle() {
        createsParticle = false;
    }
}
