package org.pablo.sql;

import org.pablo.mongo.Modelo.Album;
import org.pablo.mongo.Modelo.Artista;

import java.sql.*;
import java.util.ArrayList;

public class SQL_DB {
    private final String url = "jdbc:mysql://localhost:3306/bd_musica_sql";
    private final String user = "root";
    private final String password = "";

   public SQL_DB() {

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", user, password);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS bd_musica_sql");

            stmt.executeUpdate("USE bd_musica_sql");

            stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS artistas (
                id BIGINT PRIMARY KEY,
                nombre VARCHAR(255) NOT NULL
            )
        """);

            stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS albumes (
                id INT AUTO_INCREMENT PRIMARY KEY,
                nombre VARCHAR(255) NOT NULL,
                id_artista BIGINT,
                CONSTRAINT fk_artista FOREIGN KEY (id_artista) 
                REFERENCES artistas(id) ON DELETE CASCADE
            )
        """);

        } catch (SQLException e) {
            System.err.println("Error al crear la estructura SQL: " + e.getMessage());
        }
    }
    public void migrarDatosASQL(ArrayList<Artista> listaArtistas) {
        String sqlArtista = "INSERT IGNORE INTO artistas (id, nombre) VALUES (?, ?)";
        String sqlAlbum = "INSERT INTO albumes (nombre, id_artista) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            conn.setAutoCommit(false);

            try (PreparedStatement psArtista = conn.prepareStatement(sqlArtista);
                 PreparedStatement psAlbum = conn.prepareStatement(sqlAlbum)) {

                for (Artista artista : listaArtistas) {
                    psArtista.setLong(1, artista.getId());
                    psArtista.setString(2, artista.getNombre());
                    psArtista.executeUpdate();

                    for (Album album : artista.getAlbumes()) {
                        psAlbum.setString(1, album.getNombre());
                        psAlbum.setLong(2, artista.getId());
                        psAlbum.addBatch();
                    }
                    psAlbum.executeBatch();
                }
                conn.commit();
                System.out.println("Migración a SQL completada con éxito.");
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
