package repositories.Empleado;

import db.HibernateManager;
import jakarta.persistence.TypedQuery;
import models.*;

import java.lang.System.Logger.Level;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import org.hibernate.Hibernate;
import java.util.*
;
public class EmpRepositoryImpl implements EmpInterface{
	private final Logger logger = Logger.getLogger(EmpRepositoryImpl.class.getName());

	@Override
	public List<Empleado> findAll() {  
	    logger.info("findAll()");
	    HibernateManager hb = HibernateManager.getInstance();
	    hb.open();

	    TypedQuery<Empleado> query = hb.getManager().createNamedQuery("Empleado.findAll", Empleado.class);
	    List<Empleado> list = query.getResultList();

	    TypedQuery<Empleado> fetchQuery = hb.getManager().createQuery(
	            "SELECT e FROM Empleado e LEFT JOIN FETCH e.departamento LEFT JOIN FETCH e.proyectos",
	            Empleado.class
	    );

	    list = fetchQuery.getResultList();	    hb.close();
	    return list;
	}

    @Override
    public Optional<Empleado> findById(UUID uuid) {
        logger.info("findById()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        Optional<Empleado> empleado = Optional.ofNullable(hb.getManager().find(Empleado.class, uuid));

        empleado.ifPresent(e -> Hibernate.initialize(e.getProyectos()));

        hb.close();
        return empleado;
    }

    public Empleado save(Empleado entity) {
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();

        try (AutoCloseable resourceManager = hb::close) {
            hb.getTransaction().begin();

            // Manejar relación ManyToOne con Departamento
            if (entity.getDepartamento() != null && entity.getDepartamento().getId() != null) {
                Departamento existingDepartment = hb.getManager().find(Departamento.class, entity.getDepartamento().getId());
                if (existingDepartment != null) {
                    entity.setDepartamento(existingDepartment);
                } else {
                    throw new Exception("Departamento con UUID " + entity.getDepartamento().getId() + " no encontrado.");
                }
            } else {
                entity.setDepartamento(null);
            }

            // Manejar relación ManyToMany con Proyecto
            Set<Proyecto> proyectos = entity.getProyectos();
            for (Proyecto proyecto : proyectos) {
                if (proyecto.getId() != null) {
                    if (!existsInDatabase(proyecto.getId(), Proyecto.class, hb)) {
                        throw new Exception("Proyecto con UUID " + proyecto.getId() + " no encontrado.");
                    } else {
                        Proyecto p = hb.getManager().find(Proyecto.class, proyecto.getId());
                        entity.addProyecto(p);
                    }
                } else {
                    entity.setProyectos(null);
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
            System.out.println("Error al salvar empleado con uuid: " + entity.getId() + " " + e.getMessage());
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
    public Boolean delete(Empleado entity) {
        logger.info("delete()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        try {
            hb.getTransaction().begin();
            entity = hb.getManager().find(Empleado.class, entity.getId());
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
