package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework;

import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.res.ResourcesCompat;

public class Gauge {
    private Paint fgPaint = new Paint();

    public Gauge(float width, int fgResId) {
        fgPaint.setStyle(Paint.Style.STROKE);
        fgPaint.setStrokeWidth(width * (float)0.75);
        fgPaint.setColor(ResourcesCompat.getColor(GameView.res, fgResId, null));
        fgPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void draw(Canvas canvas, float value) {
        if(value > 0) {
            canvas.drawLine(0, 0, value, 0.f, fgPaint);
        }
    }
}
