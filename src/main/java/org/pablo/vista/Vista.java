package org.pablo.vista;

import org.pablo.api.ExtraerDatosAPI;
import org.pablo.mongo.Modelo.Artista;
import org.pablo.mongo.MongoDB;
import org.pablo.sql.SQL_DB;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Vista extends javax.swing.JFrame {
    private JButton insertarDatosPruebaMongoButton;
    private JButton migrarASQLButton;
    private JPanel panel;
    private JButton buscar;
    private JTextField buscarArtistaAPI;
    private JTextArea textArea1;
    private JButton modificar;
    private JButton borrarButton;
    private JButton Listar;
    private JButton insertarButton;
    private ArrayList<Artista> artistas;
    private Artista buscado;
    public Vista() {

        this.setContentPane(panel);
        this.setTitle("API Deezer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setSize(750, 500);

        migrarASQLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (artistas == null) {
                    JOptionPane.showMessageDialog(null, "Inserta primero los datos " +
                            "predeterminados de prueba en Mongo para migrar a SQL");
                    return;
                }
                SQL_DB sqlDB = new SQL_DB();
                sqlDB.migrarDatosASQL(artistas);
            }
        });

        insertarDatosPruebaMongoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText("");
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
                artistas = api.getArtistas();
                db.insertarVariosArtistasAlbumes(artistas);
                System.out.println("insertados");
            }
        });
        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText("");
                ExtraerDatosAPI api = new ExtraerDatosAPI();
                String artista = buscarArtistaAPI.getText();
                api.obtenerIDArtista(artista);
                api.guardarAlbumesArtistas();
                buscado = api.getArtistas().get(0);
                textArea1.setText(buscado.toString());
            }
        });
        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MongoDB db = new MongoDB();
                if (buscado != null) {
                    boolean eliminado = db.getArtistaDAO().eliminarArtista(buscado.getId());

                    if (eliminado) {
                        JOptionPane.showMessageDialog(null, "Artista eliminado de Mongo");
                    }
                }
            }
        });
        modificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MongoDB db = new MongoDB();
                if (buscado != null) {

                    String nuevoNombre = JOptionPane.showInputDialog("Nuevo nombre:", buscado.getNombre());
                    if (nuevoNombre != null) {
                        buscado.setNombre(nuevoNombre);
                        db.getArtistaDAO().modificarArtista(buscado);
                        JOptionPane.showMessageDialog(null, "Artista actualizado");
                    }
                }
            }
        });

        Listar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MongoDB mongo = new MongoDB();
                List<Artista> listaActualizada = mongo.getArtistaDAO().obtenerArtistasConLookup();
                textArea1.setText("");
                for (Artista a : listaActualizada) {
                    textArea1.append(a.getNombre()+"\n");
                }
            }
        });
        insertarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MongoDB mongo = new MongoDB();
                mongo.getArtistaDAO().guardar(buscado);
                System.out.println("insertado");
            }
        });
    }
}
