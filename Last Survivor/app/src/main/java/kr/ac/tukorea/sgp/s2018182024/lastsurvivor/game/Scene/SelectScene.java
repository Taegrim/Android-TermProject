package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Scene;

import org.xmlpull.v1.XmlPullParser;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;

public class SelectScene extends BaseScene {

    public SelectScene() {
        
    }

    @Override
    protected boolean isTransparent() {
        return true;
    }

    @Override
    public boolean onBackPressed() {
        // 아무 동작 X
        return true;
    }
}
