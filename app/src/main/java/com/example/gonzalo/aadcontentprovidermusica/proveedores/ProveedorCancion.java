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
 * Created by Gonzalo on 19/01/2016.
 */
public class ProveedorCancion extends ContentProvider {

    public static final UriMatcher convierteUri2Int;
    public static final int CANCIONES = 1;
    public static final int CANCION_ID = 2;

    private Ayudante abd;

    static{
        convierteUri2Int = new UriMatcher(UriMatcher.NO_MATCH);
        //Le damos la instrucción de qué hacer a la URI
        convierteUri2Int.addURI(Contrato.TablaCancion.AUTHORITYCANCION, Contrato.TablaCancion.TABLACANCION, CANCIONES);
        convierteUri2Int.addURI(Contrato.TablaCancion.AUTHORITYCANCION, Contrato.TablaCancion.TABLACANCION + "/#", CANCION_ID);
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
            case CANCIONES:
                // Consultando todos los registros
                c = db.query(Contrato.TablaCancion.TABLACANCION, projection, selection, selectionArgs, null, null, sortOrder);
                Log.v("Camino", "nos hemos metido por el camino del case INTERPRETES");
                break;
            case CANCION_ID:
                // Consultando un solo registro basado en el Id del Uri
                long idActividad = ContentUris.parseId(uri);
                c = db.query(Contrato.TablaCancion.TABLACANCION, projection, Contrato.TablaCancion._ID + "= ? " , new String [] {idActividad + ""},
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("URI no soportada: " + uri);
        }
        c.setNotificationUri(getContext().getContentResolver(),Contrato.TablaCancion.CONTENT_URI_CANCION);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (convierteUri2Int.match(uri)){
            case CANCIONES:
                return Contrato.TablaCancion.MULTIPLE_MIME_CANCION;

            case CANCION_ID:
                return Contrato.TablaCancion.SINGLE_MIME_CANCION;

            default:
                throw new IllegalArgumentException("Tipo de actividad desconocida " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // Comprobar que la uri utilizada para hacer la inserción es correcta
        if (convierteUri2Int.match(uri) != CANCIONES) {
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
        long rowId = db.insert(Contrato.TablaCancion.TABLACANCION, null, values);
        if (rowId > 0) {
            //Si se ha insertado el elemento correctamente, entonces devolvemos la uri del elemento que se acaba de insertar
            Uri uri_actividad = ContentUris.withAppendedId(Contrato.TablaCancion.CONTENT_URI_CANCION, rowId);
            getContext().getContentResolver().notifyChange(uri_actividad, null);
            return uri_actividad;
        }
        throw new SQLException("Error al insertar fila en : " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Vuelve a abrir la base de datos para conectar con ella en modo escritura
        SQLiteDatabase db = abd.getWritableDatabase();
        //Obtengo la uri
        int match = convierteUri2Int.match(uri);
        int affected;
        switch (match) {
            case CANCIONES: //Muchas canciones
                affected = db.delete(Contrato.TablaCancion.TABLACANCION,selection,selectionArgs);
                break;

            case CANCION_ID: //Una sóla cancion
                long idActividad = ContentUris.parseId(uri);
                affected = db.delete(Contrato.TablaCancion.TABLACANCION,Contrato.TablaCancion._ID + "= ?" , new String [] {idActividad + ""});
                break;

            default:
                throw new IllegalArgumentException("Elemento actividad desconocido: " + uri);
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
            case CANCIONES:
                affected = db.update(Contrato.TablaCancion.TABLACANCION, values, selection, selectionArgs);
                break;
            case CANCION_ID:
                //Distintas formas de obtener el idActividad
                //uri.getLastPathSegment()
                //ContentUris.parseId(uri)
                //uri.getPathSegments().get(l)
                String idActividad = uri.getPathSegments().get(1);
                affected = db.update(Contrato.TablaCancion.TABLACANCION, values,
                        Contrato.TablaCancion._ID + "= ?" , new String [] {idActividad});
                break;
            default:
                throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return affected;
    }
}
