package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Item;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.CollisionObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.RecycleBin;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.FLOAT;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Player;

public class HealthOrb extends Item {
    private static final float WIDTH = 0.7f;
    private static final float HEIGHT = 0.7f;
    private float restoreAmount;

    public static HealthOrb get(float x, float y, float restoreAmount) {
        HealthOrb orb = (HealthOrb) RecycleBin.get(HealthOrb.class);
        if(orb == null) {
            return new HealthOrb(x, y, restoreAmount);
        }
        orb.x = x;
        orb.y = y;
        orb.init(restoreAmount);
        return orb;
    }

    private HealthOrb(float x, float y, float restoreAmount) {
        super(R.mipmap.health_orb, x, y, WIDTH, HEIGHT);
        init(restoreAmount);
    }

    public void init(float restoreAmount) {
        this.restoreAmount = restoreAmount;
        fixRect();
        setCollisionRect();
    }

    public float getRestoreAmount() {
        return restoreAmount;
    }

    @Override
    public void onCollision(CollisionObject object) {
        Player player = (Player) object;
        player.increaseHp(restoreAmount);
    }

    @Override
    public void setCollisionRect() {
        collisionRect.set(rect);
        collisionRect.inset(0.12f, 0.12f);
    }

}
