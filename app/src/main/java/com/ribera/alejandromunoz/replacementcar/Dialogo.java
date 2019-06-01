package com.ribera.alejandromunoz.replacementcar;

import android.app.AlertDialog;
import android.content.Context;

public class Dialogo extends AlertDialog.Builder {
    public Dialogo(Context context, String titulo, String mensaje, String boton) {
        super(context);
        setTitle(titulo);
        setMessage(mensaje);
        setPositiveButton(boton, null);
    }
}
