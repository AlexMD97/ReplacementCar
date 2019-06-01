package com.ribera.alejandromunoz.replacementcar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Obstaculos {

    private int posicionx;
    private int posiciony;
    private int velocidadx;
    private int velocidady;
    private Bitmap imagen;
    private int alto;
    private int ancho;

    public Obstaculos(){

    }

    public Obstaculos(int posicionx, int posiciony, int velocidadx, int velocidady, Bitmap imagen, int alto, int ancho) {
        this.posicionx = posicionx;
        this.posiciony = posiciony;
        this.velocidadx = velocidadx;
        this.velocidady = velocidady;
        this.imagen = imagen;
        this.alto = alto;
        this.ancho = ancho;
    }

    public void actualizar(){
        this.posiciony += this.velocidady;
    }

    public void renderizar(Canvas canvas){
        Rect origen = new Rect(0,0, getImagen().getWidth(), getImagen().getHeight());
        Rect destino = new Rect(getPosicionx(), getPosiciony(), getPosicionx() + getAncho(), getPosiciony() + getAlto());
        canvas.drawBitmap(this.getImagen(), origen, destino, null);
    }

    public Rect getRect() {
        return new Rect(posicionx, posiciony, posicionx + ancho, posiciony + alto);
    }

    public int getPosicionx() {
        return posicionx;
    }

    public void setPosicionx(int posicionx) {
        this.posicionx = posicionx;
    }

    public int getPosiciony() {
        return posiciony;
    }

    public void setPosiciony(int posiciony) {
        this.posiciony = posiciony;
    }

    public int getVelocidadx() {
        return velocidadx;
    }

    public void setVelocidadx(int velocidadx) {
        this.velocidadx = velocidadx;
    }

    public int getVelocidady() {
        return velocidady;
    }

    public void setVelocidady(int velocidady) {
        this.velocidady = velocidady;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }
}
