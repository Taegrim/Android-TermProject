package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Random;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Enemy.MineGenerator;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Enemy.RampageGenerator;

public class Generator implements GameObject {
    private static final String TAG = Generator.class.getSimpleName();
    private final float RAMPAGE_SPAWN_TIME = 10.0f;
    private final float MINE_SPAWN_TIME = 20.0f;
    private boolean isSpawnsRampage = false, isSpawnsMine = false;

    protected float time;
    protected int generation_number;
    protected float generation_interval;
    protected static Random r;
    private ArrayList<Generator> generators = new ArrayList<>();
    protected static GenType genType;
    protected enum GenType {
        SWAM, RAMPAGE, MINE, BULLET, THUNDER, CYCLONE, SATELLITE, METEOR, BLIZZARD, COUNT
    }

    public static void setRandom(Random random) {
        Generator.r = random;
    }

    public void addGenerator(Generator gen) {
        generators.add(gen);
    }

    private void removeGenerator(GenType type) {
        for(int i = generators.size() - 1; i >= 0; --i) {
            if(generators.get(i).getGenType() == type) {
                generators.remove(i);
                return;
            }
        }
    }

    @Override
    public void update() {
        time += BaseScene.frameTime;
        if(time > MINE_SPAWN_TIME && !isSpawnsMine) {
            generators.add(new MineGenerator());
            isSpawnsMine = true;
            removeGenerator(GenType.RAMPAGE);
        }
        else if(time > RAMPAGE_SPAWN_TIME && !isSpawnsRampage) {
            generators.add(new RampageGenerator());
            isSpawnsRampage = true;
            removeGenerator(GenType.SWAM);
        }

        for(int i = generators.size() - 1; i >= 0; --i) {
            generators.get(i).update();
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void move(float x, float y) {

    }

    @Override
    public float getX() {
        return 0;
    }

    @Override
    public float getY() {
        return 0;
    }

    protected GenType getGenType() {
        return genType;
    }
}
