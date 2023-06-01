package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework;

import android.graphics.RectF;

public interface CollisionObject {
    public RectF getCollisionRect();
    public void setCollisionRect();

    public void onCollision(CollisionObject object);
}
