package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Item;

import java.util.Random;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.RecycleBin;

public class ExpOrb extends Item {
    private float exp;
    private Type type;

    public enum Type {
        SMALL, MEDIUM, LARGE;

        int resId() { return resIds[this.ordinal()]; }
        float size() { return sizes[this.ordinal()]; }
        float offset() { return offsets[this.ordinal()]; }
        float exp() { return exps[this.ordinal()]; }

        static int[] resIds = {
                R.mipmap.exp_orb_small,
                R.mipmap.exp_orb_medium,
                R.mipmap.exp_orb_large
        };
        static float[] sizes = { 1.0f, 1.2f, 1.5f };
        static float[] offsets = { 0.22f, 0.37f, 0.55f };
        static float[] exps = { 10.0f, 20.0f, 40.0f };
        static Type random(Random random) { return Type.values()[random.nextInt(3)]; }
    }

    public static ExpOrb get(Type type, float x, float y) {
        ExpOrb orb = (ExpOrb) RecycleBin.get(ExpOrb.class);
        if(orb == null) {
            return new ExpOrb(type, x, y);
        }
        orb.x = x;
        orb.y = y;
        orb.init(type);
        return orb;
    }

    private ExpOrb(Type type, float x, float y) {
        super(type.resId(), x, y, type.size(), type.size());
        init(type);
    }

    public void init(Type type) {
        this.type = type;
        width = type.size();
        height = type.size();
        setBitmapResource(type.resId());
        fixRect();
        setCollisionRect();
    }

    @Override
    public void setCollisionRect() {
        collisionRect.set(rect);
        collisionRect.inset(type.offset(), type.offset());
    }

    public float getExp() {
        return type.exp();
    }
}
