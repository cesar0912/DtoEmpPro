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
import java.util.*;

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

    public Proyecto save(Proyecto entity) {
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();

        try (AutoCloseable resourceManager = hb::close) {
            hb.getTransaction().begin();

            // Manejar relación ManyToMany con Empleado
            Set<Empleado> empleados = entity.getEmpleados();
            for (Empleado empleado : empleados) {
                if (empleado.getId() != null) {
                    if (!existsInDatabase(empleado.getId(), Empleado.class, hb)) {
                        throw new Exception("Empleado con UUID " + empleado.getId() + " no encontrado.");
                    } else {
                        Empleado e = hb.getManager().find(Empleado.class, empleado.getId());
                        entity.agregarEmpleado(e);
                    }
                } else {
                    entity.setEmpleados(null);
                }
            }

            if (entity.getId() == null) {
                hb.getManager().persist(entity);
            } else {
                entity = hb.getManager().merge(entity);
            }

            hb.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            System.out.println("Error al salvar proyecto con uuid: " + entity.getId() + " " + e.getMessage());
            if (hb.getTransaction().isActive()) {
                hb.getTransaction().rollback();
            }
        }

        return null;
    }

    private static <T> boolean existsInDatabase(UUID id, Class<T> entityClass, HibernateManager hb) {
        return hb.getManager().find(entityClass, id) != null;
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
