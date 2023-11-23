package repositories.Departamento;

import java.util.List;
import java.util.logging.Logger;

import db.HibernateManager;
import exceptions.DepartamentoException;
import jakarta.persistence.TypedQuery;
import models.Departamento;
import models.Empleado;

public class DepRepositoryImpl implements DepInterface{
	private final Logger logger = Logger.getLogger(DepRepositoryImpl.class.getName());
	
	public List<Departamento> findAll() {
		logger.info("findAll()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        TypedQuery<Departamento> query = hb.getManager().createNamedQuery("Departamento.findAll", Departamento.class);
        List<Departamento> list = query.getResultList();
        hb.close();
        return list;
	}
	@Override
	public boolean save(Departamento entity) {
		logger.info("save()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        hb.getTransaction().begin();
        
        try {
            Empleado jefe = null;
            if (entity.getJefe().getId() != null) { 
                jefe = hb.getManager().find(Empleado.class, entity.getJefe().getId());
            }

            entity.setJefe(jefe);
            Departamento mergedDepartamento = hb.getManager().merge(entity);

            if (mergedDepartamento.getJefe() != null) {
                Empleado jefeActualizado = mergedDepartamento.getJefe();
                jefeActualizado.setDepartamento(mergedDepartamento);
                hb.getManager().merge(jefeActualizado); 
            }

            hb.getTransaction().commit();
            hb.close();
            return true;

        } catch (Exception e) {
            return false;
        } finally {
            if (hb.getTransaction().isActive()) {
                hb.getTransaction().rollback();
            }
        }
	}


	@Override
	public boolean delete(Departamento entity) {
	    logger.info("delete()");
	    HibernateManager hb = HibernateManager.getInstance();
	    hb.open();
	    try {
	        hb.getTransaction().begin();
	        entity = hb.getManager().find(Departamento.class, entity.getId());

	        for (Empleado e : entity.getEmpleados()) {
	            e.setDepartamento(null);
	            hb.getManager().merge(e);
	        }

	        hb.getTransaction().commit();
	        hb.getManager().clear();
	        hb.getTransaction().begin();
	        entity = hb.getManager().find(Departamento.class, entity.getId());
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
