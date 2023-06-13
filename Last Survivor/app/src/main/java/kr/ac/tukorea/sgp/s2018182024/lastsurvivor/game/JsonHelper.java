package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Magic.Magic;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Magic.MagicManager;

public class JsonHelper {
    private static final String TAG = JsonHelper.class.getSimpleName();

    public static boolean readProperty(Object object, String name, JsonReader reader) throws IOException {
        try {
            Field field = object.getClass().getField(name);
            Class<?> type = field.getType();
            if (type == int.class) {
                int value = reader.nextInt();
                Log.d(TAG, "Int " + name + ": " + value + " - " + object);
                field.setInt(object, value);
            }
            else if (type == String.class) {
                String value = reader.nextString();
                Log.d(TAG, "String " + name + ": " + value + " - " + object);
                field.set(object, value);
            }
            else if (type == boolean.class) {
                boolean value = reader.nextBoolean();
                Log.d(TAG, "boolean " + name + ": " + value + " - " + object);
                field.set(object, value);
            }
            else if (type == double.class) {
                double value = reader.nextDouble();
                Log.d(TAG, "double " + name + ": " + value + " - " + object);
                field.set(object, value);
            }
            else if (type == long.class) {
                long value = reader.nextLong();
                Log.d(TAG, "long " + name + ": " + value + " - " + object);
                field.set(object, value);
            }
            else if (type == int[].class) {
                int[] values = readIntArray(reader);
                Log.d(TAG, "int[] " + name + ": [" + values.length + "] - " + object);
                field.set(object, values);
            }
            else if (type == ArrayList.class) {
                ArrayList<String> values = readStringArray(reader);
                Log.d(TAG, "String List " + name + ": [" + values.size() + "] - " + object);
                field.set(object, values);
            }
            else if (type == MagicManager.MagicType.class) {
                int value = reader.nextInt();
                MagicManager.MagicType magicType = MagicManager.MagicType.values()[value];
                Log.d(TAG, "MagicType " + name + ": " + value + " - " + object);
                field.set(object, magicType);
            }
            else {
                Log.e(TAG, "Not handling " + name + ". type: " + type + " - " + object);
                return false;
            }
            return true;
        } catch (NoSuchFieldException e) {
            Log.e(TAG, "No field \"" + name + "\" in " + object);
            return false;
        } catch (IllegalAccessException e) {
            return false;
        }
    }
    private static int[] readIntArray(JsonReader reader) throws IOException {
        ArrayList<Integer> integers = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            int value = reader.nextInt();
            integers.add(value);
        }
        reader.endArray();

        int[] ints = new int[integers.size()];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = integers.get(i);
        }

        return ints;
    }

    private static ArrayList<String> readStringArray(JsonReader reader) throws IOException {
        ArrayList<String> strings = new ArrayList<>();

        reader.beginArray();
        while(reader.hasNext()) {
            String value = reader.nextString();
            strings.add(value);
        }
        reader.endArray();

        return strings;
    }
}
