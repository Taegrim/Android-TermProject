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
    protected static Player player;
    protected MagicType magicType;
    protected float dx, dy, angle, speed;
    protected ArrayList<Integer> enemyIndices = new ArrayList<>();

    public enum AttackType {
        NORMAL, PENETRATION, COUNT
    }

    public enum MagicType {
        BULLET, THUNDER, CYCLONE, SATELLITE, METEOR, BLIZZARD, COUNT;

        float coefficient() { return coefficients[this.ordinal()]; }
        float increaseRate() { return increaseRates[this.ordinal()]; }
        float damage() { return damages[this.ordinal()]; }
        int count() { return counts[this.ordinal()]; }
        int level() { return levels[this.ordinal()]; }
        int maxLevel() { return maxLevels[this.ordinal()]; }
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

        static final float[] coefficients = { 2.0f, 5.8f, 1.7f, 1.2f, 7.0f, 3.6f }; // 데미지 계수
        static float[] increaseRates = { 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f };
        static float[] damages = { 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f };  // 결과 데미지, 초기 값으로 1.0 선언
        static int[] counts = { 1, 1, 1, 1, 1, 20 };
        static int[] levels = { 1, 1, 1, 1, 1, 1 };
        static int[] maxLevels = { 7, 7, 7, 7, 7, 7 };
        static final float[] defaultCooldowns = { 0.75f, 2.2f, 3.5f, 0.0f, 4.5f, 6.0f };
        static FLOAT[] cooldowns = new FLOAT[COUNT.ordinal()];
        static AttackType[] attackTypes = {
                AttackType.NORMAL,
                AttackType.PENETRATION,
                AttackType.PENETRATION,
                AttackType.PENETRATION,
                AttackType.PENETRATION,
                AttackType.PENETRATION,
        };
    }


    public MagicManager(){}

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setMagicType(MagicType type) {
        magicType = type;
        magicType.calculateDamage(player);
        magicType.setCooldown(magicType.defaultCooldown());
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

    public static void onLevelUp(MagicType type, Player player) {
        int magicId = type.ordinal();
        int level = MagicType.levels[magicId] + 1;
        int maxLevel = MagicType.maxLevels[magicId];
        if(level > maxLevel) {
            MagicType.levels[magicId] = maxLevel;
            return ;
        }
        MagicType.levels[magicId] = level;

        switch(type) {
            case BULLET:
                switch(level) {
                    case 2:
                    case 5:
                        MagicType.counts[magicId] += 1;
                        break;
                    case 3:
                    case 6:
                        changeIncreaseRate(type, player, 0.5f);
                        break;
                    case 4:
                    case 7:
                        Log.d(TAG, "마력탄 특성 습득!");
                        break;
                }
                break;

            case THUNDER:
                switch (level) {
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        MagicType.counts[magicId] += 1;
                        changeIncreaseRate(type, player, 0.2f);
                        break;
                    case 7:
                        Log.d(TAG, "낙뢰 특성 습득!");
                        break;
                }
                break;
            case CYCLONE:
                switch(level) {
                    case 2:
                    case 5:
                        MagicType.counts[magicId] += 1;
                        break;
                    case 3:
                    case 6:
                        changeIncreaseRate(type, player, 0.5f);
                        break;
                    case 4:
                        Log.d(TAG, "돌개바람 4레벨 달성!");
                        break;
                    case 7:
                        Log.d(TAG, "마력탄 특성 습득!");
                        break;
                }
                break;
            case SATELLITE:
                switch (level) {
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        changeIncreaseRate(type, player, 0.2f);
                        MagicType.counts[magicId] += 1;
                        break;
                    case 7:
                        Log.d(TAG, "위성 특성 습득!");
                        break;
                }
                break;
            case METEOR:
                switch(level) {
                    case 2:
                    case 5:
                        MagicType.counts[magicId] += 1;
                        break;
                    case 3:
                    case 6:
                        changeIncreaseRate(type, player, 0.5f);
                    case 4:
                        Log.d(TAG, "메테오 4레벨 달성!");
                        break;
                    case 7:
                        Log.d(TAG, "메테오 특성 습득!");
                        break;
                }
                break;
        }
    }

    // 적 타겟팅 함수
    protected float getDistance(float x1, float y1, float x2, float y2) {
        return (float) (Math.pow((x2 - x1), 2.0) + Math.pow((y2 - y1), 2.0));
    }

    protected boolean getCloseEnemyDir(ArrayList<GameObject> enemies) {
        for(int i = enemies.size() - 1; i >= 0; --i) {
            Enemy enemy = (Enemy) enemies.get(i);

            // 화면 내에 있는 적들만 처리
            if(Metrics.isInGameView(enemy.getX(), enemy.getY())) {
                enemyIndices.add(i);
            }
        }

        float playerX = player.getX();
        float playerY = player.getY();
        float dist, min = 100000.0f;
        int index = -1;

        for(int i : enemyIndices) {
            if(i >= enemies.size()) continue;

            Enemy enemy = (Enemy) enemies.get(i);

            dist = getDistance(playerX, playerY, enemy.getX(), enemy.getY());
            if(dist < min) {
                min = dist;
                index = i;
            }
        }

        if(-1 == index)
            return false;

        Enemy enemy = (Enemy) enemies.get(index);
        float directionX = enemy.getX() - playerX;
        float directionY = enemy.getY() - playerY;
        double radian = Math.atan2(directionY, directionX);
        this.dx = (float) (speed * Math.cos(radian));
        this.dy = (float) (speed * Math.sin(radian));
        this.angle = (float) Math.toDegrees(radian);

        return true;
    }

    protected void getRandomDir() {
        int sign = (r.nextInt(2) == 0) ? 1 : -1;
        float radian = r.nextFloat() * 3.14f * sign;
        this.dx = (float) (speed * Math.cos(radian));
        this.dy = (float) (speed * Math.sin(radian));
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

    protected void setRandomPosition() {
        float offset = 3.0f;
        this.dx = r.nextFloat() * (Metrics.gameWidth - offset) + offset / 2.0f;
        this.dy = r.nextFloat() * (Metrics.gameHeight - offset) + offset / 2.0f;
    }

    // 화면내 적이 없다면 false, 하나라도 있다면 true
    protected boolean isEvenOneOnScreen(ArrayList<GameObject> objects) {
        for(int i = objects.size() - 1; i >= 0; --i) {
            Enemy enemy = (Enemy) objects.get(i);
            if(Metrics.isInGameView(enemy.getX(), enemy.getY())) {
                return true;
            }
        }
        return false;
    }
}
