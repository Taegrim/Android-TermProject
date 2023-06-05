package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Magic;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.RecycleBin;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Player;

public class Satellite extends Magic {
    private static final String TAG = Satellite.class.getSimpleName();
    private static final float WIDTH = 1.0f;
    private static final float HEIGHT = 1.0f;
    private static Player player;
    private float angle, radius, speed;

    public static Satellite get(MagicManager.MagicType type, float x, float y, float angle, float radius,
                                float speed)
    {
        Satellite satellite = (Satellite) RecycleBin.get(Satellite.class);
        if(satellite == null) {
            return new Satellite(type, x, y, angle, radius, speed);
        }
        satellite.x = x;
        satellite.y = y;
        satellite.init(angle, radius, speed);
        return satellite;
    }

    public Satellite(MagicManager.MagicType type, float x, float y, float angle, float radius,
                     float speed)
    {
        super(R.mipmap.satellite, x, y, WIDTH, HEIGHT);
        magicType = type;
        init(angle, radius, speed);
    }

    public void init(float angle, float radius, float speed)
    {
        this.angle = angle;
        this.radius = radius;
        this.damage = magicType.damage();
        this.attackType = magicType.attackType();
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
        angle = (angle + speed * BaseScene.frameTime) % 360;
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
