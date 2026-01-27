package org.pablo.Modelo;

import com.mongodb.MongoBulkWriteException;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.InsertManyOptions;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;

public class MongoDB {
    private final MongoClient cliente = MongoClients.create("mongodb://localhost:27017");
    private MongoDatabase bd_musica;
    public MongoDB(){
        PojoCodecProvider pojo = PojoCodecProvider.builder().automatic(true).build();

        CodecRegistry codec = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(pojo));

        bd_musica = cliente.getDatabase("bd_musica").withCodecRegistry(codec);
    }
    public void insertarVariosArtistasAlbumes(ArrayList<Artista> listaArtistas) {
        if (listaArtistas == null || listaArtistas.isEmpty()) {
            System.out.println("La lista de artistas está vacía.");
            return;
        }

        MongoCollection<Artista> collectionArtista = bd_musica.getCollection("artistas", Artista.class);
        MongoCollection<Album> collectionAlbumes = bd_musica.getCollection("albumes", Album.class);
        try {
            InsertManyOptions options = new InsertManyOptions().ordered(false);

            collectionArtista.insertMany(listaArtistas, options);
            ArrayList<Album> listaAlbums = new ArrayList<>();
            for  (Artista artista : listaArtistas) {
               ArrayList<Album> albumes = artista.getAlbumes();
                listaAlbums.addAll(albumes);
            }
            collectionAlbumes.insertMany(listaAlbums, options);

        } catch (MongoBulkWriteException e) {
            System.out.println("Aviso: Algunos artistas ya existían o hubo un error parcial.");
        }
    }
}
