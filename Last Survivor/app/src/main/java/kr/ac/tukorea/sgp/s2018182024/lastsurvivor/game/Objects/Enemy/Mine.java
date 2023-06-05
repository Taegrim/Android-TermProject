package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Enemy;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.RecycleBin;

public class Mine extends Enemy {
    private static final float WIDTH = 1.5f;
    private static final float HEIGHT = 1.7f;

    public static Mine get(float x, float y, int level) {
        Mine mine = (Mine) RecycleBin.get(Mine.class);
        if(mine == null) {
            return new Mine(x, y, level);
        }
        mine.x = x;
        mine.y = y;
        mine.init(level);
        return mine;
    }

    private Mine(float x, float y, int level) {
        super(R.mipmap.mine, x, y, WIDTH, HEIGHT, 5, 4);
        speed = 0.7f;
        init(level);
    }

    private void init(int level) {
        hp = maxHp = (level + 1) * 80.0f + 60.0f;
        damage = 20.0f;
        exp = (level + 1) * 4.0f;
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
