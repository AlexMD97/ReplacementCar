package com.ribera.alejandromunoz.replacementcar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Coche {
    private int posicionx;
    private int posiciony;
    private Bitmap imagen;
    private int alto;
    private int ancho;

    public Coche(int posicionx, int posiciony, Bitmap imagen, int alto, int ancho) {
        this.posicionx = posicionx;
        this.posiciony = posiciony;
        this.imagen = imagen;
        this.alto = alto;
        this.ancho = ancho;
    }

    public Coche() {

    }

    public void actualizar() {

    }

    public void renderizar(Canvas canvas) {
        Rect origen = new Rect(0,0, getImagen().getWidth(), getImagen().getHeight());
        Rect destino = new Rect(getPosicionx(), getPosiciony(), getPosicionx() + getAncho(), getPosiciony() + getAlto());
        canvas.drawBitmap(getImagen(), origen, destino, null);
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
