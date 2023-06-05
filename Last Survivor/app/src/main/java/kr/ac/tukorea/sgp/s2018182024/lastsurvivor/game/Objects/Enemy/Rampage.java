package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Enemy;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.RecycleBin;

public class Rampage extends Enemy {
    private static final float WIDTH = 1.0f;
    private static final float HEIGHT = 1.1f;

    public static Rampage get(float x, float y, int level) {
        Rampage rampage = (Rampage) RecycleBin.get(Rampage.class);
        if(rampage == null) {
            return new Rampage(x, y, level);
        }
        rampage.x = x;
        rampage.y = y;
        rampage.init(level);
        return rampage;
    }

    private Rampage(float x, float y, int level) {
        super(R.mipmap.rampage, x, y, WIDTH, HEIGHT, 5, 4);
        speed = 1.2f;
        init(level);
    }

    private void init(int level) {
        hp = maxHp = (level + 1) * 60.0f + 30.0f;
        damage = 15.0f;
        exp = (level + 1) * 3.5f;
        fixRect();
        setCollisionRect();
    }

    @Override
    public void onRecycle() {

    }

    @Override
    public void setCollisionRect() {
        collisionRect.set(rect);
        collisionRect.inset(0.15f, 0.15f);
    }
}
