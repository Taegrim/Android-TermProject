package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game;

import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.CollisionHelper;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.CollisionObject;

public class PlayerCollisionHelper {

    public static void explodeCollision(Player player, CollisionObject object,
                                        ArrayList<CollisionObject> targets)
    {
        for(int i = targets.size() - 1; i >= 0; --i) {
            if(targets.get(i) == targets.get(i)) continue;

            CollisionObject target = targets.get(i);
            if(CollisionHelper.collide(object, target)) {
                target.onCollision(player);
            }
        }
    }
}
