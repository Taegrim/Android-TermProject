package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.BuildConfig;

public class BaseScene {
    private static ArrayList<BaseScene> stack = new ArrayList<>();
    protected ArrayList<GameObject> objects = new ArrayList<>();
    public static float frameTime;
    protected static Handler handler = new Handler();
    private static Paint collisionPaint;

    public static BaseScene getTopScene() {
        int top = stack.size() - 1;
        if(top < 0)
            return null;
        return stack.get(top);
    }

    public static void popAllScene() {
        while (!stack.isEmpty()) {
            BaseScene scene = getTopScene();
            scene.popScene();
        }
    }

    public int getObjectCount() {
        return objects.size();
    }

    public int pushScene() {
        stack.add(this);
        return stack.size();
    }

    public int popScene() {
        stack.remove(this);
        return stack.size();
    }

    public int addObject(GameObject object) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                objects.add(object);
            }
        });
        return objects.size();
    }

    public int removeObject(GameObject object) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                objects.remove(object);

                if(object instanceof Recyclable) {
                    RecycleBin.collect((Recyclable) object);
                }
            }
        });
        return objects.size();
    }

    public ArrayList<GameObject> getAllObjects() {
        return objects;
    }

    public void update(long timeElapsed) {
        frameTime = timeElapsed / 1_000_000_000f;
        for(GameObject object : objects){
            object.update();
        }
    }

    public void draw(Canvas canvas) {
        for(GameObject object : objects){
            object.draw(canvas);
        }

        if(BuildConfig.DEBUG){
            if(collisionPaint == null){
                collisionPaint = new Paint();
                collisionPaint.setStyle(Paint.Style.STROKE);
                collisionPaint.setColor(Color.RED);
            }
            for(GameObject obj : objects){
                if(!(obj instanceof  CollisionObject)) continue;

                RectF rect = ((CollisionObject) obj).getCollisionRect();
                canvas.drawRect(rect, collisionPaint);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
