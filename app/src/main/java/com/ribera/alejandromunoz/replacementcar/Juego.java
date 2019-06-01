package com.ribera.alejandromunoz.replacementcar;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;


/**
 * Posibles mejoras:
 * - Poner condición de victoria
 * - Que los barriles no se solapen
 * - Poner animación de explosión
 * - Poder elegir el coche con el spinner del mainActivity
 * - Meter varios bitmaps para los obstaculos y que salgan random
 */

public class Juego extends SurfaceView implements SurfaceHolder.Callback {
    private Activity parentActivity;
    private Bitmap bmp;
    private SurfaceHolder holder;
    private BucleJuego bucle;

    private Coche coche;
    private ArrayList<Obstaculos> obstaculos;
    private int velocidadObstaculos = 10;
    private int desplazamientoCoche = 25;
    private int posYFondo = 0;

    private final int numObstaculos = 3;
    private boolean jugando = true;
    private int numCoche;

    private static final String TAG = Juego.class.getSimpleName();


    public Juego(Activity context, int coche) {
        super(context);
        parentActivity = context;
        holder = getHolder();
        holder.addCallback(this);
        this.numCoche = coche;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // se crea la superficie, creamos el game loop

        // Para interceptar los eventos de la SurfaceView
        getHolder().addCallback(this);

        // creamos el game loop
        bucle = new BucleJuego(getHolder(), this);

        // Hacer la Vista focusable para que pueda capturar eventos
        setFocusable(true);

        obstaculos = new ArrayList<>();
        inicializarCoche();
        inicializarObstaculos();

        //comenzar el bucle
        bucle.start();
    }


    private void inicializarCoche() {
        coche = new Coche();
        Bitmap bmp;
        switch (numCoche) {
            case 0:
                bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.azul);
                break;
            case 1:
                bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.negro);
                break;
            case 2:
                bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.amarillo);
                break;
            default:
                bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.rojo);
                break;
        }
        coche.setImagen(bmp);
        coche.setAncho(this.getWidth() / 6);
        coche.setAlto(coche.getImagen().getHeight() * coche.getAncho() / coche.getImagen().getWidth());     // Regla de 3
        coche.setPosicionx((this.getWidth() - coche.getAncho()) / 2);
        coche.setPosiciony(this.getHeight() - coche.getAlto() - (this.getHeight() / 30));
    }

    private void inicializarObstaculos() {
        for(int i=0; i<numObstaculos; i++) {
            // WRENCH = llave
            Obstaculos obstaculo = new Obstaculos();
            obstaculo.setImagen(BitmapFactory.decodeResource(this.getResources(), R.drawable.barrel));
            obstaculo.setAncho(this.getWidth() / 7);
            obstaculo.setAlto(obstaculo.getImagen().getHeight() * obstaculo.getAncho() / obstaculo.getImagen().getWidth());

            int maxX = getWidth() - obstaculo.getAncho();
            obstaculo.setPosicionx((int) (Math.random() * maxX));
            obstaculo.setPosiciony(obstaculo.getAlto() * -1);
            obstaculo.setVelocidadx(0);
            // lA VELOCIDAD Y TAMBIEN PUED3E SER RANDOM
            obstaculo.setVelocidady(this.velocidadObstaculos);

            obstaculos.add(obstaculo);
        }
    }

    /**
     * Este método actualiza el estado del juego. Contiene la lógica del videojuego
     * generando los nuevos estados y dejando listo el sistema para un repintado.
     */
    public void actualizar() {
        if(!jugando) {
            bucle.setEjecutandose(false);
            // parentActivity.finish();
        }

        coche.actualizar();
        for(Obstaculos o : obstaculos) {
            o.actualizar();
            // Al salirse por debajo, reiniciamos el objeto
            if(o.getPosiciony() > this.getHeight()) {
                o.setPosiciony(o.getAlto() * -1);
                int posX = (int) (Math.random() * (getWidth() - o.getAncho()));
                o.setPosicionx(posX);

                // AQUI PUEDES INCREMENTAR UN CONTADOR DE OBSTÁCULOS SUPERADOS.
            }
        }

        for(Obstaculos o: obstaculos) {
            if(o.getRect().intersect(coche.getRect())) {
                jugando = false;
            }
        }

        posYFondo += velocidadObstaculos;
        if(posYFondo > getHeight()) posYFondo = 0;
    }

    /**
     * Este método dibuja el siguiente paso de la animación correspondiente
     */
    public void renderizar(Canvas canvas) {
        this.dibujarFondo(canvas);

        coche.renderizar(canvas);

        for(Obstaculos o : obstaculos) {
            o.renderizar(canvas);
        }

        Paint paint = new Paint();
        if(!jugando) {
            escribeTextoCentrado(canvas, "FIN DEL JUEGO!", getHeight()/2, 80, Color.YELLOW, Color.BLACK);
        }

        // Para poner información en pantalla:
        /*paint.setColor(Color.WHITE);
        paint.setTextSize(60);
        canvas.drawText(String.valueOf(this.getWidth()),20, 50, paint);*/
    }

    private void escribeTextoCentrado(Canvas canvas, String texto, float y, float size, int color, int sombra) {
        Paint paint = new Paint();
        paint.setColor(sombra);
        paint.setStrokeWidth(size/8);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(size);
        float anchoTexto = paint.measureText(texto); // Obtenemos la medida
        float x = (getWidth()-anchoTexto) / 2;   // Calculamos el centro
        canvas.drawText(texto,x,y,paint);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(texto,x,y,paint);
    }

    private void dibujarFondo(Canvas canvas) {
        Bitmap fondo = BitmapFactory.decodeResource(getResources(), R.drawable.fondo);
        Rect origen = new Rect(0,0, fondo.getWidth(), fondo.getHeight());
        Rect destino = new Rect(0, posYFondo, getWidth(), posYFondo + getHeight());
        Rect destino2 = new Rect(0, posYFondo-getHeight(), getWidth(), posYFondo-1);
        canvas.drawBitmap(fondo, origen, destino, null);
        canvas.drawBitmap(fondo, origen, destino2, null);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Juego destruido!");
        // cerrar el thread y esperar que acabe
        boolean retry = true;
        while (retry) {
            try {
                bucle.fin();
                bucle.join();
                retry = false;
            } catch (InterruptedException e) {

            }
        }
    }

    public BucleJuego getBucle() {
        return bucle;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if(event.getY() >= coche.getPosiciony() && event.getY() <= coche.getPosiciony() + coche.getAlto()) {
                int centroCoche = coche.getPosicionx() + (coche.getAncho() / 2);
                if(event.getX() < centroCoche && coche.getPosicionx() > 0) {
                    coche.setPosicionx(coche.getPosicionx() - desplazamientoCoche);
                } else {
                    if(coche.getPosicionx() < getWidth() - coche.getPosicionx()) {
                        coche.setPosicionx(coche.getPosicionx() + desplazamientoCoche);
                    }
                }
            }
        }

        return true;
    }
}
