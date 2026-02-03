package org.pablo.mongo.Modelo;

import org.bson.codecs.pojo.annotations.BsonId;

import java.util.ArrayList;

public class Artista {
    @BsonId
    private Long id;
    private String nombre;
    private ArrayList<Album> albumes;

    public Artista(Long id, String nombre, ArrayList<Album> albumes) {
        this.id = id;
        this.nombre = nombre;
        this.albumes = albumes;
    }

    public Artista() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Album> getAlbumes() {
        return albumes;
    }

    public void setAlbumes(ArrayList<Album> albumes) {
        this.albumes = albumes;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("------------------------------\n");
        sb.append("Albumes:\n");

        if (albumes != null && !albumes.isEmpty()) {
            for (Album album : albumes) {
                sb.append(" • ").append(album.getNombre()).append("\n");
            }
        }
        return sb.toString();
    }
}
