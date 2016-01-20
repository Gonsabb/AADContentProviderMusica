package com.example.gonzalo.aadcontentprovidermusica;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Principal extends AppCompatActivity {

    private Uri uriInterprete = Contrato.TablaInterprete.CONTENT_URI_INTERPRETE;
    private Uri uriDisco = Contrato.TablaDisco.CONTENT_URI_DISCO;
    private Uri uriCancion = Contrato.TablaCancion.CONTENT_URI_CANCION;
    private android.widget.Button btMostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        this.btMostrar = (Button) findViewById(R.id.btMostrar);

    }

   public void mostrar (View v){
       Intent i = new Intent(this, Segunda.class);
       startActivity(i);
   }
}
