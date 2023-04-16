package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;

public class BitmapPool {
    private static HashMap<Integer, Bitmap> bitmaps = new HashMap<>();

    public static Bitmap get(int resId) {
        Bitmap bitmap = bitmaps.get(resId);
        if(bitmap == null){
            bitmap = BitmapFactory.decodeResource(GameView.res, resId);
            bitmaps.put(resId, bitmap);
        }
        return bitmap;
    }
}
