package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework;

import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.res.ResourcesCompat;

public class Gauge {
    private Paint fgPaint = new Paint();
    private Paint bgPaint = new Paint();

    public Gauge(float width, int fgColorResId, int bgColorResId) {
        fgPaint.setStyle(Paint.Style.STROKE);
        fgPaint.setStrokeWidth(width * (float)0.75);
        fgPaint.setColor(ResourcesCompat.getColor(GameView.res, fgColorResId, null));
        fgPaint.setStrokeCap(Paint.Cap.ROUND);

        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(width);
        bgPaint.setColor(ResourcesCompat.getColor(GameView.res, bgColorResId, null));
        bgPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void draw(Canvas canvas, float value) {
        canvas.drawLine(0, 0, 1.0f, 0.0f, bgPaint);
        if(value > 0) {
            canvas.drawLine(0, 0, value, 0.f, fgPaint);
        }
    }
}
