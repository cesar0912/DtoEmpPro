package controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import models.Departamento;
import models.Empleado;
import models.Proyecto;
import repositories.Departamento.DepInterface;
import repositories.Empleado.EmpInterface;
import repositories.Proyecto.ProInterface;

public class OficinaController {
	
	private final Logger logger = Logger.getLogger(OficinaController.class.getName());
	
	private final DepInterface depRepository;
    private final EmpInterface empRepository;
    private final ProInterface proRepository;

	public OficinaController(DepInterface depRepository, EmpInterface empRepository, ProInterface proRepository) {
        this.depRepository = depRepository;
        this.empRepository = empRepository;
        this.proRepository = proRepository;
    }

	public List<Departamento> getDepartamentos() {
        logger.info("Obteniendo Departamentos");
        return depRepository.findAll();
    }
	public Optional<Departamento> getDepartamentoPorId(UUID id) {
        logger.info("Obteniendo Departamentos");
        return depRepository.findById(id);
    }
    public boolean createDepartamento(Departamento dep) {
        logger.info("Creando Departamento");
        return depRepository.save(dep);
    }

    public Boolean updateDepartamento(Departamento dep) {
        logger.info("Actualizando Departamento con uuid: " + dep.getId());
        return depRepository.update(dep);
    }

    public Boolean deleteDepartamento(Departamento dep) {
        logger.info("Eliminando Departamento con uuid: " + dep.getId());
        return depRepository.delete(dep);
    }

    public List<Empleado> getEmpleados() {
        logger.info("Obteniendo Empleados");
        return empRepository.findAll();
    }
    public Optional<Empleado> getEmpleadoPorId(UUID id) {
        logger.info("Obteniendo Empleado");
        return empRepository.findById(id);
    }
    public Boolean createEmpleado(Empleado emp) {
        logger.info("Creando Empleado");
        return empRepository.save(emp);
    }


    public Boolean updateEmpleado(Empleado emp) {
        logger.info("Actualizando Empleado con uuid: " + emp.getId());
        return empRepository.update(emp);
    }

    public Boolean deleteEmpleado(Empleado emp) {
        logger.info("Eliminando Empleado con uuid: " + emp.getId());
        return empRepository.delete(emp);
    }
    public List<Proyecto> getProyectos() {
        logger.info("Obteniendo Proyectos");
        return proRepository.findAll();
    }
    public Optional<Proyecto> getProyectoPorId(UUID id) {
        logger.info("Obteniendo Proyecto");
        return proRepository.findById(id);
    }

    public Boolean createProyecto(Proyecto pro) {
        logger.info("Creando Proyecto");
        return proRepository.save(pro);
    }


    public Boolean updateProyecto(Proyecto pro) {
        logger.info("Actualizando Proyecto con uuid: " + pro.getId());
        return proRepository.update(pro);
    }

    public Boolean deleteProyecto(Proyecto pro) {
        logger.info("Eliminando Proyecto con uuid: " + pro.getId());
        return proRepository.delete(pro);
    }

	public boolean anadirEmpPro(Proyecto proyecto, Empleado empleado) {
		logger.info("Añadiendo Empleado a un Proyecto con uuid: " + proyecto.getId()+"emp: "+empleado.getId());
        return proRepository.anadir(proyecto,empleado);
	}

	public boolean deleteEmpPro(Proyecto proyecto, Empleado empleado) {
		logger.info("Eliminando Empleado a un Proyecto con uuid: " + proyecto.getId()+"emp: "+empleado.getId());
        return proRepository.eliminar(proyecto,empleado);
	}

	public boolean deleteProEmp(Proyecto proyecto, Empleado empleado) {
		logger.info("Eliminando un Proyecto a un Empleado con uuid: " + proyecto.getId()+"emp: "+empleado.getId());
        return empRepository.eliminar(proyecto,empleado);
	}

	public boolean anadirProEmp(Proyecto proyecto, Empleado empleado) {
		logger.info("Añadiendo un Proyecto a un Empleado con uuid: " + proyecto.getId()+"emp: "+empleado.getId());
        return empRepository.anadir(proyecto,empleado);
	}

}
