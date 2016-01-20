package com.example.gonzalo.aadcontentprovidermusica;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Gonzalo on 18/01/2016.
 */
public class Contrato {

    public Contrato() {
    }

    public static abstract class TablaCancion implements BaseColumns {
        public static final String TABLACANCION = "cancion";
        public static final String TITULO = "titulo";
        public static final String IDDISCO = "id_disco";

        //La autoridad es la cadena q identifica a qué contentprovider se llama
        public final static String AUTHORITYCANCION = "com.example.gonzalo.aadcontentprovidermusica.proveedores.ProveedorCancion";
        //Definir como llego a la tabla (a q tabla estoy llegando)
        public final static Uri CONTENT_URI_CANCION =
                Uri.parse("content://" + AUTHORITYCANCION + "/" + TABLACANCION);
        public final static String SINGLE_MIME_CANCION =
                "vnd.android.cursor.item/vnd." + AUTHORITYCANCION + TABLACANCION;
        public final static String MULTIPLE_MIME_CANCION =
                "vnd.android.cursor.dir/vnd." + AUTHORITYCANCION + TABLACANCION;

    }

    public static abstract class TablaDisco implements BaseColumns {
        public static final String TABLADISCO = "disco";
        public static final String NOMBREDISCO = "nombredisco";
        public static final String IDINTERPRETE = "id_interprete";

        //La autoridad es la cadena q identifica a qué contentprovider se llama
        public final static String AUTHORITYDISCO = "com.example.gonzalo.aadcontentprovidermusica.proveedores.ProveedorDisco";
        //Definir como llego a la tabla (a q tabla estoy llegando)
        public final static Uri CONTENT_URI_DISCO =
                Uri.parse("content://" + AUTHORITYDISCO + "/" + TABLADISCO);
        public final static String SINGLE_MIME_DISCO =
                "vnd.android.cursor.item/vnd." + AUTHORITYDISCO + TABLADISCO;
        public final static String MULTIPLE_MIME_DISCO =
                "vnd.android.cursor.dir/vnd." + AUTHORITYDISCO + TABLADISCO;

    }

    public static abstract class TablaInterprete implements BaseColumns {
        public static final String TABLAINTERPRETE = "interprete";
        public static final String NOMBREINTERPRETE = "nombreinterprete";

        //La autoridad es la cadena q identifica a qué contentprovider se llama
        public final static String AUTHORITYINTERPRETE = "com.example.gonzalo.aadcontentprovidermusica.proveedores.ProveedorInterprete";
        //Definir como llego a la tabla (a q tabla estoy llegando)
        public final static Uri CONTENT_URI_INTERPRETE =
                Uri.parse("content://" + AUTHORITYINTERPRETE + "/" + TABLAINTERPRETE);
        public final static String SINGLE_MIME_INTERPRETE =
                "vnd.android.cursor.item/vnd." + AUTHORITYINTERPRETE + TABLAINTERPRETE;
        public final static String MULTIPLE_MIME_INTERPRETE =
                "vnd.android.cursor.dir/vnd." + AUTHORITYINTERPRETE + TABLAINTERPRETE;

    }
}
