package org.pablo;

import org.pablo.Modelo.Artista;
import org.pablo.Modelo.ExtraerDatosAPI;
import org.pablo.Modelo.MongoDB;
import org.pablo.Modelo.SQL_DB;

import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        ExtraerDatosAPI api = new ExtraerDatosAPI();
        api.obtenerIDArtista("Travis Scott");
        api.obtenerIDArtista("Playboi Carti");
        api.obtenerIDArtista("Don Toliver");
        api.obtenerIDArtista("Gunna");
        api.obtenerIDArtista("Young Thug");
        api.obtenerIDArtista("Future");

        api.guardarAlbumesArtistas();
        api.mostrarAlbumesArtistas();
        MongoDB db = new MongoDB();
        ArrayList<Artista> artistas = api.getArtistas();
        db.insertarVariosArtistasAlbumes(artistas);
        SQL_DB sqlDB = new SQL_DB();
        sqlDB.migrarDatosASQL(artistas);



    }
}