package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.RecycleBin;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Magic.Particles.Ice;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.MainScene;

public class Blizzard extends FallingMagic {
    private static final String TAG = Blizzard.class.getSimpleName();
    private static final float WIDTH = 1.0f;
    private static final float HEIGHT = 3.0f;
    private static final int PARTICLE_START_OFFSET = 3;

    private static final float FPS = 7.0f;
    public static final float X_SPEED = 5.0f;
    public static final float Y_SPEED = 10.0f;
    private static final int PARTICLE_DURATION = 1000;

    private static final int blizzardResIds[] = {
            R.mipmap.blizzard_a01, R.mipmap.blizzard_a02, R.mipmap.blizzard_a03, R.mipmap.blizzard_a04,
            R.mipmap.blizzard_a05, R.mipmap.blizzard_a06, R.mipmap.blizzard_a07, R.mipmap.blizzard_a08,
            R.mipmap.blizzard_a09, R.mipmap.blizzard_a10, R.mipmap.blizzard_a11, R.mipmap.transparent_image
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

        setSize(WIDTH, HEIGHT);
        fixRect();
    }

    @Override
    protected void createParticle() {
        if(createsParticle) return;

        createsParticle = true;
        MainScene scene = (MainScene) BaseScene.getTopScene();
        scene.addObject(MainScene.Layer.PARTICLE,
                Ice.get(x + (width / 2.0f), y + (height / 2.0f), PARTICLE_DURATION,
                        magicType.damage()));
    }

    private void setFallingValue() {
        magicResIds = blizzardResIds;
        xSpeed = X_SPEED;
        ySpeed = Y_SPEED;
        fps = FPS;
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

    public static float getMoveTime() {
        return (blizzardResIds.length - PARTICLE_START_OFFSET) / FPS;
    }

    public static float getBitmapWidth() {
        return WIDTH;
    }

    public static float getBitmapHeight() {
        return HEIGHT;
    }
}
