package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.AnimationSprite;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.CollisionObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic.BulletGenerator;

public class Player extends AnimationSprite implements CollisionObject {
    private static final String TAG = Player.class.getSimpleName();
    private static final float PLAYER_WIDTH = 35 * Metrics.bitmapRatio;
    private static final float PLAYER_HEIGHT = 64 * Metrics.bitmapRatio;
    private static final float SPEED = 5.f;

    private int dir;
    private float tx, ty;
    private float dx, dy;
    private float hp, maxHp;
    private float exp, maxExp;
    private int level;
    private float invincibleTime, maxInvincibleTime;
    private boolean isInvincible;
    private RectF collisionRect = new RectF();

    private static final int[] resId = {
            R.mipmap.player_idle, R.mipmap.player_move_updown, R.mipmap.player_move_left,
            R.mipmap.player_move_on_left, R.mipmap.player_move_right, R.mipmap.player_move_on_right
    };

    public Player() {
        super(resId[0], Metrics.gameWidth / 2, Metrics.gameHeight / 2,
                PLAYER_WIDTH, PLAYER_HEIGHT, 6, 3);
        dir = 0;
        tx = x;
        ty = y;
        dx = dy = 0;
        hp = maxHp = 100.0f;
        maxInvincibleTime = 0.5f;
        isInvincible = false;
        exp = 0.0f;
        maxExp = 20.0f;
        level = 1;

        setCollisionRect();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public void changeResource() {
        dir = (dir + 1) % resId.length;
        super.changeResource(resId[dir]);
    }

    public void setTargetPosition(float tx, float ty) {
        this.tx = tx;
        this.ty = ty;
        float dx = tx - this.x;
        float dy = ty - this.y;
        double radian = Math.atan2(dy, dx);
        this.dx = (float) (SPEED * Math.cos(radian));
        this.dy = (float) (SPEED * Math.sin(radian));
    }

    @Override
    public void update() {
        x += dx * BaseScene.frameTime;
        if((dx > 0 && x > tx) || (dx < 0 & x < tx)){
            x = tx;
            dx = 0;
        }
        y += dy * BaseScene.frameTime;
        if((dy > 0 && y > ty) || (dy < 0 && y < ty)){
            y = ty;
            dy = 0;
        }

        // 최대 무적 시간이 0이 아닐경우 무적상태에 들어 갔음을 확인
        // 타이머를 증가시켜 일정 시간이 지나면
        if(isInvincible) {
            invincibleTime += BaseScene.frameTime;

            if(invincibleTime > maxInvincibleTime) {
                invincibleTime = 0.0f;
                isInvincible = false;
            }
        }

        fixRect();
        setCollisionRect();
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public boolean getIsInvincible() {
        return isInvincible;
    }

    public boolean decreaseHp(float damage) {
        hp -= damage;
        isInvincible = true;
        if(hp <= 0)
            return true;
        return false;
    }

    public void increaseExp(float exp) {
        this.exp += exp;

        Log.d(TAG, "level : " + this.level + " exp : " + this.exp +
                " maxExp : " + this.maxExp);
        // 레벨업 가능할 때까지 반복
        while(this.exp >= this.maxExp) {
            Log.d(TAG, "Level Up!");
            ++this.level;
            this.exp -= maxExp;
            this.maxExp = (float) Math.floor(this.maxExp * 1.5f);
            onLevelUp();
        }
    }

    public void onLevelUp() {
        // 레벨업 했을 때 하는 처리
    }

    public void setCollisionRect() {
        collisionRect.set(rect);
        collisionRect.inset(0.1f, 0.1f);
    }
    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }
}
