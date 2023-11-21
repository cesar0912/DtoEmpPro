package repositories.Departamento;

import db.HibernateManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import models.*;

import java.lang.System.Logger.Level;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.Set;

public class DepRepositoryImpl implements DepInterface {
	private final Logger logger = Logger.getLogger(DepRepositoryImpl.class.getName());

	@Override
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
	public Optional<Departamento> findById(UUID uuid) {
		logger.info("findById()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		Optional<Departamento> dep = Optional.ofNullable(hb.getManager().find(Departamento.class, uuid));
		hb.close();
		return dep;
	}

	public Departamento save(Departamento entity) {
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();

		try (AutoCloseable resourceManager = hb::close) {
			hb.getTransaction().begin();

			Set<Empleado> empleados = entity.getEmpleados();
			Set<Empleado> empleadosToAdd = new HashSet<>();
			for (Empleado empleado : empleados) {
				if (empleado.getId() != null) {
					if (!existsInDatabase(empleado.getId(), Empleado.class, hb)) {
						throw new Exception("Empleado con UUID " + empleado.getId() + " no encontrado.");
					} else {
						Empleado e = hb.getManager().find(Empleado.class, empleado.getId());
						empleadosToAdd.add(e);
						e.setDepartamento(entity);
					}
				}
			}
			entity.setEmpleados(empleadosToAdd);

			System.out.println("Empleados en el departamento después de la actualización: " + entity.getEmpleados());
			if (entity.getId() == null) {
				hb.getManager().persist(entity);
			} else {
				entity = hb.getManager().merge(entity);
			}

			hb.getTransaction().commit();
			return entity;
		} catch (Exception e) {
			System.out.println("Error al salvar departamento con uuid: " + entity.getId() + " " + e.getMessage());
			if (hb.getTransaction().isActive()) {
				hb.getTransaction().rollback();
			}
		} finally {
			hb.close();
		}

		return null;
	}

	private static <T> boolean existsInDatabase(UUID id, Class<T> entityClass, HibernateManager hb) {
		return hb.getManager().find(entityClass, id) != null;
	}

	@Override
	public Boolean delete(Departamento entity) {
		logger.info("delete()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		try {
			hb.getTransaction().begin();
			entity = hb.getManager().find(Departamento.class, entity.getId());
			hb.getManager().remove(entity);
			hb.getTransaction().commit();
			hb.close();
			return true;
		} catch (Exception e) {
			System.out.println("Error al eliminar departamento con uuid: " + entity.getId() + " - " + e.getMessage());
		} finally {
			if (hb.getTransaction().isActive()) {
				hb.getTransaction().rollback();
			}
		}
		return false;
	}

}
