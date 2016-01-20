package com.example.gonzalo.aadcontentprovidermusica;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.gonzalo.aadcontentprovidermusica.adaptadores.AdapCanciones;
import com.example.gonzalo.aadcontentprovidermusica.clasespojo.Disco;
import com.example.gonzalo.aadcontentprovidermusica.clasespojo.Interprete;

public class CancionesActividad extends AppCompatActivity {

    private android.widget.ListView listView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canciones_actividad);
        this.listView2 = (ListView) findViewById(R.id.listView2);

        String selClausula =   Contrato.TablaCancion.IDDISCO+" = ?";
        String[] sArgs = {getIntent().getExtras().getLong("iddisc")+""};
        final Cursor curs = getContentResolver().query(Contrato.TablaCancion.CONTENT_URI_CANCION, null, selClausula,sArgs,null);

        AdapCanciones adp = new AdapCanciones(this,curs,searchInterprete());
        listView2.setAdapter(adp);
    }

    public Interprete searchInterprete() {
        String selClausula = Contrato.TablaDisco._ID + " = ?";
        String[] sArgs = {getIntent().getExtras().getLong("iddisc") + ""};
        Cursor cdisc = getContentResolver().query(Contrato.TablaDisco.CONTENT_URI_DISCO, null, selClausula, sArgs, null);
        long idI = 0;

        if (cdisc != null) {
            while (cdisc.moveToNext()) {
                idI = cdisc.getLong(2);
//                Log.v("aa", "id del interprete"+idInt);
            }
        }

        selClausula = Contrato.TablaInterprete._ID + " = ?";
        String[] sArgs2 = {idI + ""};
        cdisc = getContentResolver().query(Contrato.TablaInterprete.CONTENT_URI_INTERPRETE, null, selClausula, sArgs2, null);
        Interprete i = new Interprete();
        if (cdisc != null) {
            while (cdisc.moveToNext()) {
                i.setnombreInterprete(cdisc.getString(1));
//                Log.v("aa", inter.toString());
            }
        }
        return i;
    }
}
