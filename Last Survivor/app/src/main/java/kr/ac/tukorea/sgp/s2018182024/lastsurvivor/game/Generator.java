package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameObject;

public class Generator implements GameObject {
    private static final String TAG = Generator.class.getSimpleName();
    protected int generation_number;
    protected float generation_interval;
    protected Random r = new Random();
    private ArrayList<Generator> generators = new ArrayList<>();

    public void addGenerator(Generator gen) {
        generators.add(gen);
        Log.d(TAG, "Generator added! : " + gen.getClass().getName());
    }

    public void removeGenerator(Generator gen) {
        generators.remove(gen);
        Log.d(TAG, "Generator removed! : " + gen.getClass().getName());
    }

    @Override
    public void update() {
        for(Generator gen : generators) {
            gen.update();
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
