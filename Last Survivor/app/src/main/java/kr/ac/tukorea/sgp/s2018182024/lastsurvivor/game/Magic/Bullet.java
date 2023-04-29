package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import android.graphics.Canvas;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic.Magic;

public class Bullet extends Magic {
    private static int SPEED = 2;

    public Bullet(int resId, float x, float y, float width, float height) {
        super(resId, x, y, width, height, 1);
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        y += SPEED * BaseScene.frameTime;
        fixRect();

        if(rect.top > Metrics.gameHeight) {
            BaseScene.getTopScene().removeObject(this);
        }
        collisionRect.set(rect);
        collisionRect.inset(0.2f, 0.2f);
    }

}
