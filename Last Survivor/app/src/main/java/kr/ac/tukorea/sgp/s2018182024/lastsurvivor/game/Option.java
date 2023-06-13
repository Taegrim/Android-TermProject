package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.JsonReader;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameObject;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Objects.Magic.MagicManager;

public class Option {
    private static final String TAG = Option.class.getSimpleName();

    protected static ArrayList<Option> options;
    private static Context context;
    public String name, image;
    public ArrayList<String> description;
    public MagicManager.MagicType magicType;

    public static ArrayList<Option> loadOptions(Context context, String fileName) {
        ArrayList<Option> options = new ArrayList<>();
        try {
            Option.context = context;
            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open(fileName);
            JsonReader jr = new JsonReader(new InputStreamReader(is));

            jr.beginArray();
            while(jr.hasNext()) {
                Option option = loadOption(jr);
                if(option != null) {
                    options.add(option);
                }
            }
            jr.endArray();
            jr.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        Option.options = options;
        return options;
    }

    private static Option loadOption(JsonReader jr) {
        Option option = new Option();
        try {
            jr.beginObject();
            while(jr.hasNext()) {
                String name = jr.nextName();
                if(!JsonHelper.readProperty(option, name, jr)) {
                    jr.skipValue();
                }
            }
            jr.endObject();
        }
        catch (IOException e) {
            return null;
        }

        return option;
    }

    public Bitmap getImage() {
        try {
            return BitmapFactory.decodeStream(context.getAssets().open(image));
        }
        catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Option get(int index) {
        return options.get(index);
    }

}
