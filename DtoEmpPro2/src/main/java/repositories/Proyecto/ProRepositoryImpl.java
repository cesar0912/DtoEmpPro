package repositories.Proyecto;

import db.HibernateManager;
import jakarta.persistence.TypedQuery;
import models.*;

import java.lang.System.Logger.Level;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import org.hibernate.Hibernate;


public class ProRepositoryImpl implements ProInterface{
	private final Logger logger = Logger.getLogger(ProRepositoryImpl.class.getName());

	@Override
    public List<Proyecto> findAll() {  
        logger.info("findAll()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        TypedQuery<Proyecto> query = hb.getManager().createNamedQuery("Proyecto.findAll", Proyecto.class);
        List<Proyecto> list = query.getResultList();
        list.forEach(p -> Hibernate.initialize(p.getEmpleados()));

        hb.close();
        return list;
    }

    @Override
    public Optional<Proyecto> findById(UUID uuid) {
        logger.info("findById()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        Optional<Proyecto> raqueta = Optional.ofNullable(hb.getManager().find(Proyecto.class, uuid));
        hb.close();
        return raqueta;
    }

    @Override
    public Proyecto save(Proyecto entity) {
        logger.info("save()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        hb.getTransaction().begin();

        try {
            hb.getManager().merge(entity);
            hb.getTransaction().commit();
            hb.close();
            return entity;

        } catch (Exception e) {
            System.out.println("Error al salvar raqueta con uuid: " + entity.getId() + "\n" + e.getMessage());
        } finally {
            if (hb.getTransaction().isActive()) {
                hb.getTransaction().rollback();
               
            }
        } 
        return null;
    }

    @Override
    public Boolean delete(Proyecto entity) {
        logger.info("delete()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        try {
            hb.getTransaction().begin();
            // Ojo que borrar implica que estemos en la misma sesión y nos puede dar problemas, por eso lo recuperamos otra vez
            entity = hb.getManager().find(Proyecto.class, entity.getId());
            hb.getManager().remove(entity);
            hb.getTransaction().commit();
            hb.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error al eliminar tenista con uuid: " + entity.getId() + " - " + e.getMessage());
        } finally {
            if (hb.getTransaction().isActive()) {
                hb.getTransaction().rollback();
            }
        }
        return false;
    }

}
