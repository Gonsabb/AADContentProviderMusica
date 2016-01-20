package com.example.gonzalo.aadcontentprovidermusica.clasespojo;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.gonzalo.aadcontentprovidermusica.Contrato;

/**
 * Created by Gonzalo on 18/01/2016.
 */
public class Interprete {

    private long id;
    private String nombreInterprete;

    public Interprete() {
    }

    public Interprete(int id, String nombreDisco) {
        this.id = id;
        this.nombreInterprete = nombreDisco;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getnombreInterprete() {
        return nombreInterprete;
    }

    public void setnombreInterprete(String nombreInterprete) {
        this.nombreInterprete = nombreInterprete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Interprete that = (Interprete) o;

        if (id != that.id) return false;
        return !(nombreInterprete != null ? !nombreInterprete.equals(that.nombreInterprete) : that.nombreInterprete != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (nombreInterprete != null ? nombreInterprete.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Interprete{" +
                "id=" + id +
                ", nombreDisco='" + nombreInterprete + '\'' +
                '}';
    }

    public ContentValues getContentValues(){
        ContentValues cv = new ContentValues();
        //cv.put(Contrato.TablaInterprete._ID,this.id);
        cv.put(Contrato.TablaInterprete.NOMBREINTERPRETE,this.nombreInterprete);
        return cv;
    }

    public void set(Cursor c){ //A partir del cursor recuperamos el nombre del interprete
        this.id = c.getLong(c.getColumnIndex(Contrato.TablaInterprete._ID));
        this.nombreInterprete = c.getString(c.getColumnIndex(Contrato.TablaInterprete.NOMBREINTERPRETE));
    }
}
