package repositories.Departamento;

import db.HibernateManager;
import jakarta.persistence.TypedQuery;
import models.*;

import java.lang.System.Logger.Level;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;


public class DepRepositoryImpl implements DepInterface{
	private final Logger logger = Logger.getLogger(DepRepositoryImpl.class.getName());

    @Override
    public List<Departamento> findAll() { 
        logger.info("findAll()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        TypedQuery<Departamento> query = hb.getManager().createNamedQuery("Raqueta.findAll", Departamento.class);
        List<Departamento> list = query.getResultList();
        hb.close();
        return list;
    }

    @Override
    public Optional<Departamento> findById(UUID uuid) {
        logger.info("findById()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        Optional<Departamento> raqueta = Optional.ofNullable(hb.getManager().find(Departamento.class, uuid));
        hb.close();
        return raqueta;
    }

    @Override
    public Departamento save(Departamento entity) {
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
    public Boolean delete(Departamento entity) {
        logger.info("delete()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        try {
            hb.getTransaction().begin();
            // Ojo que borrar implica que estemos en la misma sesión y nos puede dar problemas, por eso lo recuperamos otra vez
            entity = hb.getManager().find(Departamento.class, entity.getId());
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
