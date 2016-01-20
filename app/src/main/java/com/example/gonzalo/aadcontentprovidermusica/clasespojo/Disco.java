package com.example.gonzalo.aadcontentprovidermusica.clasespojo;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.gonzalo.aadcontentprovidermusica.Contrato;

/**
 * Created by Gonzalo on 18/01/2016.
 */
public class Disco {
    private long id, idInterprete;
    private String nombreDisco;

    public Disco() {
    }

    public Disco(long id, long idInterprete, String nombreDisco) {
        this.id = id;
        this.idInterprete = idInterprete;
        this.nombreDisco = nombreDisco;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdInterprete() {
        return idInterprete;
    }

    public void setIdInterprete(long idInterprete) {
        this.idInterprete = idInterprete;
    }

    public String getNombreDisco() {
        return nombreDisco;
    }

    public void setNombreDisco(String nombreDisco) {
        this.nombreDisco = nombreDisco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Disco disco = (Disco) o;

        if (id != disco.id) return false;
        if (idInterprete != disco.idInterprete) return false;
        return !(nombreDisco != null ? !nombreDisco.equals(disco.nombreDisco) : disco.nombreDisco != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (idInterprete ^ (idInterprete >>> 32));
        result = 31 * result + (nombreDisco != null ? nombreDisco.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Disco{" +
                "id=" + id +
                ", idInterprete=" + idInterprete +
                ", nombreDisco='" + nombreDisco + '\'' +
                '}';
    }

    public ContentValues getContentValues(){
        ContentValues cv = new ContentValues();
        //cv.put(Contrato.TablaDisco._ID,this.id);
        cv.put(Contrato.TablaDisco.NOMBREDISCO,this.nombreDisco);
        cv.put(Contrato.TablaDisco.IDINTERPRETE,this.idInterprete);
        return cv;
    }

    public void set(Cursor c){ //A partir del cursor recuperamos el nombre del disco y la id del interprete
        this.id = c.getLong(c.getColumnIndex(Contrato.TablaDisco._ID));
        this.nombreDisco = c.getString(c.getColumnIndex(Contrato.TablaDisco.NOMBREDISCO));
        this.idInterprete= c.getLong(c.getColumnIndex(Contrato.TablaDisco.IDINTERPRETE));
    }

}
