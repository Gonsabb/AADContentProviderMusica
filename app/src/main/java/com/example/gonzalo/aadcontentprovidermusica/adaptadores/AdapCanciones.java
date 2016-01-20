package com.example.gonzalo.aadcontentprovidermusica.adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.gonzalo.aadcontentprovidermusica.R;
import com.example.gonzalo.aadcontentprovidermusica.clasespojo.Cancion;
import com.example.gonzalo.aadcontentprovidermusica.clasespojo.Interprete;

/**
 * Created by Gonzalo on 20/01/2016.
 */
public class AdapCanciones extends CursorAdapter{

    private Interprete in = new Interprete();
    private Cancion canc = new Cancion();

    public AdapCanciones(Context co, Cursor cu, Interprete interprete) {
        super(co, cu, true);
        in = interprete;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater i = LayoutInflater.from(parent.getContext());
        View v = i.inflate(R.layout.itemcancion, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView cancion = (TextView)view.findViewById(R.id.tvCancion);
        TextView interprete = (TextView)view.findViewById(R.id.tvInterprete);

        canc.set(cursor);
        cancion.setText(canc.getTitulo());
        interprete.setText(in.getnombreInterprete());
    }

}
