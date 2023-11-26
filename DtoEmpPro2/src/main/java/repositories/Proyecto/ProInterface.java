package repositories.Proyecto;

import java.util.UUID;

import models.Empleado;
import models.Proyecto;
import repositories.CrudRepository;

public interface ProInterface extends CrudRepository<Proyecto, UUID>{

	boolean anadir(Proyecto proyecto, Empleado empleado);

	boolean eliminar(Proyecto proyecto, Empleado empleado);

}
