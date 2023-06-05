package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Item;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.CollisionObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Recyclable;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Sprite;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Player;

public class Item extends Sprite implements CollisionObject, Recyclable {
    protected RectF collisionRect = new RectF();

    public Item(int resId, float x, float y, float width, float height) {
        super(resId, x, y, width, height);
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }

    @Override
    public void setCollisionRect() {

    }

    public void onCollision(Player player) {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, rect, null);
    }

    @Override
    public void onRecycle() {

    }
}
