package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import android.graphics.Canvas;
import android.util.Log;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BitmapPool;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.RecycleBin;

public class Blizzard extends FallingMagic {
    private static final String TAG = Blizzard.class.getSimpleName();
    private static final float WIDTH = 1.0f;
    private static final float HEIGHT = 3.0f;
    private static final float PARTICLE_WIDTH = 3.0f;
    private static final int PARTICLE_START_OFFSET = 1;

    private static final float FPS = 7.0f;
    public static final float X_SPEED = 5.0f;
    public static final float Y_SPEED = 10.0f;

    private static final int blizzardResIds[] = {
            R.mipmap.blizzard_a01, R.mipmap.blizzard_a02, R.mipmap.blizzard_a03, R.mipmap.blizzard_a04,
            R.mipmap.blizzard_a05, R.mipmap.blizzard_a06, R.mipmap.blizzard_a07, R.mipmap.blizzard_a08,
            R.mipmap.blizzard_a09, R.mipmap.blizzard_a10, R.mipmap.blizzard_a11, R.mipmap.transparent_image
    };

    private static final int iceResIds[] = {
            R.mipmap.blizzard_particle_01, R.mipmap.blizzard_particle_02, R.mipmap.blizzard_particle_03,
            R.mipmap.blizzard_particle_04, R.mipmap.blizzard_particle_05, R.mipmap.blizzard_particle_06,
            R.mipmap.blizzard_particle_07,
    };

    public static Blizzard get(MagicManager.MagicType type, float x, float y) {
        Blizzard blizzard = (Blizzard) RecycleBin.get(Blizzard.class);
        if(blizzard == null) {
            return new Blizzard(type, x, y);
        }
        blizzard.x = x;
        blizzard.y = y;
        blizzard.setBitmapResource(blizzardResIds[0]);
        blizzard.init();
        return blizzard;
    }

    private Blizzard(MagicManager.MagicType type, float x, float y) {
        super(blizzardResIds[0], x, y, WIDTH, HEIGHT);

        setFallingValue();

        magicType = type;
        init();
    }

    private void init() {
        this.createdTime = System.currentTimeMillis();
        this.damage = magicType.damage();
        this.attackType = magicType.attackType();

        particleBitmap = BitmapPool.get(blizzardResIds[0]);

        setSize(WIDTH, HEIGHT);
        fixRect();
    }

    private void setFallingValue() {
        magicResIds = blizzardResIds;
        particleResIds = iceResIds;
        xSpeed = X_SPEED;
        ySpeed = Y_SPEED;
        fps = FPS;
        particleSize = PARTICLE_WIDTH;
        particleStartOffset = PARTICLE_START_OFFSET;
    }

    @Override
    protected void resizeMagicByIndex(int index) {
        float xRatio = BaseScene.frameTime * X_SPEED;
        float yRatio = BaseScene.frameTime * Y_SPEED;
        float width = WIDTH * (1 - xRatio);
        float height = HEIGHT * (1 - yRatio);

        setSize(width, height);
    }
}
