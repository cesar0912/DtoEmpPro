
import java.util.logging.*;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import controllers.OficinaController;
import io.IO;
import models.Empleado;
import models.Proyecto;
import models.Departamento;
import repositories.Departamento.DepRepositoryImpl;
import repositories.Empleado.EmpRepositoryImpl;
import repositories.Proyecto.ProRepositoryImpl;

public class Main {
	static EntityManager em=null;
	static OficinaController controller = new OficinaController(
                new DepRepositoryImpl(),
                new EmpRepositoryImpl(),
                new ProRepositoryImpl()
                 
        );

	public static void main(String[] args) {
		Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);



		em = Persistence.createEntityManagerFactory("unidad-persistencia").createEntityManager();

		showResult("Inicial");
		em.getTransaction().begin();

		List<String> opciones = List.of("1: Mostrar departamentos", "2: Mostrar empleados","3: Mostrar proyectos",
				"4: Añadir departamento","5: Añadir empleado","6: Añadir proyectos\n7: Eliminar departamento", "8: Eliminar empleado", "9: Eliminar proyecto",
				"10: Modificar departamento","11: Modificar empleado","12: Modificar proyecto\n13: Salir");
		
		while (true) {
			System.out.println(opciones);
			switch (IO.readInt()) {
			case 1:
				listarDepartamentos();
				break;
			case 2:
				listarEmpleados();
				break;
			case 3:
				var proyectos = controller.getProyectos();
				proyectos.forEach(System.out::println);
				break;
			case 4:
				anadirDep();
				break;
			case 5:
				anadirEmp();
				break;
			case 6:
				controller.createProyecto(anadirPro());
				break;
			case 7:
				controller.deleteDepartamento(eliminarDep());
				break;
			case 8:
				controller.deleteEmpleado(eliminarEmp());
				break;
			case 9:
				controller.deleteProyecto(eliminarPro());
				break;
			/*case 10:
				modificar(dep, emp);
				break;
			case 11:
				modificar(emp, dep,pro);
				break;
			case 12:
				modificar(pro, emp);
				break;
			case 13:
				cerrar(dep, emp, pro);
				return;*/
			default:
			}
		}

	}
	private static void listarEmpleados() {
		List<Empleado> empleados = controller.getEmpleados().stream()
                .sorted(Comparator.comparing(Empleado::getNombre))
                .collect(Collectors.toList());
        empleados.forEach(System.out::println);
	}
	private static void listarDepartamentos() {
		List<Departamento> departamentos = controller.getDepartamentos().stream()
                .sorted(Comparator.comparing(Departamento::getNombre))
                .collect(Collectors.toList());
        departamentos.forEach(System.out::println);
	}

	private static Departamento eliminarDep() {
		IO.print("UUID ? ");
		String id = IO.readString();
		UUID uuid=UUID.fromString(id);
		return new Departamento(uuid);
	}
	private static Empleado eliminarEmp() {
		IO.print("UUID ? ");
		String id = IO.readString();
		UUID uuid=UUID.fromString(id); 
		return new Empleado(uuid);
	}
	private static Proyecto eliminarPro() {
		IO.print("UUID ? ");
		String id = IO.readString();
		UUID uuid=UUID.fromString(id); 
		return new Proyecto(uuid);
	}
	private static Proyecto anadirPro() {
		IO.print("Nombre ? ");
		String nombre = IO.readString();
		return new Proyecto(nombre);
	}

	private static void anadirEmp() {
		String nombre = IO.readString("Nombre ? ");
		Double salario = IO.readDoubleOptional("Salario?: ");
		LocalDate nacido = IO.readLocalDateOptional("Nacimiento?: ");
		UUID departamento = IO.readUUIDOptional("Departamento ? ");
		Empleado empleado = new Empleado(nombre, salario,nacido, new Departamento(departamento));
				
		IO.println(controller.createEmpleado(empleado) ? "Insertado correctamente" :
				 "No se ha insertado");
	}

	private static void anadirDep() {
		// Obtenemos los datos del departamento que se quiere insertar
		String nombre = IO.readString("Nombre ? ");
		UUID jefe = IO.readUUIDOptional("Jefe ? ");

		// Creamos el departamento y lo insertamos
		Departamento departamento = new Departamento(nombre, new Empleado(jefe));
				
		// Comprobamos si se ha insertado el registro y damos feedback
		IO.println(controller.createDepartamento(departamento) ? "Insertado correctamente" :
				"No se ha encontrado un empleado con el ID introducido" );
	}
  private static void showResult(String msg) {
		System.out.println("* " + msg);
		
		System.out.println("-".repeat(80));
	}

}
