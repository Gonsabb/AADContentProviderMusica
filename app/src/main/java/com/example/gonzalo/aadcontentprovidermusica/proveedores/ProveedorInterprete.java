package com.example.gonzalo.aadcontentprovidermusica.proveedores;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.gonzalo.aadcontentprovidermusica.Ayudante;
import com.example.gonzalo.aadcontentprovidermusica.Contrato;

/**
 * Created by Gonzalo on 18/01/2016.
 */
public class ProveedorInterprete extends ContentProvider{
    public static final UriMatcher convierteUri2Int;
    public static final int INTERPRETES = 1;
    public static final int INTERPRETE_ID = 2;

    private Ayudante abd;

    static{
        convierteUri2Int = new UriMatcher(UriMatcher.NO_MATCH);
        //Le damos la instrucción de qué hacer a la URI
        convierteUri2Int.addURI(Contrato.TablaInterprete.AUTHORITYINTERPRETE, Contrato.TablaInterprete.TABLAINTERPRETE, INTERPRETES);
        convierteUri2Int.addURI(Contrato.TablaInterprete.AUTHORITYINTERPRETE, Contrato.TablaInterprete.TABLAINTERPRETE + "/#", INTERPRETE_ID);
    }

    @Override
    public boolean onCreate() {
        abd = new Ayudante(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Obtener base de datos
        SQLiteDatabase db = abd.getReadableDatabase();
        // Comparar Uri
        int match = convierteUri2Int.match(uri);

        Cursor c;

        switch (match) {
            case INTERPRETES:
                // Consultando todos los registros
                c = db.query(Contrato.TablaInterprete.TABLAINTERPRETE, projection, selection, selectionArgs, null, null, sortOrder);
                Log.v("Camino", "nos hemos metido por el camino del case INTERPRETES");
                break;
            case INTERPRETE_ID:
                // Consultando un solo registro basado en el Id del Uri
                long idActividad = ContentUris.parseId(uri);
                c = db.query(Contrato.TablaInterprete.TABLAINTERPRETE, projection, Contrato.TablaInterprete._ID + "= ? " , new String [] {idActividad + ""},
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("URI no soportada: " + uri);
        }
        c.setNotificationUri(getContext().getContentResolver(),Contrato.TablaInterprete.CONTENT_URI_INTERPRETE);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {//Devuelve el tipo mime que corresponde a la uri con que se ha llamado
        switch (convierteUri2Int.match(uri)){
            case INTERPRETES:
                return Contrato.TablaInterprete.MULTIPLE_MIME_INTERPRETE;

            case INTERPRETE_ID:
                return Contrato.TablaInterprete.SINGLE_MIME_INTERPRETE;

            default:
                throw new IllegalArgumentException("Tipo de actividad desconocida " + uri);
        }
    }

    //método insert
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // Comprobar que la uri utilizada para hacer la inserción es correcta
        if (convierteUri2Int.match(uri) != INTERPRETES) {
            throw new IllegalArgumentException("URI desconocida : " + uri);//SI no es correcta la Uri
        }

        //Si el ContentValues es nulo, crea un ContentValues
        ContentValues contentValues;
        if (values == null) {
            throw new IllegalArgumentException("Interprete null ");
        }
        //Validar

        // Inserción de nueva fila
        SQLiteDatabase db = abd.getWritableDatabase();//Conectamos a la base de datos en modo escritura
        long rowId = db.insert(Contrato.TablaInterprete.TABLAINTERPRETE, null, values);
        if (rowId > 0) {
            //Si se ha insertado el elemento correctamente, entonces devolvemos la uri del elemento que se acaba de insertar
            Uri uri_actividad = ContentUris.withAppendedId(Contrato.TablaInterprete.CONTENT_URI_INTERPRETE, rowId);
            getContext().getContentResolver().notifyChange(uri_actividad, null);
            return uri_actividad;
        }
        throw new SQLException("Error al insertar fila en : " + uri);
    }

    //método borrar
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Vuelve a abrir la base de datos para conectar con ella en modo escritura
        SQLiteDatabase db = abd.getWritableDatabase();
        //Obtengo la uri
        int match = convierteUri2Int.match(uri);
        int affected;
        switch (match) {
            case INTERPRETES: //Muchos interpretes
                affected = db.delete(Contrato.TablaInterprete.TABLAINTERPRETE,selection,selectionArgs);
                break;

            case INTERPRETE_ID: //Un sólo interprete
                long idActividad = ContentUris.parseId(uri);
                affected = db.delete(Contrato.TablaInterprete.TABLAINTERPRETE,Contrato.TablaInterprete._ID + "= ?" , new String [] {idActividad + ""});
                break;

            default:
                throw new IllegalArgumentException("Elemento actividad desconocido: " +
                        uri);
        }
        // Notificar cambio asociado a la urigetContext().getContentResolver().notifyChange(uri, null);
        getContext().getContentResolver().notifyChange(uri, null);
        //Devuelve el numero de filas borradas
        return affected;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = abd.getWritableDatabase();
        int affected;
        switch (convierteUri2Int.match(uri)) {
            case INTERPRETES:
                affected = db.update(Contrato.TablaInterprete.TABLAINTERPRETE, values, selection, selectionArgs);
                break;
            case INTERPRETE_ID:
                //Distintas formas de obtener el idActividad
                //uri.getLastPathSegment()
                //ContentUris.parseId(uri)
                //uri.getPathSegments().get(l)
                String idActividad = uri.getPathSegments().get(1);
                affected = db.update(Contrato.TablaInterprete.TABLAINTERPRETE, values,
                        Contrato.TablaInterprete._ID + "= ?" , new String [] {idActividad});
                break;
            default:
                throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return affected;
    }
}
