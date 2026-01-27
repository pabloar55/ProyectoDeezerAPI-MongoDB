package org.pablo.Modelo;

import java.util.ArrayList;

public class Artista {
    private Long id;
    private String nombre;
    private ArrayList<Album> albumes;

    public Artista(Long id, String nombre, ArrayList<Album> albumes) {
        this.id = id;
        this.nombre = nombre;
        this.albumes = albumes;
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
        return "Artista: " + nombre + " (ID: " + id + ") | Álbumes: " + albumes;
    }
}
