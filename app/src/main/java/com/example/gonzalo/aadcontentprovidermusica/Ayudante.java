package com.example.gonzalo.aadcontentprovidermusica;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Gonzalo on 18/01/2016.
 */
public class Ayudante extends SQLiteOpenHelper{

    public static final String DATABASE_NAME ="contentprovidermusica.sqlite";
    public static final int DATABASE_VERSION = 5;

    public Ayudante(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql, sql2, sql3;
        sql="create table "+Contrato.TablaInterprete.TABLAINTERPRETE+ " ("+
                Contrato.TablaInterprete._ID+ " integer primary key autoincrement, "+
                Contrato.TablaInterprete.NOMBREINTERPRETE+" text)";

        db.execSQL(sql);

        sql2="create table "+Contrato.TablaDisco.TABLADISCO+ " ("+
                Contrato.TablaDisco._ID+ " integer primary key autoincrement, "+
                Contrato.TablaDisco.NOMBREDISCO+" text, " +
                Contrato.TablaDisco.IDINTERPRETE+" long)";

        db.execSQL(sql2);

        sql3="create table "+Contrato.TablaCancion.TABLACANCION+ " ("+
                Contrato.TablaCancion._ID+ " integer primary key autoincrement, "+
                Contrato.TablaCancion.TITULO+" text, " +
                Contrato.TablaCancion.IDDISCO+" long)";

        db.execSQL(sql3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql1="drop table if exists "
                + Contrato.TablaCancion.TABLACANCION;
        db.execSQL(sql1);

        String sql2="drop table if exists "
                + Contrato.TablaDisco.TABLADISCO;
        db.execSQL(sql2);

        String sql3="drop table if exists "
                + Contrato.TablaInterprete.TABLAINTERPRETE;
        db.execSQL(sql3);
        onCreate(db);
    }
}
