package com.ribera.alejandromunoz.replacementcar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

public class ActivityJuego extends AppCompatActivity {

    private Juego juego;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent i = getIntent();
        int coche = i.getIntExtra("numCoche", 0);
        juego = new Juego(this, coche);

        setContentView(juego);
    }

    @Override
    public void onBackPressed() {
        juego.getBucle().setEjecutandose(false);
        super.onBackPressed();
    }
}
