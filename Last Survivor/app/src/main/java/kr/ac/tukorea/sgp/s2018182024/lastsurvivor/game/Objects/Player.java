package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import java.util.List;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.AnimationSprite;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.CollisionObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Gauge;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.FLOAT;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Magic.MagicManager;

public class Player extends AnimationSprite implements CollisionObject {
    public interface Listener {
        public void onLevelUp();
    }

    private static final String TAG = Player.class.getSimpleName();
    private static final float PLAYER_WIDTH = 35 * Metrics.bitmapRatio;
    private static final float PLAYER_HEIGHT = 64 * Metrics.bitmapRatio;
    public static final float SPEED = 5.f;
    private Listener listener;
    private float hp, maxHp;
    private float exp, maxExp;
    private int level;
    private float invincibleTime, maxInvincibleTime;
    private boolean isInvincible;
    private RectF collisionRect = new RectF();
    private FLOAT power = new FLOAT();
    private FLOAT damageAmp = new FLOAT();
    private Gauge hpGauge = new Gauge(0.2f, R.color.player_hp_gauge_fg, R.color.player_hp_gauge_bg);
    private Gauge expGauge = new Gauge(0.2f, R.color.player_exp_gauge_fg, R.color.player_exp_gauge_bg);

    private static final int[] resId = {
            R.mipmap.player_idle, R.mipmap.player_move_updown, R.mipmap.player_move_left,
            R.mipmap.player_move_on_left, R.mipmap.player_move_right, R.mipmap.player_move_on_right
    };

    public Player(Listener listener) {
        super(resId[0], Metrics.gameWidth / 2, Metrics.gameHeight / 2,
                PLAYER_WIDTH, PLAYER_HEIGHT, 6, 3);
        this.listener = listener;

        hp = maxHp = 100.0f;
        maxInvincibleTime = 1.0f;
        isInvincible = false;
        exp = 0.0f;
        maxExp = 20.0f;
        level = 1;

        power.set(30.0f);
        damageAmp.set(1.0f);

        setCollisionRect();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        drawGauge(canvas, hpGauge, 0.0f, 0.3f, hp, maxHp);
        drawGauge(canvas, expGauge, 0.0f, Metrics.gameHeight - 0.1f, exp, maxExp);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    public void drawGauge(Canvas canvas, Gauge gauge, float x, float y,
                          float amount, float maxAmount) {
        canvas.save();
        canvas.translate(x, y);
        canvas.scale(Metrics.gameWidth, 1.0f);
        gauge.draw(canvas, amount / maxAmount);
        canvas.restore();
    }

    @Override
    public void update() {
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

    public boolean getIsInvincible() {
        return isInvincible;
    }

    public float getPower() {
        return power.get();
    }

    public float getDamageAmp() {
        return damageAmp.get();
    }

    public int getLevel() {
        return level;
    }

    public boolean decreaseHp(float damage) {
        hp -= damage;
        isInvincible = true;
        if(hp <= 0)
            return true;
        return false;
    }

    public void increaseHp(float amount) {
        hp += amount;
        if(hp > maxHp) {
            hp = maxHp;
        }
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
            listener.onLevelUp();
        }
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
