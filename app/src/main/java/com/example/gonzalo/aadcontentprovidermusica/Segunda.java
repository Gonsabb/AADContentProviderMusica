package com.example.gonzalo.aadcontentprovidermusica;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.gonzalo.aadcontentprovidermusica.adaptadores.Adaptador;
import com.example.gonzalo.aadcontentprovidermusica.clasespojo.Cancion;
import com.example.gonzalo.aadcontentprovidermusica.clasespojo.Disco;
import com.example.gonzalo.aadcontentprovidermusica.clasespojo.Interprete;

/**
 * Created by Gonzalo on 19/01/2016.
 */
public class Segunda extends AppCompatActivity {

    private Cursor c;
    private ListView listView;
    private SharedPreferences pc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.segunda);
        this.listView = (ListView) findViewById(R.id.listView);
        init();
    }

    public void init(){
        pc = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String aux = pc.getString("start","nop");
        if(aux.equals("nop")) {
            SharedPreferences.Editor ed = pc.edit();
            ed.putString("start", "sip");
            ed.commit();
            read();
        }

        c = getContentResolver().query(Contrato.TablaDisco.CONTENT_URI_DISCO, null,null,null,null);
        Adaptador adp =  new Adaptador(this,c);
        listView.setAdapter(adp);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                swap(c);
            }
        });


//        Disco x = new Disco();
//        x.set(c);
//
//        String selClausula = Contrato.TablaDisco._ID + " = ?";
//        String[] sArgs = {x.getId() + ""};
//        final Cursor ccanc = getContentResolver().query(Contrato.TablaCancion.CONTENT_URI_CANCION, null, selClausula, sArgs, null);
//
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh) {
            refresh();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void refresh(){
        read();
        Intent i = new Intent(this, Segunda.class);
        startActivity(i);
    }
    public void read(){
        Cursor cur = getContentResolver().query(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Audio.Media.IS_MUSIC + " = 1", null, null);
        while (cur.moveToNext()) {
            write(cur);
        }
        cur.close();
    }
    public void write(Cursor cursor) {
        Cancion can;
        Interprete inter;
        Disco discc;
        long idinter, iddisc, idcan;
        Cursor c2;

        c2 = getContentResolver().query(Contrato.TablaInterprete.CONTENT_URI_INTERPRETE, null, null, null, null);
        idinter = 0;

        if (cursor.getString(25) != null) {
//            if (c2 != null) {
                while (c2.moveToNext()) {
                    if (c2.getString(1).equals(cursor.getString(25))) {
                        idinter = c2.getLong(0);
                    }
                }
//            }
        }
        if (idinter == 0) {
            inter = new Interprete(0 ,cursor.getString(25));
            idinter = getId(getContentResolver().insert(Contrato.TablaInterprete.CONTENT_URI_INTERPRETE, inter.getContentValues()), Contrato.TablaInterprete.CONTENT_URI_INTERPRETE);
        }
        c2 = getContentResolver().query(Contrato.TablaDisco.CONTENT_URI_DISCO, null, null, null, null);
        iddisc = 0;


        if (cursor.getString(28) != null) {
//            if (c2 != null) {
                while (c2.moveToNext()) {
                    if (c2.getString(1).equals(cursor.getString(28))) {
                        iddisc = c2.getLong(0);
                    }
                }
//            }
        }
        if (iddisc == 0) {
            discc = new Disco(0, idinter, cursor.getString(28));
            iddisc = getId(getContentResolver().insert(Contrato.TablaDisco.CONTENT_URI_DISCO, discc.getContentValues()), Contrato.TablaDisco.CONTENT_URI_DISCO);
        }
        c2 = getContentResolver().query(Contrato.TablaCancion.CONTENT_URI_CANCION, null, null, null, null);
        idcan = 0;


        if (cursor.getString(8) != null) {
//            if (c2 != null) {
                while (c2.moveToNext()) {
                    if (c2.getString(2).equals(cursor.getString(8))) {
                        idcan = c2.getLong(0);
                    }
                }
//            }
        }
        if (idcan == 0) {
            can = new Cancion(0, cursor.getString(8), iddisc);
            idcan = getId(getContentResolver().insert(Contrato.TablaCancion.CONTENT_URI_CANCION, can.getContentValues()), Contrato.TablaCancion.CONTENT_URI_CANCION);
        }
    }

    public long getId(Uri u, Uri cor){
        String aux = u.toString().substring(cor.toString().length()+1);
        return Long.parseLong(aux);
    }

    public void swap(Cursor c) {
        Intent i=new Intent(this,CancionesActividad.class);
        Disco d = new Disco();
        d.set(c);
        i.putExtra("iddisc", d.getId());
        startActivity(i);
    }

//    public Interprete buscarInterprete() {
//        String selClausula = Contrato.TablaDisco._ID + " = ?";
//        Disco x = new Disco();
//        x.set(c);
//
//        String[] sArgs = {x.getId() + ""};
//        Cursor cdisc = getContentResolver().query(Contrato.TablaDisco.CONTENT_URI_DISCO, null, selClausula, sArgs, null);
//        long idI = 0;
//
//        if (cdisc != null) {
//            while (cdisc.moveToNext()) {
//                idI = cdisc.getLong(2);
////                Log.v("aa", "id del interprete"+idInt);
//            }
//        }
//
//        selClausula = Contrato.TablaInterprete._ID + " = ?";
//        String[] sArgs2 = {idI + ""};
//        cdisc = getContentResolver().query(Contrato.TablaInterprete.CONTENT_URI_INTERPRETE, null, selClausula, sArgs2, null);
//        Interprete i = new Interprete();
//        if (cdisc != null) {
//            while (cdisc.moveToNext()) {
//                i.setnombreInterprete(cdisc.getString(1));
////                Log.v("aa", inter.toString());
//            }
//        }
//        return i;
//    }

}
