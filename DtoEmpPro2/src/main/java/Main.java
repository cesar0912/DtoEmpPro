import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import controllers.OficinaController;
import io.IO;
import models.Departamento;
import models.Empleado;
import repositories.Departamento.DepRepositoryImpl;
import repositories.Empleado.EmpRepositoryImpl;
import repositories.Proyecto.ProRepositoryImpl;

public class Main {

	private static Scanner scanner = new Scanner(System.in);
	static OficinaController controller = new OficinaController(new DepRepositoryImpl(), new EmpRepositoryImpl(),
			new ProRepositoryImpl()

	);

	public static void main(String[] args) {
		while (true) {
			System.out.println("1. Gestiona Empleados");
			System.out.println("2. Gestiona Proyectos");
			System.out.println("3. Gestiona Departamentos");
			System.out.println("0. Exit");
			System.out.print("Elige: ");

			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume the newline character

			switch (choice) {
			case 1:
				manageEmpleados();
				break;
			case 2:
				manageProyectos();
				break;
			case 3:
				manageDepartamentos();
				break;

			case 0:
				System.out.println("Exiting the program. Goodbye!");
				System.exit(0);
			default:
				System.out.println("Invalid choice. Please enter a valid option.");
			}
		}
	}

	private static void manageEmpleados() {
		while (true) {
			System.out.println("\n--- Empleado Gestiones ---");
			System.out.println("1. Guardar Empleado");
			System.out.println("2. Borrar Empleado");
			System.out.println("3. Modificar Empleado");
			System.out.println("4. Listar Empleado");
			System.out.println("0. Volver");
			System.out.print("Elige: ");

			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume the newline character

			switch (choice) {
			case 1:
				saveEmpleado();
				break;
			case 2:
				deleteEmpleado();
				break;
			case 3:
				updateEmpleado();
				break;
			case 4:
				var empleados = controller.getEmpleados();
				empleados.forEach(System.out::println);
				break;
			case 0:
				return;
			default:
				System.out.println("Invalid choice. Please enter a valid option.");
			}
		}
	}

	private static void saveEmpleado() {
		String nombre = IO.readString("Nombre ? ");
		Double salario = IO.readDoubleOptional("Salario?: ");
		LocalDate nacido = IO.readLocalDateOptional("Nacido ? ");
		UUID departamento = IO.readUUIDOptional("Departamento ? ");
		Empleado empleado;
		if (departamento != null) {
			empleado = new Empleado(nombre, salario, nacido, new Departamento(departamento));
		} else {
			empleado = new Empleado(nombre, salario, nacido);
		}
		IO.println(controller.createEmpleado(empleado) ? "Insertado correctamente"
				: "No se ha encontrado un empleado con el ID introducido");
	}

	private static void deleteEmpleado() {
		UUID id = IO.readUUID("ID:  ? ");
		Empleado empleado = new Empleado(id);

		IO.println(controller.deleteEmpleado(empleado) ? "Eliminado correctamente"
				: "No se ha encontrado un Empleado con el ID introducido");
	}

	private static void updateEmpleado() {
		UUID id = IO.readUUID("id ?");
		String nombre = IO.readString("Nombre ? ");
		Double salario = IO.readDoubleOptional("Salario?: ");
		LocalDate nacido = IO.readLocalDateOptional("Nacido ? ");
		UUID departamento = IO.readUUIDOptional("Departamento ? ");

		Empleado empleado = new Empleado(id, nombre, salario, nacido, new Departamento(departamento));
		IO.println(controller.updateEmpleado(empleado) ? "Actualizado correctamente"
				: "\nRegistro no encontrado o Información no válida\n" + "Asegúrese de:\n"
						+ "- Haber rellenado al menos 1 campo\n"
						+ "- Que el ID del empleado a modificar exista en la tabla empleado\n"
						+ "- Que el ID del departamento exista en la tabla departamento");
	}

	private static void manageProyectos() {
		while (true) {
			System.out.println("\n--- Proyecto Gestiones ---");
			System.out.println("1. Listar Proyecto");
			System.out.println("2. Buscar Proyecto");
			System.out.println("3. Guardar Proyecto");
			System.out.println("4. Borrar Proyecto");
			System.out.println("5. Modificar Proyecto");
			
			System.out.println("0. Volver");
			System.out.print("Elige: ");

			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume the newline character

			switch (choice) {
			case 1:
				var proyectos = controller.getProyectos();
				proyectos.forEach(System.out::println);
				break;
			case 2:
				break;
			case 3:
				saveProyecto();
				break;
			case 4:
				deleteProyecto();
				break;
			case 5:
				updateProyecto();
				break;
			

			case 0:
				return; // Return to the main menu
			default:
				System.out.println("Invalid choice. Please enter a valid option.");
			}
		}
	}

	private static void updateProyecto() {
		// TODO Auto-generated method stub

	}

	private static void deleteProyecto() {
		// TODO Auto-generated method stub

	}

	private static void saveProyecto() {
		// TODO Auto-generated method stub

	}

	private static void manageDepartamentos() {
		while (true) {
			System.out.println("\n--- Departamento Gestiones ---");
			System.out.println("1. Listar Departamento");
			System.out.println("2. Buscar Departamento");
			System.out.println("3. Guardar Departamento");
			System.out.println("4. Borrar Departamento");
			System.out.println("5. Modificar Departamento");

			System.out.println("0. Volver");
			System.out.print("Elige: ");

			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume the newline character

			switch (choice) {
			case 1:
				var departamentos = controller.getDepartamentos();
				departamentos.forEach(System.out::println);
				break;
			case 2:
				break;
			case 3:
				saveDepartamento();
				break;
			case 4:
				deleteDepartamento();
				break;
			case 5:
				updateDepartamento();
				break;

			case 0:
				return; // Return to the main menu
			default:
				System.out.println("Invalid choice. Please enter a valid option.");
			}
		}
	}

	private static void updateDepartamento() {
		IO.print("ID ? ");
		UUID id = IO.readUUID();
		IO.print("Nombre ? ");
		String nombre = IO.readStringOptional();
		IO.print("Jefe ? ");
		UUID jefe = IO.readUUIDOptional();

		Departamento departamento = new Departamento(id, nombre, new Empleado(jefe));
		IO.println(controller.updateDepartamento(departamento) ? "Actualizado correctamente"
				: "\nRegistro no encontrado o Información no válida\n" + "Asegúrese de:\n"
						+ "- Haber rellenado al menos 1 campo\n"
						+ "- Que el ID del departamento a modificar exista en la tabla departamento\n"
						+ "- Que el ID del jefe exista en la tabla empleado");
	}

	private static void deleteDepartamento() {
		UUID jefe = IO.readUUID("ID Departamento ? ");
		Departamento departamento = new Departamento(jefe);

		IO.println(controller.deleteDepartamento(departamento) ? "Eliminado correctamente"
				: "No se ha encontrado un Departamento con el ID introducido");
	}

	private static void saveDepartamento() {
		String nombre = IO.readString("Nombre ? ");
		UUID jefe = IO.readUUIDOptional("Jefe ? ");
		Departamento departamento = new Departamento(nombre, new Empleado(jefe));

		IO.println(controller.createDepartamento(departamento) ? "Insertado correctamente"
				: "No se ha encontrado un empleado con el ID introducido");

	}
}
