package org.pablo.api;

import org.json.JSONArray;
import org.json.JSONObject;
import org.pablo.mongo.Modelo.Album;
import org.pablo.mongo.Modelo.Artista;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;


public class ExtraerDatosAPI {
    private final String baseURL = "https://api.deezer.com";
    private HashMap<Long, String> artistasNombreID;
    private HttpClient client;
    private ArrayList<Artista> artistas;

    public ExtraerDatosAPI() {
        client = HttpClient.newHttpClient();
        artistasNombreID = new HashMap<>();
        artistas = new ArrayList<>();
    }

    public void obtenerIDArtista(String nombre) {
        String nombreFormateado = URLEncoder.encode(nombre, StandardCharsets.UTF_8);
        JSONObject jsonID = consultaAPI(baseURL + "/search/artist?q=" + nombreFormateado);
        guardarArtistaID(jsonID);
    }

    public void guardarAlbumesArtistas() {
        for (Long aux : artistasNombreID.keySet()) {
            String queryAlbumes = baseURL + "/artist/" + aux + "/albums";
            guardarAlbumesArtista(consultaAPI(queryAlbumes), aux);
        }
    }

    private JSONObject consultaAPI(String query) {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(query)).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONObject(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void guardarAlbumesArtista(JSONObject albumes, Long idArtista) {
        JSONArray data = albumes.getJSONArray("data");
        ArrayList<Album> albumesList = new ArrayList<>();
        for (int i = 0; i < data.length(); i++) {
            if (data.getJSONObject(i).getString("record_type").equals("album"))
                albumesList.add(new Album(data.getJSONObject(i).getString("title"), idArtista));
        }
        artistas.add(new Artista(idArtista, artistasNombreID.get(idArtista), albumesList));
    }

    private void guardarArtistaID(JSONObject jsonObject) {
        JSONArray data = jsonObject.getJSONArray("data");
        if (!data.isEmpty()) {
            artistasNombreID.put(data.getJSONObject(0).getLong("id"), data.getJSONObject(0).getString("name"));
        } else {
            System.out.println("No se encontraron canciones.");
        }
    }
    public void mostrarAlbumesArtistas() {
          for (Artista a : artistas) {
              System.out.println(a.toString());
          }
    }

    public ArrayList<Artista> getArtistas() {
        return artistas;
    }
}
