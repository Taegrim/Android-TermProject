package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Enemy;

import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.AnimationSprite;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.CollisionHelper;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.CollisionObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Recyclable;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.CollisionChecker;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Magic.Magic;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Magic.MagicManager;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Scene.MainScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Player;

public class Enemy extends AnimationSprite implements CollisionObject, Recyclable {
    private float dx, dy;
    private float tx, ty;
    protected float speed;
    private int frames;
    private static final int UPDATE_FRAME = 30;
    private Player player;
    protected float damage;
    protected float hp, maxHp, exp;
    protected RectF collisionRect = new RectF();
    protected ArrayList<Magic> magics = new ArrayList<>();
    protected HashMap<Magic, Float> collisionTime = new HashMap<>();

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
        Iterator<Magic> it = magics.iterator();
        while(it.hasNext()) {
            Magic magic = it.next();
            if(magic == null)
                continue;

            if(CollisionHelper.collide(magic, this)) {
                // 지속성 마법과 계속 충돌 중이고, 피해 간격이 지났다면 다시 충돌 할 수 있도록
                // 충돌 목록과, 타이머 목록에서 삭제
                if(MagicManager.AttackType.CONTINUOUS == magic.getAttackType()) {
                    float time = collisionTime.get(magic) + BaseScene.frameTime;
                    if(time >= magic.getMagicType().collisionTime()) {
                        collisionTime.remove(magic);
                        it.remove();
                    }
                    else{
                        collisionTime.put(magic, time);
                    }
                }
            }
            else{
                // 충돌이 끝났으면 충돌 목록과 타이머 목록에서 삭제
                if(MagicManager.AttackType.CONTINUOUS == magic.getAttackType()) {
                    collisionTime.remove(magic);
                }
                it.remove();
            }
        }

        frames +=1;
        if(frames == UPDATE_FRAME){
            targetUpdate();
            frames = 0;
        }

        x += dx * BaseScene.frameTime;
        y += dy * BaseScene.frameTime;

        fixRect();
        setCollisionRect();
    }

    // 같은 유형의 마법에 대해선 하나씩만 충돌하도록 함
    public boolean checkCollisionValidation(Magic magic) {
        MagicManager.MagicType type = magic.getMagicType();
        for(int i = magics.size() - 1; i >= 0; --i) {
            Magic collisionMagic = magics.get(i);
            if(collisionMagic == null)
                continue;

            if(type == collisionMagic.getMagicType()) {
                return false;
            }
        }
        magics.add(magic);
        
        // 지속형 마법은 충돌이 시작한 시간을 저장
        if(MagicManager.AttackType.CONTINUOUS == magic.getAttackType()) {
            collisionTime.put(magic, 0.0f);
        }

        return true;
    }

    public void removeCollision(Magic magic) {
        Iterator<Magic> it = magics.iterator();
        while(it.hasNext()) {
            Magic collisionMagic = it.next();
            if(collisionMagic == magic) {
                collisionTime.remove(magic);
                it.remove();
            }
        }
    }

    public float getDamage() {
        return damage;
    }

    public float getExp() {
        return exp;
    }

    public boolean decreaseHp(float damage) {
        if(hp <= 0)
            return false;

        hp -= damage;
        if(hp <= 0)
            return true;
        return false;
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }


    @Override
    protected void fixCollisionRect() {
        setCollisionRect();
    }

    @Override
    public void setCollisionRect() {

    }

    @Override
    public void onRecycle() {

    }
}
