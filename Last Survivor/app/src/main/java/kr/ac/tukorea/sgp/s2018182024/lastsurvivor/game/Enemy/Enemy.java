package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Enemy;

import android.graphics.RectF;

import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.AnimationSprite;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.CollisionObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Recyclable;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic.MagicManager;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.MainScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Player;

public class Enemy extends AnimationSprite implements CollisionObject, Recyclable {
    private float dx, dy;
    private float tx, ty;
    protected float speed;
    private int frames;
    private static final int UPDATE_FRAME = 30;
    private Player player;
    protected float damage;
    protected float hp, maxHp, exp;
    protected ArrayList<Boolean> collisionFlag = new ArrayList<>();
    protected RectF collisionRect = new RectF();

    public Enemy(int resId, float x, float y, float width, float height, float fps, int frameCount) {
        super(resId, x, y, width, height, fps, frameCount);

        MainScene scene = (MainScene) BaseScene.getTopScene();
        player = scene.player;
        targetUpdate();

        for(int i = 0; i < MagicManager.MagicType.COUNT.ordinal(); ++i) {
            collisionFlag.add(false);
        }
    }

    private void targetUpdate() {
        tx = player.getX();
        ty = player.getY();
        float directionX = tx - this.x;
        float directionY = ty - this.y;
        double radian = Math.atan2(directionY, directionX);
        dx = (float) (speed * Math.cos(radian));
        dy = (float) (speed * Math.sin(radian));
    }

    @Override
    public void update() {
        frames +=1;
        if(frames == UPDATE_FRAME){
            targetUpdate();
            frames = 0;
        }

        x += dx * BaseScene.frameTime;
        y += dy * BaseScene.frameTime;

        fixRect();
        setCollisionRect();
    }

    public float getDamage() {
        return damage;
    }

    public float getExp() {
        return exp;
    }

    public boolean decreaseHp(float damage) {
        if(hp <= 0)
            return false;

        hp -= damage;
        if(hp <= 0)
            return true;
        return false;
    }

    public void setCollisionFlag(MagicManager.MagicType type, boolean value) {
        collisionFlag.set(type.ordinal(), value);
    }

    public boolean getCollisionFlag(MagicManager.MagicType type) {
        return collisionFlag.get(type.ordinal());
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }

    @Override
    public void setCollisionRect() {

    }

    @Override
    public void onRecycle() {

    }
}
