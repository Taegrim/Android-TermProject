package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import android.util.Log;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.RecycleBin;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Player;

public class Satellite extends Magic {
    private static final String TAG = Satellite.class.getSimpleName();
    private static final float WIDTH = 1.0f;
    private static final float HEIGHT = 1.0f;
    private static Player player;
    private float angle, radius, speed;

    public static Satellite get(MagicManager.MagicType type, float x, float y, float angle, float radius,
                                float damage, float speed, MagicManager.AttackType attackType)
    {
        Satellite satellite = (Satellite) RecycleBin.get(Satellite.class);
        if(satellite == null) {
            return new Satellite(type, x, y, angle, radius, damage, speed, attackType);
        }
        satellite.x = x;
        satellite.y = y;
        satellite.init(angle, radius, damage, speed, attackType);
        return satellite;
    }

    public Satellite(MagicManager.MagicType type, float x, float y, float angle, float radius,
                     float damage, float speed, MagicManager.AttackType attackType)
    {
        super(R.mipmap.satellite, x, y, WIDTH, HEIGHT);
        magicType = type;
        init(angle, radius, damage, speed, attackType);
    }

    public void init(float angle, float radius, float damage, float speed,
                     MagicManager.AttackType attackType)
    {
        this.angle = angle;
        this.radius = radius;
        this.damage = damage;
        this.attackType = attackType;
        this.speed = speed;
        fixRect();
        setCollisionRect();
    }

    public static void setPlayer(Player player) {
        if(Satellite.player == null) {
            Satellite.player = player;
        }
    }

    @Override
    public void update() {
        angle = (angle + speed) % 360;
        double radian = Math.toRadians(this.angle);
        x = player.getX() + (float) (radius * Math.cos(radian));
        y = player.getY() + (float) (radius * Math.sin(radian));

        fixRect();
        setCollisionRect();

        super.update();
    }

    @Override
    public void setCollisionRect() {
        collisionRect.set(rect);
        collisionRect.inset(0.3f, 0.3f);
    }
}
