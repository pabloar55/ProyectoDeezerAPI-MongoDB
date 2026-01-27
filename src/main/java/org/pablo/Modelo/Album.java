package org.pablo.Modelo;

public class Album {
    private Long id;
    private String nombre;
    private Long idArtista;

    public Album(String nombre,  Long idArtista) {
        this.nombre = nombre;
        this.idArtista = idArtista;
    }

    public Album() {
    }

    public Long getId() {
        return id;
    }

    public Long getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(Long idArtista) {
        this.idArtista = idArtista;
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

    @Override
    public String toString() {
        return "Album{'" + nombre + "'}";
    }
}
