package com.example.gonzalo.aadcontentprovidermusica.adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gonzalo.aadcontentprovidermusica.R;
import com.example.gonzalo.aadcontentprovidermusica.clasespojo.Cancion;
import com.example.gonzalo.aadcontentprovidermusica.clasespojo.Disco;
import com.example.gonzalo.aadcontentprovidermusica.clasespojo.Interprete;
import com.example.gonzalo.aadcontentprovidermusica.proveedores.ProveedorCancion;
import com.example.gonzalo.aadcontentprovidermusica.proveedores.ProveedorDisco;
import com.example.gonzalo.aadcontentprovidermusica.proveedores.ProveedorInterprete;

/**
 * Created by Gonzalo on 19/01/2016.
 */
public class Adaptador extends CursorAdapter {

//    private Interprete inter= new Interprete();
//    private Cursor c, c2;
//    private Context context;
//    private ProveedorCancion pc;
//    private ProveedorDisco pd;
//    private ProveedorInterprete pi;

    public Adaptador(Context context, Cursor c) {
        super(context, c, true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater i = LayoutInflater.from(parent.getContext());
        return i.inflate(R.layout.item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView disco = (TextView) view.findViewById(R.id.tvDisco);
        Disco disc = new Disco();
        disc.set(cursor);
        disco.setText(disc.getNombreDisco());
    }


//    public Adaptador(Context context, Cursor c, Cursor c2, Interprete interprete) {
//        super(context, c ,true);
//        this.context=context;
//        this.c=c;
//        this.c2=c2;
//        this.inter=interprete;
//    }

//    @Override
//    public void bindView(View view, Context context, Cursor cursor) {
//
//        TextView titulo = (TextView) view.findViewById(R.id.tvTitulo);
//        Cancion cancion =new Cancion();
//        cancion.set(c2);
//        titulo.setText(cancion.getTitulo());
//
//        TextView interprete = (TextView) view.findViewById(R.id.tvInterprete);
//        interprete.setText(inter.getnombreInterprete());
//
//        Disco disc = new Disco();
//        disc.set(cursor);
//        TextView disco = (TextView) view.findViewById(R.id.tvDisco);
//        disco.setText(cancion.getTitulo());
//
//    }
//
//    @Override
//    public View newView(Context context, Cursor cursor, ViewGroup parent) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        return inflater.inflate(R.layout.item, parent, false);
//    }
}
