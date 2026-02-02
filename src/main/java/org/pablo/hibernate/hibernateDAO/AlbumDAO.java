package org.pablo.hibernate.hibernateDAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.pablo.hibernate.HibernateUtil;
import org.pablo.hibernate.entidades.AlbumEntity;

import java.util.List;

public class AlbumDAO {

    public List<AlbumEntity> buscarPorNombre(String nombre) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL
            String hql = "FROM AlbumEntity WHERE nombre LIKE :busqueda";
            Query<AlbumEntity> query = session.createQuery(hql, AlbumEntity.class);
            query.setParameter("busqueda", "%" + nombre + "%");

            return query.list();
        }
    }
    public void guardarOActualizar(AlbumEntity album) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(album);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    public List<AlbumEntity> listarPorArtista(Long idArtista) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM AlbumEntity WHERE idArtista = :id";
            return session.createQuery(hql, AlbumEntity.class)
                    .setParameter("id", idArtista)
                    .list();
        }
    }
    public void eliminar(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            AlbumEntity album = session.find(AlbumEntity.class, id);
            if (album != null) session.remove(album);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
