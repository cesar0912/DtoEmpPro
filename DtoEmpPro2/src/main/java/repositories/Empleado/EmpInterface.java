package repositories.Empleado;

import java.util.UUID;

import models.Empleado;
import models.Proyecto;
import repositories.CrudRepository;

public interface EmpInterface extends CrudRepository<Empleado, UUID>{

	boolean eliminar(Proyecto proyecto, Empleado empleado);

	boolean anadir(Proyecto proyecto, Empleado empleado);

}
