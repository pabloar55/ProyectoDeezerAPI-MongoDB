package org.pablo;

import com.mongodb.client.MongoClient;
import org.pablo.Modelo.ExtraerDatosAPI;
import org.pablo.Modelo.MongoDB;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        ExtraerDatosAPI a = new ExtraerDatosAPI();
        a.obtenerIDArtista("Travis Scott");
        a.obtenerIDArtista("Playboi Carti");
        a.obtenerIDArtista("Don Toliver");
        a.obtenerIDArtista("Gunna");
        a.obtenerIDArtista("Young Thug");
        a.obtenerIDArtista("Future");

        a.guardarAlbumesArtistas();
        a.mostrarAlbumesArtistas();
        MongoDB db = new MongoDB();
        db.insertarVariosArtistasAlbumes(a.getArtistas());


    }
}