package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Enemy.Enemy;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Generator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Player;

public class MagicGenerator extends Generator {
    private static final String TAG = MagicGenerator.class.getSimpleName();
    protected Player player;
    protected Magic.AttackType attackType;
    protected float damage;
    protected float coefficient, increaseRate;
    protected float dx, dy, angle, speed;
    protected ArrayList<Integer> enemyIndices = new ArrayList<>();

    public MagicGenerator(){}

    public void setPlayer(Player player) {
        this.player = player;
    }

    // 데미지 계산식 = 플레이어 공격력 * 계수 * 피해 증가량 * 플레이어 마법 피해 증폭량
    protected void calculateDamage(Player player) {
        this.damage = player.getPower() * coefficient * increaseRate * player.getDamageAmp();
    }

    // 스킬 피해 증가량 변경 set/change
    public void setIncreaseRate(float value) {
        this.increaseRate = value;
    }

    public void changeIncreaseRate(float value) {
        this.increaseRate += value;
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
        for(int i = enemies.size() - 1; i >= 0; --i) {
            Enemy enemy = (Enemy) enemies.get(i);

            // 화면 내에 있는 적들만 처리
            if(Metrics.isInGameView(enemy.getX(), enemy.getY(), -1.0f, -2.0f)) {
                enemyIndices.add(i);
            }
        }

        int size = enemyIndices.size();
        // 화면 내 적이 생성 수보다 많다면 랜덤 뽑기, 적다면 화면 내 모든 적에게 처리
        if(size > generation_number) {
            for(int i = 0; i < generation_number; ++i) {
                int randomIndex = r.nextInt(size - i) + i;
                int enemyIndex = enemyIndices.get(randomIndex);
                enemyIndices.set(randomIndex, enemyIndices.get(i));
                enemyIndices.set(i, enemyIndex);
            }

            for(int i = size - 1; i >= generation_number; --i) {
                enemyIndices.remove(i);
            }
        }
    }
}
