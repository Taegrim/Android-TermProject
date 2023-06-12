package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.BuildConfig;

/**
 * TODO: document your custom view class.
 */
public class GameView extends View implements Choreographer.FrameCallback {
    private static final String TAG = GameView.class.getSimpleName();
    public static GameView view;
    public static Resources res;
    protected Paint fpsPaint;
    protected Paint borderPaint;
    protected boolean running;

    public GameView(Context context) {
        super(context);
        init(null, 0);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    public static void clear() {
        view = null;
    }

    private void init(AttributeSet attrs, int defStyle) {
        GameView.view = this;
        GameView.res = getResources();
        running = true;

        Choreographer.getInstance().postFrameCallback(this);

        if(BuildConfig.DEBUG) {
            fpsPaint = new Paint();
            fpsPaint.setColor(Color.BLUE);
            fpsPaint.setTextSize(100f);

            borderPaint = new Paint();
            borderPaint.setColor(Color.RED);
            borderPaint.setStyle(Paint.Style.STROKE);
            borderPaint.setStrokeWidth(0.1f);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);

        float viewRatio = (float)w / (float)h;
        float gameRatio = Metrics.gameWidth / Metrics.gameHeight;

        // 화면 비율이 게임 비율 보다 크면 (폰이 가로로 넓으면)
        if(viewRatio > gameRatio){
            Metrics.xOffset = (int) ((w - h * gameRatio) / 2);
            Metrics.yOffset = 0;
            Metrics.scale = h / Metrics.gameHeight;
        }
        else{
            Metrics.xOffset = 0;
            Metrics.yOffset = (int) ((h - w / gameRatio) / 2);
            Metrics.scale = w / Metrics.gameWidth;
        }
    }

    @Override
    public void doFrame(long currentTime) {
        BaseScene scene = BaseScene.getTopScene();
        if(scene != null) {
            scene.update(currentTime);
        }

        invalidate();
        if(running){
            Choreographer.getInstance().postFrameCallback(this);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate(Metrics.xOffset, Metrics.yOffset);
        canvas.scale(Metrics.scale, Metrics.scale);
        BaseScene scene = BaseScene.getTopScene();
        if(scene != null){
            scene.draw(canvas);
        }

        if(BuildConfig.DEBUG){
            canvas.drawRect(0, 0, Metrics.gameWidth, Metrics.gameHeight, borderPaint);
        }
        canvas.restore();

        if(BuildConfig.DEBUG && BaseScene.frameTime > 0) {
            int fps = (int) (1.0f / BaseScene.frameTime);
            int count = (scene != null) ? scene.getObjectCount() : 0;
            canvas.drawText("FPS : " + fps + " objects : " + count, 50f, 100f, fpsPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = BaseScene.getTopScene().onTouchEvent(event);
        if(handled){
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void pauseGame(){
        running = false;
        BaseScene scene = BaseScene.getTopScene();
        if(scene == null)
            return;
        scene.onPause();
    }

    public void resumeGame(){
        if(running){
            return;
        }
        running = true;

        BaseScene.getTopScene().resumeScene();
        Choreographer.getInstance().postFrameCallback(this);
    }


    public boolean onBackPressed() {
        BaseScene scene = BaseScene.getTopScene();
        if (scene == null)
            return false;

        return scene.onBackPressed();
    }

    public Activity getActivity() {
        Context context = getContext();
        while(context instanceof ContextWrapper) {
            if(context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }
}