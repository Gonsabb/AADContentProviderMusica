package com.example.gonzalo.aadcontentprovidermusica.clasespojo;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.gonzalo.aadcontentprovidermusica.Contrato;

/**
 * Created by Gonzalo on 18/01/2016.
 */
public class Cancion {
    private long id, idDisco;
    private String titulo;

    public Cancion() {
    }

    public Cancion(long id, String titulo, long idDisco) {
        this.id = id;
        this.titulo = titulo;
        this.idDisco = idDisco;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdDisco() {
        return idDisco;
    }

    public void setIdDisco(long idDisco) {
        this.idDisco = idDisco;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cancion cancion = (Cancion) o;

        if (id != cancion.id) return false;
        if (idDisco != cancion.idDisco) return false;
        return !(titulo != null ? !titulo.equals(cancion.titulo) : cancion.titulo != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (idDisco ^ (idDisco >>> 32));
        result = 31 * result + (titulo != null ? titulo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Cancion{" +
                "id=" + id +
                ", idDisco=" + idDisco +
                ", titulo='" + titulo + '\'' +
                '}';
    }

    public ContentValues getContentValues(){
        ContentValues cv = new ContentValues();
        //cv.put(Contrato.TablaCancion._ID,this.id);
        cv.put(Contrato.TablaCancion.TITULO,this.titulo);
        cv.put(Contrato.TablaCancion.IDDISCO,this.idDisco);
        return cv;
    }

    public void set(Cursor c){ //A partir del cursor recuperamos el titulo y la id del disco
        this.id = c.getLong(c.getColumnIndex(Contrato.TablaCancion._ID));
        this.titulo = c.getString(c.getColumnIndex(Contrato.TablaCancion.TITULO));
        this.idDisco= c.getLong(c.getColumnIndex(Contrato.TablaCancion.IDDISCO));
    }
}
