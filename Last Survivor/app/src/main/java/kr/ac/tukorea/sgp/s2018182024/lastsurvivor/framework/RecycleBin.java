package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class RecycleBin {
    private static HashMap<Class, ArrayList<Recyclable>> recycleBin = new HashMap<>();

    public static void collect(Recyclable object) {
        Class c = object.getClass();
        ArrayList<Recyclable> arr = recycleBin.get(c);
        if(arr == null) {
            arr = new ArrayList<>();
            recycleBin.put(c, arr);
        }
        if(arr.indexOf(object) >= 0) {      // 이미 있다면 끝냄
            return;
        }

        object.onRecycle();
        arr.add(object);
    }

    public static Recyclable get(Class c) {
        ArrayList<Recyclable> arr = recycleBin.get(c);
        if(arr == null) {   // 아직 배열이 안만들어 졌다면
            return null;
        }
        if(arr.size() == 0) { // 배열이 비어있다면 (= 전부 사용하고 있다면)
            return null;
        }
        return arr.remove(0);
    }
}
