package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Item;

import android.graphics.RectF;

import java.util.Random;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.EventCollision;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.RecycleBin;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Player;

public class ExpOrb extends Item implements EventCollision {
    private static final float SPEED = 6.0f;
    private static final float RETARGET_TIME = 0.15f;
    private Player player;
    private float exp;
    private Type type;
    private RectF eventCollision = new RectF();
    private float dx, dy;
    private boolean isAbsorption;
    private float absorptionRange;
    private float time;

    public enum Type {
        SMALL, MEDIUM, LARGE;

        int resId() { return resIds[this.ordinal()]; }
        float size() { return sizes[this.ordinal()]; }
        float offset() { return offsets[this.ordinal()]; }
        float exp() { return exps[this.ordinal()]; }
        int rate() { return rates[this.ordinal()]; }

        static final int[] resIds = {
                R.mipmap.exp_orb_small,
                R.mipmap.exp_orb_medium,
                R.mipmap.exp_orb_large
        };
        static final float[] sizes = { 1.0f, 1.2f, 1.5f };
        static final float[] offsets = { 0.22f, 0.37f, 0.55f };
        static final float[] exps = { 10.0f, 20.0f, 40.0f };
        static final int[] rates = { 60, 30, 10 };

        static Type random(Random random) {
            int random_num = random.nextInt(100);
            if(0 <= random_num && random_num < Type.SMALL.rate()) {
                return Type.SMALL;
            }
            else if(Type.SMALL.rate() <= random_num &&
                    random_num < Type.SMALL.rate() + Type.MEDIUM.rate())
            {
                return Type.MEDIUM;
            }
            else {
                return Type.LARGE;
            }
        }
    }

    public static ExpOrb get(Type type, float x, float y, float absorptionRange) {
        ExpOrb orb = (ExpOrb) RecycleBin.get(ExpOrb.class);
        if(orb == null) {
            return new ExpOrb(type, x, y, absorptionRange);
        }
        orb.x = x;
        orb.y = y;
        orb.init(type, absorptionRange);
        return orb;
    }

    private ExpOrb(Type type, float x, float y, float absorptionRange) {
        super(type.resId(), x, y, type.size(), type.size());
        init(type, absorptionRange);
    }

    public void init(Type type, float absorptionRange) {
        this.type = type;
        width = type.size();
        height = type.size();
        dx = dy = 0.0f;
        isAbsorption = false;
        this.absorptionRange = absorptionRange;
        time = 0;
        setBitmapResource(type.resId());
        fixRect();
        setCollisionRect();
        setEventCollisionRect();
    }

    @Override
    public void update() {
        if(isAbsorption) {
            time += BaseScene.frameTime;

            x += dx * BaseScene.frameTime;
            y += dy * BaseScene.frameTime;
        }

        if(time > RETARGET_TIME) {
            setTarget(player);
            time = 0;
        }

        fixRect();
        setCollisionRect();
    }

    public void setTarget(Player player) {
        float directionX = player.getX() - x;
        float directionY = player.getY() - y;
        double radian = Math.atan2(directionY, directionX);
        this.dx = (float) (SPEED * Math.cos(radian));
        this.dy = (float) (SPEED * Math.sin(radian));
        this.player = player;
    }

    public boolean setAbsorption(boolean value) {
        if(this.isAbsorption != value) {
            this.isAbsorption = value;
            return true;
        }
        return false;
    }

    @Override
    public void onCollision(Player player) {
        player.increaseExp(type.exp());
    }

    @Override
    public RectF getEventCollisionRect() {
        return eventCollision;
    }

    @Override
    protected void fixCollisionRect() {
        setCollisionRect();
        setEventCollisionRect();
    }

    @Override
    public void setCollisionRect() {
        collisionRect.set(rect);
        collisionRect.inset(type.offset(), type.offset());
    }

    public void setEventCollisionRect() {
        eventCollision.set(collisionRect.left - absorptionRange,
                collisionRect.top - absorptionRange,
                collisionRect.right + absorptionRange,
                collisionRect.bottom + absorptionRange);
    }

    public float getExp() {
        return type.exp();
    }
}
