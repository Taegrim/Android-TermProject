package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework;

import android.graphics.RectF;

public class CollisionChecker {
    public static boolean collide(CollisionObject obj1, CollisionObject obj2){
        RectF r1 = obj1.getCollisionRect();
        RectF r2 = obj2.getCollisionRect();

        if(r1.right < r2. left) return false;
        if(r1.left > r2. right) return false;
        if(r1.bottom < r2.top) return false;
        if(r1.top > r2. bottom) return false;

        return true;
    }
}
