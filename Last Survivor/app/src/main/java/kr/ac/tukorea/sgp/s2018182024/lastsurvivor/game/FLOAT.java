package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game;

public class FLOAT {
    private float value;
    private float ratio = 1.0f;
    private float result;

    public FLOAT() {}

    public FLOAT(float value) {
        this.value = value;
        this.result = value;
    }

    public void set(float value) {
        this.value = value;
        this.result = value;
    }

    public float get() {
        return result;
    }

    public void increaseRatio(float value) {
        ratio += value;
        changeResult();
    }

    public void changeRatio(float value) {
        ratio = value;
        changeResult();
    }

    private void changeResult() {
        result = value * ratio;
    }

}