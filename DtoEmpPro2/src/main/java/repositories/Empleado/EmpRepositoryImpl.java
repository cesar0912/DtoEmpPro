package repositories.Empleado;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import org.hibernate.Hibernate;

import db.HibernateManager;
import exceptions.DepartamentoException;
import exceptions.EmpleadoException;
import jakarta.persistence.TypedQuery;
import models.Departamento;
import models.Empleado;
import models.Proyecto;

public class EmpRepositoryImpl implements EmpInterface{
	private final Logger logger = Logger.getLogger(EmpRepositoryImpl.class.getName());
	@Override
	public List<Empleado> findAll() {
	    HibernateManager hb = HibernateManager.getInstance();

	    // Abre la transacción
	    hb.open();

	    TypedQuery<Empleado> empleados = hb.getManager().createNamedQuery("Empleado.findAll", Empleado.class);
	    List<Empleado> e=empleados.getResultList();
	    for (Empleado empleado : e) {
	        empleado.getProyectos().size(); 
	    }
	    hb.close();

	    return e;
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
	@Override
	public boolean save(Empleado entity) {
		logger.info("save()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        hb.getTransaction().begin();

        try {
        	if(entity.getDepartamento()!=null) {
        		Departamento departamento = null;
                departamento = hb.getManager().find(Departamento.class, entity.getDepartamento().getId());
                entity.setDepartamento(departamento);
        	}
            hb.getManager().merge(entity);
            hb.getTransaction().commit();
            hb.close();
            return true;

        } catch (Exception e) {
            throw new EmpleadoException("Error al salvar empleado con uuid: " + entity.getId() + "\n" + e.getMessage());
        } finally {
            if (hb.getTransaction().isActive()) {
                hb.getTransaction().rollback();
            }
        }
	}
	//caso de que el empleado actualizado sea jefe y se le pase el mismo departamento
	public boolean update(Empleado entity) {
		logger.info("update()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        hb.getTransaction().begin();

        try {
        	if(hb.getManager().find(Empleado.class, entity.getId())==null) {
	        	throw new EmpleadoException("Error al actualizar Empleado. El empleado no existe.");
	        }
        	if(entity.getDepartamento()!=null && entity.getDepartamento().getId()!=null) {
        		Departamento departamento = null;
                departamento = hb.getManager().find(Departamento.class, entity.getDepartamento().getId());
                
                if(departamento!=null) {
                	if(departamento.getJefe().getId().equals(entity.getId())) {
                		entity.setDepartamento(departamento);
                	}else {
		                entity.setDepartamento(departamento);
		                Empleado e = hb.getManager().find(Empleado.class, entity.getId());
		    	        Departamento departamentoExistente = e.getDepartamento();
		    	        if(departamentoExistente!=null) {
		    	        	departamentoExistente.setJefe(null);
		    	        	hb.getManager().merge(departamentoExistente);
		    	        	hb.getTransaction().commit();
		    	        	hb.getManager().clear();
		    	        	hb.getTransaction().begin();
		    	        }
		                
	                }
                }
        	}else {
        		Empleado e = hb.getManager().find(Empleado.class, entity.getId());
    	        Departamento departamento = e.getDepartamento();
    	        if(departamento!=null) {
    	        	departamento.setJefe(null);
    	        	hb.getManager().merge(departamento);
    	        	hb.getTransaction().commit();
    	        	hb.getManager().clear();
    	        	hb.getTransaction().begin();
    	        }
                
        		
        	}
            hb.getManager().merge(entity);
            hb.getTransaction().commit();
            hb.close();
            return true;

        } catch (Exception e) {
            throw new EmpleadoException("Error al salvar empleado con uuid: " + entity.getId() + "\n" + e.getMessage());
        } finally {
            if (hb.getTransaction().isActive()) {
                hb.getTransaction().rollback();
            }
        }
	}
	@Override
	public boolean delete(Empleado entity) {
	    logger.info("delete()");
	    HibernateManager hb = HibernateManager.getInstance();
	    hb.open();
	    try {
	        hb.getTransaction().begin();
	        entity = hb.getManager().find(Empleado.class, entity.getId());
	        Departamento departamento = entity.getDepartamento();
	        if(departamento!=null) {
	        	departamento.setJefe(null);
	        	hb.getManager().merge(departamento);
	        	hb.getTransaction().commit();
	        	hb.getManager().clear();
	        	hb.getTransaction().begin();
	        }

	        entity = hb.getManager().find(Empleado.class, entity.getId());
	        hb.getManager().remove(entity);

	        hb.getTransaction().commit();

	        return true;
	    } catch (Exception e) {
	        throw new DepartamentoException("Error al eliminar tenista con uuid: " + entity.getId() + " - " + e.getMessage());
	    } finally {
	        if (hb.getTransaction().isActive()) {
	            hb.getTransaction().rollback();
	        }
	        hb.close();
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
            empleado.add(proyecto);
            hb.getManager().merge(empleado);
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
            empleado.remove(proyecto);
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