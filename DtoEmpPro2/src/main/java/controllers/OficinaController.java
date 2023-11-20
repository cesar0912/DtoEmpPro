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

    public Departamento createDepartamento(Departamento dep) {
        logger.info("Creando Departamento");
        return depRepository.save(dep);
    }

    public Optional<Departamento> getDepartamentoById(UUID uuid) {
        logger.info("Obteniendo Departamento con uuid: " + uuid);
        return depRepository.findById(uuid);
    }

    public Departamento updateDepartamento(Departamento dep) {
        logger.info("Actualizando Departamento con uuid: " + dep.getId());
        return depRepository.save(dep);
    }

    public Boolean deleteDepartamento(Departamento dep) {
        logger.info("Eliminando Departamento con uuid: " + dep.getId());
        return depRepository.delete(dep);
    }

    public List<Empleado> getEmpleados() {
        logger.info("Obteniendo Empleados");
        return empRepository.findAll();
    }

    public Empleado createEmpleado(Empleado emp) {
        logger.info("Creando Empleado");
        return empRepository.save(emp);
    }

    public Optional<Empleado> getEmpleadoById(UUID uuid) {
        logger.info("Obteniendo Empleado con uuid: " + uuid);
        return empRepository.findById(uuid);
    }

    public Empleado updateEmpleado(Empleado emp) {
        logger.info("Actualizando Empleado con uuid: " + emp.getId());
        return empRepository.save(emp);
    }

    public Boolean deleteEmpleado(Empleado emp) {
        logger.info("Eliminando Empleado con uuid: " + emp.getId());
        return empRepository.delete(emp);
    }
    public List<Proyecto> getProyectos() {
        logger.info("Obteniendo Proyectos");
        return proRepository.findAll();
    }

    public Proyecto createProyecto(Proyecto pro) {
        logger.info("Creando Proyecto");
        return proRepository.save(pro);
    }

    public Optional<Proyecto> getProyectoById(UUID uuid) {
        logger.info("Obteniendo Proyecto con uuid: " + uuid);
        return proRepository.findById(uuid);
    }

    public Proyecto updateProyecto(Proyecto pro) {
        logger.info("Actualizando Proyecto con uuid: " + pro.getId());
        return proRepository.save(pro);
    }

    public Boolean deleteProyecto(Proyecto pro) {
        logger.info("Eliminando Proyecto con uuid: " + pro.getId());
        return proRepository.delete(pro);
    }

}
