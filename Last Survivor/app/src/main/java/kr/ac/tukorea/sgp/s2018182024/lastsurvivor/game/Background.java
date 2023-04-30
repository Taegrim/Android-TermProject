package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Sprite;

public class Background extends Sprite {
    public Background(int resId){
        super(resId, Metrics.gameWidth / 2, Metrics.gameHeight / 2,
                Metrics.gameWidth, Metrics.gameHeight);
        setSize(Metrics.gameWidth, height);
    }
}
