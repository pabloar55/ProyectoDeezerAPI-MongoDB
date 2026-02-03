package org.pablo.mongo.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.result.DeleteResult;
import org.bson.conversions.Bson;
import org.pablo.mongo.Modelo.Artista;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.lookup;
import static com.mongodb.client.model.Filters.eq;

public class ArtistaDAO {
    private final MongoCollection<Artista> collectionArtista;
    private final MongoDatabase db;

    public ArtistaDAO(MongoDatabase db) {
        this.db = db;
        this.collectionArtista = db.getCollection("artistas", Artista.class);
    }


    public List<Artista> obtenerArtistasConLookup() {
        List<Artista> resultados = new ArrayList<>();
        Bson lookupStage = lookup("albumes", "_id", "idArtista", "albumes");
        collectionArtista.aggregate(List.of(lookupStage))
                .into(resultados);

        return resultados;
    }

    public void guardar(Artista artista) {
        collectionArtista.replaceOne(
                eq("_id", artista.getId()),
                artista,
                new ReplaceOptions().upsert(true)
        );
    }

    public boolean eliminarArtista(Long id) {
        DeleteResult result = collectionArtista.deleteOne(eq("_id", id));
        return result.getDeletedCount() > 0;
    }

    public void modificarArtista(Artista artista) {
        collectionArtista.replaceOne(eq("_id", artista.getId()), artista);
    }
}
