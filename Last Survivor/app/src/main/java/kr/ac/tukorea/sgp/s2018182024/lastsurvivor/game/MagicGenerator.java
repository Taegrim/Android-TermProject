package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameObject;

public class MagicGenerator implements GameObject {
    private static final String TAG = MagicGenerator.class.getSimpleName();

    @Override
    public void update() {
        MainScene scene = (MainScene) BaseScene.getTopScene();
        ArrayList<GameObject> arr = scene.getAllObjects();
        for(GameObject obj1 : arr){
            if(!(obj1 instanceof Magic)) continue;

            MagicObject magic = (MagicObject) obj1;
            if(magic.checkCast()) {
                for(GameObject obj2 : arr){
                    if(!(obj2 instanceof Player)) continue;

                    Player player = (Player) obj2;
                    scene.addObject(new Bullet(R.mipmap.bullet, player.getX(), player.getY(),
                            1.f, 1.f));
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
