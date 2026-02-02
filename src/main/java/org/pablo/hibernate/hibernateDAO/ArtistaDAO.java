package org.pablo.hibernate.hibernateDAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.pablo.hibernate.HibernateUtil;
import org.pablo.hibernate.entidades.ArtistaEntity;

import java.util.List;

public class ArtistaDAO {

    public List<ArtistaEntity> buscarPorNombre(String nombre) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Usamos HQL:
            String hql = "FROM ArtistaEntity WHERE nombre LIKE :busqueda";
            Query<ArtistaEntity> query = session.createQuery(hql, ArtistaEntity.class);
            query.setParameter("busqueda", "%" + nombre + "%");

            return query.list();
        }
    }

    public ArtistaEntity buscarPorId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(ArtistaEntity.class, id);
        }
    }
    public List<ArtistaEntity> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM ArtistaEntity ", ArtistaEntity.class).list();
        }
    }
    public void guardarOActualizar(ArtistaEntity artista) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.merge(artista);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    public void eliminar(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            ArtistaEntity artista = session.find(ArtistaEntity.class, id);
            if (artista != null) {
                session.remove(artista);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
