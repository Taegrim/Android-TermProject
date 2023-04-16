package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.AnimationSprite;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Metrics;

public class Enemy extends AnimationSprite {

    public Enemy(float sizeX, float sizeY) {
        super(R.mipmap.enemy1_move, Metrics.gameWidth / 2 + 1.0f, Metrics.gameHeight / 2,
                sizeX, sizeY, 5, 4);
    }


}
