package kr.ac.tukorea.sgp.s2018182024.lastsurvivor.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import kr.ac.tukorea.sgp.s2018182024.lastsurvivor.R;

public class TitleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
    }

    public void onBtnStart(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void onBtnEnd(View view) {
        finish();
    }
}