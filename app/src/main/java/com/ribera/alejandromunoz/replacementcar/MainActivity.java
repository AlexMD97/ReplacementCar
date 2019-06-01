package com.ribera.alejandromunoz.replacementcar;

import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnempieza;
    MediaPlayer mp;

    public int AnchoPantalla, AltoPantalla;
    public int coche = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnempieza = findViewById(R.id.btn_empezar);

        CalculaTamanoPantalla();
        MusicaMenu();

        findViewById(R.id.btn_empezar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                //mp.reset();
                Intent i = new Intent(MainActivity.this, ActivityJuego.class);
                i.putExtra("numCoche", coche);
                MainActivity.this.startActivity(i);
            }
        });
    }

    public void CalculaTamanoPantalla() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        AnchoPantalla = size.x;
        AltoPantalla = size.y;
    }


    public void MusicaMenu() {
        //TO DOWNLOAD..
        mp = MediaPlayer.create(this, R.raw.menu);

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        mp.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuppal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.mnu_salir:
                System.exit(0);
                return true;
            case R.id.mnu_info:
                Dialogo d = new Dialogo(this,
                        getString(R.string.dialog_titulo),
                        getString(R.string.dialog_msg),
                        getString(R.string.dialogbtn));
                d.create();
                d.show();
                return true;
            case R.id.mnu_credit:
                Dialogo d2 = new Dialogo(this,
                        getString(R.string.credit_title),
                        getString(R.string.credit_msg),
                        getString(R.string.credit_btn));
                d2.create();
                d2.show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


}
