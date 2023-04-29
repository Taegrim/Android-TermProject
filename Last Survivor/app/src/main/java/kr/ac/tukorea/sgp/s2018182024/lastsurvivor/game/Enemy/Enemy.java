package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Enemy;

import android.util.Log;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.AnimationSprite;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.MainScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Player;

public class Enemy extends AnimationSprite {
    private float dx, dy;
    private float tx, ty;
    protected float speed;
    private int frames;
    private static final int UPDATE_FRAME = 30;
    private Player player;

    public Enemy(int resId, float x, float y, float width, float height, float fps, int frameCount) {
        super(resId, x, y, width, height, fps, frameCount);

        MainScene scene = (MainScene) BaseScene.getTopScene();
        player = scene.player;
        targetUpdate();
    }

    private void targetUpdate() {
        tx = player.getX();
        ty = player.getY();
        float directionX = tx - this.x;
        float directionY = ty - this.y;
        double radian = Math.atan2(directionY, directionX);
        dx = (float) (speed * Math.cos(radian));
        dy = (float) (speed * Math.sin(radian));
    }

    @Override
    public void update() {
        frames +=1;
        if(frames == UPDATE_FRAME){
            targetUpdate();
            frames = 0;
        }

        float time = BaseScene.frameTime;
        x += dx * time;
        y += dy * time;

        fixRect();
    }
}
