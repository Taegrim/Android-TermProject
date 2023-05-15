package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;

public class SelectActivity extends AppCompatActivity {

    private static final String TAG = SelectActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
    }

    public void onBtnSelectAbility(View view) {
        Log.d(TAG, "Select Ability!");
    }
}