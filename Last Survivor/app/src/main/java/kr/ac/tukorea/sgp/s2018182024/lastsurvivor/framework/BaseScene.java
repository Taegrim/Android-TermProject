package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.BuildConfig;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Enemy.Enemy;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Scene.MainScene;

public class BaseScene {
    private static ArrayList<BaseScene> stack = new ArrayList<>();
    protected ArrayList<ArrayList<GameObject>> layers;
    public static float frameTime;
    protected static Handler handler = new Handler();
    private static Paint collisionPaint, debugPaint;
    protected long previousNanos;

    protected <E extends Enum<E>> void initLayers(E enumCount) {
        int layerCount = enumCount.ordinal();
        layers = new ArrayList<>();
        for(int i = 0; i < layerCount; ++i) {
            layers.add(new ArrayList<>());
        }
    }

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
        int count = 0;
        for(ArrayList<GameObject> objects : layers) {
            count += objects.size();
        }
        return count;
    }

    public int pushScene() {
        BaseScene scene = getTopScene();
        if(scene != null) {
            scene.onPause();
        }
        stack.add(this);
        this.onStart();
        return stack.size();
    }

    public void popScene() {
        this.onEnd();
        stack.remove(this);
        BaseScene scene = getTopScene();
        if(scene != null) {
            scene.resumeScene();
            return;
        }

        finishActivity();
    }

    private void finishActivity() {
        GameView.view.getActivity().finish();
    }

    public <E extends Enum<E>> void addObject(E layerIndex, GameObject object, boolean immediate) {
        if(immediate) {
            addObject(layerIndex, object);
            return;
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                addObject(layerIndex, object);
            }
        });
    }

    public <E extends Enum<E>> void addObject(E layerIndex, GameObject object) {
        layers.get(layerIndex.ordinal()).add(object);
    }

    public <E extends Enum<E>> void removeObject(E layerIndex, GameObject object, boolean immediate) {
        if(immediate) {
            removeObject(layerIndex, object);
            return;
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                removeObject(layerIndex, object);
            }
        });
    }

    public <E extends Enum<E>> void removeObject(E layerIndex, GameObject object) {
        boolean removed = getObjects(layerIndex).remove(object);
        if(removed && object instanceof Recyclable) {
            RecycleBin.collect((Recyclable) object);
        }
    }

    public <E extends Enum<E>> ArrayList<GameObject> getObjects(Enum layerIndex) {
        return layers.get(layerIndex.ordinal());
    }

    public void update(long nanos) {
        long prev = previousNanos;
        previousNanos = nanos;
        if(prev == 0) {
            return;
        }
        long timeElapsed = nanos - prev;
        frameTime = timeElapsed / 1_000_000_000f;
        for(ArrayList<GameObject> objects : layers) {
            for(int i = objects.size() - 1; i >= 0; --i){
                objects.get(i).update();
            }
        }
    }

    public void draw(Canvas canvas, int index) {
        BaseScene scene = stack.get(index);
        if(index > 0 && scene.isTransparent()) {
            draw(canvas, index - 1);
        }

        ArrayList<ArrayList<GameObject>> layers = scene.layers;
        for(ArrayList<GameObject> objects : layers) {
            for(GameObject obj : objects){
                obj.draw(canvas);
            }
        }

        if(BuildConfig.DEBUG){
            if(collisionPaint == null) {
                collisionPaint = new Paint();
                collisionPaint.setStyle(Paint.Style.STROKE);
                collisionPaint.setColor(Color.RED);
            }
            if(debugPaint == null) {
                debugPaint = new Paint();
                debugPaint.setColor(Color.RED);
                debugPaint.setTextSize(1);
            }

            for(ArrayList<GameObject> objects : layers) {
                for (GameObject obj : objects) {
                    if(obj instanceof Enemy) {
                        Enemy enemy = (Enemy) obj;
                        canvas.drawText(Integer.toString(objects.indexOf(obj)),
                                enemy.getX(), enemy.getY(), debugPaint);
                    }

                    if (!(obj instanceof CollisionObject)) continue;

                    RectF rect = ((CollisionObject) obj).getCollisionRect();
                    canvas.drawRect(rect, collisionPaint);
                }
            }
        }
    }

    public void draw(Canvas canvas) {
        draw(canvas, stack.size() - 1);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int touchLayer = getTouchLayer();
        if(touchLayer < 0)
            return false;

        ArrayList<GameObject> objects = layers.get(touchLayer);
        for(GameObject obj : objects) {
            if(obj instanceof Touchable) {
                boolean touched = ((Touchable) obj).onTouchEvent(event);
                if(touched)
                    return true;
            }
        }
        return false;
    }

    public void resumeScene() {
        previousNanos = 0;
        onResume();
    }

    protected boolean isTransparent() {
        return false;
    }

    protected int getTouchLayer() {
        return -1;
    }

    protected void onPause() {

    }

    protected void onResume() {

    }

    protected void onStart() {

    }

    protected void onEnd() {

    }

    public boolean onBackPressed() {
        popScene();
        return true;
    }
}
