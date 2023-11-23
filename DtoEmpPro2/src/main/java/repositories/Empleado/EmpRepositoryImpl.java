package repositories.Empleado;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.Transaction;

import db.HibernateManager;
import exceptions.DepartamentoException;
import exceptions.EmpleadoException;
import jakarta.persistence.TypedQuery;
import models.Departamento;
import models.Empleado;

public class EmpRepositoryImpl implements EmpInterface{
	private final Logger logger = Logger.getLogger(EmpRepositoryImpl.class.getName());
	@Override
	public List<Empleado> findAll() {
		logger.info("findAll()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        TypedQuery<Empleado> query = hb.getManager().createNamedQuery("Empleado.findAll", Empleado.class);
        List<Empleado> list = query.getResultList();
        hb.close();
        return list;
	}

	@Override
	public boolean save(Empleado entity) {
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

}