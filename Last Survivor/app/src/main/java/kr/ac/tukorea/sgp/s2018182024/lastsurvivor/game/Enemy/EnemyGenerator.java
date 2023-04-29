package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Enemy;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameObject;

public class EnemyGenerator implements GameObject {
    private static final String TAG = EnemyGenerator.class.getSimpleName();
    protected int generation_number;
    protected float generation_interval;
    private ArrayList<EnemyGenerator> generators = new ArrayList<>();

    public void addGenerator(EnemyGenerator gen) {
        generators.add(gen);
        Log.d(TAG, "Generator added! : " + gen.getClass().getName());
    }

    public void removeGenerator(EnemyGenerator gen) {
        generators.remove(gen);
        Log.d(TAG, "Generator removed! : " + gen.getClass().getName());
    }

    @Override
    public void update() {
        for(EnemyGenerator gen : generators) {
            gen.update();
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
