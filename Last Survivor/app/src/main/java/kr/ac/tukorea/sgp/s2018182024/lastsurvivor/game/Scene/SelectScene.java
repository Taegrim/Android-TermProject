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

    }

    public void setContent() {

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
