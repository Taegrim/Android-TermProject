package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Scene;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.app.MainActivity;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.databinding.OptionItemBinding;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.databinding.SelectMagicBinding;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.BaseScene;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Button;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.GameView;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.framework.Sprite;
import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.game.Option;

public class SelectScene extends BaseScene {
    private static final String TAG = SelectScene.class.getSimpleName();
    private ArrayList<Option> options = new ArrayList<>();
    private static Random random = new Random();
    private static Context context = GameView.view.getContext();;
    private SelectMagicBinding binding;

    public enum Layer {
        TOUCH, COUNT
    }

    public SelectScene() {
        initLayers(Layer.COUNT);

//        for(int i = 0; i < 3; ++i) {
//            addObject(Layer.TOUCH, getItem());
//        }

        ViewGroup.LayoutParams linear = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        binding = SelectMagicBinding.inflate(GameView.view.getActivity().getLayoutInflater());

        setContent();

        GameView.view.getActivity().addContentView(binding.getRoot(), linear);
    }

    public void setContent() {
        Option option = Option.get(0);
        binding.image01.setImageBitmap(option.getImage());
        binding.name01.setText(option.name);
        binding.level01.setText("Lv " + option.currentLevel);
        binding.description01.setText(option.description.get(option.currentLevel));

        option = Option.get(1);
        binding.image02.setImageBitmap(option.getImage());
        binding.name02.setText(option.name);
        binding.level02.setText("Lv " + option.currentLevel);
        binding.description02.setText(option.description.get(option.currentLevel));


        option = Option.get(2);
        binding.image03.setImageBitmap(option.getImage());
        binding.name03.setText(option.name);
        binding.level03.setText("Lv " + option.currentLevel);
        binding.description03.setText(option.description.get(option.currentLevel));
    }

    @Override
    protected boolean isTransparent() {
        return true;
    }

    @Override
    protected int getTouchLayer() {
        return Layer.TOUCH.ordinal();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //
        return false;
    }

    @Override
    protected void onEnd() {
        ((ViewGroup)binding.content.getParent()).removeView(binding.content);
    }
}
