package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Enemy.Enemy;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.FLOAT;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Generator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Player;

public class MagicManager extends Generator {
    private static final String TAG = MagicManager.class.getSimpleName();
    protected Player player;
    protected MagicType magicType;
    protected float dx, dy, angle, speed;
    protected ArrayList<Integer> enemyIndices = new ArrayList<>();

    public enum AttackType {
        NORMAL, PENETRATION, COUNT
    }

    public enum MagicType {
        BULLET, THUNDER, COUNT;

        float coefficient() { return coefficients[this.ordinal()]; }
        float increaseRate() { return increaseRates[this.ordinal()]; }
        float damage() { return damages[this.ordinal()]; }
        int count() { return counts[this.ordinal()]; }
        float defaultCooldown() { return defaultCooldowns[this.ordinal()]; }
        float cooldown() { return cooldowns[this.ordinal()].get(); }
        AttackType attackType() { return attackTypes[this.ordinal()]; }

        void setCooldown(float value) {
            if(cooldowns[this.ordinal()] == null) {
                cooldowns[this.ordinal()] = new FLOAT();
            }
            cooldowns[this.ordinal()].set(value);
        }

        void calculateDamage(Player player) {
            damages[this.ordinal()] = player.getPower() * this.coefficient() *
                    this.increaseRate() * player.getDamageAmp();
        }

        static final float[] coefficients = { 2.0f, 1.0f };
        static float[] increaseRates = { 1.0f, 1.0f };
        static float[] damages = { 1.0f, 1.0f };
        static int[] counts = { 1, 1 };
        static final float[] defaultCooldowns = { 0.75f, 2.2f };
        static FLOAT[] cooldowns = new FLOAT[COUNT.ordinal()];
        static AttackType[] attackTypes = {
                AttackType.NORMAL,
                AttackType.PENETRATION };
    }


    public MagicManager(){}

    public void setPlayer(Player player) {
        this.player = player;
    }

    // 스킬 피해 증가량 변경 set/change
    public static void setIncreaseRate(MagicType type, Player player, float value) {
        MagicType.increaseRates[type.ordinal()] = value;
        type.calculateDamage(player);
    }

    public static void changeIncreaseRate(MagicType type, Player player, float value) {
        setIncreaseRate(type, player, type.increaseRate() + value);
    }

    public static void setMagicCount(MagicType type, int value) {
        MagicType.counts[type.ordinal()] = value;
    }

    public static void changeMagicCount(MagicType type, int value) {
        setMagicCount(type, type.count() + value);
    }

    public static void increaseCooldownRatio(MagicType type, float value) {
        MagicType.cooldowns[type.ordinal()].increaseRatio(value);
        Log.d(TAG, "cooldown : " + type.cooldown());
    }


    // 적 타겟팅 함수
    protected float getDistance(float x1, float y1, float x2, float y2) {
        return (float) (Math.pow((x2 - x1), 2.0) + Math.pow((y2 - y1), 2.0));
    }

    protected void getCloseEnemyDir(ArrayList<GameObject> enemies) {
        float playerX = player.getX();
        float playerY = player.getY();
        float dist, min = 100000.0f;
        int index = 0;

        for(int i = enemies.size() - 1; i >= 0; --i) {
            Enemy enemy = (Enemy) enemies.get(i);

            dist = getDistance(playerX, playerY, enemy.getX(), enemy.getY());
            if(dist < min) {
                min = dist;
                index = i;
            }
        }

        Enemy enemy = (Enemy) enemies.get(index);
        float directionX = enemy.getX() - playerX;
        float directionY = enemy.getY() - playerY;
        double radian = Math.atan2(directionY, directionX);
        this.dx = (float) (speed * Math.cos(radian));
        this.dy = (float) (speed * Math.sin(radian));
        this.angle = (float) Math.toDegrees(radian);
    }

    protected void getRandomTargetEnemy(ArrayList<GameObject> enemies) {
        if(AttackType.PENETRATION == magicType.attackType()) {
            for(int i = enemies.size() - 1; i >= 0; --i) {
                Enemy enemy = (Enemy) enemies.get(i);
                enemy.setCollisionFlag(magicType, false);

                // 화면 내에 있는 적들만 처리
                if(Metrics.isInGameView(enemy.getX(), enemy.getY(), -1.0f, -2.0f)) {
                    enemyIndices.add(i);
                }
            }
        }
        else {
            for(int i = enemies.size() - 1; i >= 0; --i) {
                Enemy enemy = (Enemy) enemies.get(i);

                // 화면 내에 있는 적들만 처리
                if(Metrics.isInGameView(enemy.getX(), enemy.getY(), -1.0f, -2.0f)) {
                    enemyIndices.add(i);
                }
            }
        }

        int size = enemyIndices.size();
        int count = magicType.count();
        // 화면 내 적이 생성 수보다 많다면 랜덤 뽑기, 적다면 화면 내 모든 적에게 처리
        if(size > count) {
            for(int i = 0; i < count; ++i) {
                int randomIndex = r.nextInt(size - i) + i;
                int enemyIndex = enemyIndices.get(randomIndex);
                enemyIndices.set(randomIndex, enemyIndices.get(i));
                enemyIndices.set(i, enemyIndex);
            }

            for(int i = size - 1; i >= count; --i) {
                enemyIndices.remove(i);
            }
        }
    }
}
