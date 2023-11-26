package repositories.Proyecto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import db.HibernateManager;
import exceptions.DepartamentoException;
import exceptions.EmpleadoException;
import exceptions.ProyectoException;
import jakarta.persistence.TypedQuery;
import models.Departamento;
import models.Empleado;
import models.Proyecto;

public class ProRepositoryImpl implements ProInterface{
	private final Logger logger = Logger.getLogger(ProRepositoryImpl.class.getName());
	
	public List<Proyecto> findAll() {
	    HibernateManager hb = HibernateManager.getInstance();

	    // Abre la transacción
	    hb.open();

	    TypedQuery<Proyecto> proyectos = hb.getManager().createNamedQuery("Proyecto.findAll", Proyecto.class);
	    List<Proyecto> p=proyectos.getResultList();
	    for (Proyecto proyecto : p) {
	        proyecto.getEmpleados().size(); 
	    }
	    hb.close();

	    return p;
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
	public boolean save(Proyecto entity) {
		logger.info("save()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        hb.getTransaction().begin();

        try {
            hb.getManager().merge(entity);
            hb.getTransaction().commit();
            hb.close();
            return true;

        } catch (Exception e) {
            throw new ProyectoException("Error al guardar proyecto con uuid: " + entity.getId() + "\n" + e.getMessage());
        } finally {
            if (hb.getTransaction().isActive()) {
                hb.getTransaction().rollback();
            }
        }
	}
	@Override
	public boolean update(Proyecto entity) {
		logger.info("save()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        hb.getTransaction().begin();

        try {
        	if(hb.getManager().find(Proyecto.class, entity.getId())==null) {
        		throw new ProyectoException("Error al salvar raqueta con uuid: " + entity.getId() + "\n");
        	}
        	Proyecto p=hb.getManager().find(Proyecto.class, entity.getId());
        	p.setNombre(entity.getNombre());
            hb.getManager().merge(p);
            hb.getTransaction().commit();
            hb.close();
            return true;

        } catch (Exception e) {
            throw new ProyectoException("Error al modificar proyecto con uuid: " + entity.getId() + "\n" + e.getMessage());
        } finally {
            if (hb.getTransaction().isActive()) {
                hb.getTransaction().rollback();
            }
        }
	}
	
	
	
	@Override
	public boolean delete(Proyecto entity) {
		logger.info("delete()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        try {
            hb.getTransaction().begin();
            entity = hb.getManager().find(Proyecto.class, entity.getId());
            hb.getManager().remove(entity);
            hb.getTransaction().commit();
            hb.close();
            return true;
        } catch (Exception e) {
            throw new DepartamentoException("Error al eliminar Proyecto con uuid: " + entity.getId() + " - " + e.getMessage());
        } finally {
            if (hb.getTransaction().isActive()) {
                hb.getTransaction().rollback();
            }
        }
	}
	public boolean anadir(Proyecto proyecto, Empleado empleado) {
		logger.info("añadirEmp()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        try {
            hb.getTransaction().begin();
            proyecto = hb.getManager().find(Proyecto.class, proyecto.getId());
            empleado=hb.getManager().find(Empleado.class,empleado.getId());
            proyecto.add(empleado);
            hb.getManager().merge(proyecto);
            hb.getTransaction().commit();
            hb.close();
            return true;
        } catch (Exception e) {
            throw new DepartamentoException("Error al añadir empleado a Proyecto con uuid: " + proyecto.getId() + " - " + e.getMessage());
        } finally {
            if (hb.getTransaction().isActive()) {
                hb.getTransaction().rollback();
            }
        }
	}
	public boolean eliminar(Proyecto proyecto, Empleado empleado) {
		logger.info("añadirEmp()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        try {
            hb.getTransaction().begin();
            proyecto = hb.getManager().find(Proyecto.class, proyecto.getId());
            empleado=hb.getManager().find(Empleado.class,empleado.getId());
            proyecto.remove(empleado);
            hb.getManager().merge(proyecto);
            hb.getTransaction().commit();
            hb.close();
            return true;
        } catch (Exception e) {
            throw new DepartamentoException("Error al eliminar empleado de un Proyecto con uuid: " + proyecto.getId() + " - " + e.getMessage());
        } finally {
            if (hb.getTransaction().isActive()) {
                hb.getTransaction().rollback();
            }
        }
	}

}