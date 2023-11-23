import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

import controllers.OficinaController;
import io.IO;
import models.Departamento;
import models.Empleado;

public class MainEmpleados {
	static void manageEmpleados(OficinaController controller,Scanner scanner) {
		while (true) {
			System.out.println("\n--- Empleado Gestiones ---");
			System.out.println("1. Listar Empleado");
			System.out.println("2. Buscar Empleado");
			System.out.println("3. Guardar Empleado");
			System.out.println("4. Borrar Empleado");
			System.out.println("5. Modificar Empleado");
			
			System.out.println("0. Volver");
			System.out.print("Elige: ");

			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume the newline character

			switch (choice) {
			case 1:
				var empleados = controller.getEmpleados();
				empleados.forEach(System.out::println);
				break;
			case 2:
				buscarEmpleado(controller);
				break;
			case 3:
				saveEmpleado(controller);
				break;
			case 4:
				deleteEmpleado(controller);
				break;
			case 5:
				updateEmpleado(controller);
				break;
			
			case 0:
				return;
			default:
				System.out.println("Invalid choice. Please enter a valid option.");
			}
		}
	}

	private static void buscarEmpleado(OficinaController controller) {
		UUID id = IO.readUUID("ID:  ? ");
		System.out.println(controller.getEmpleadoPorId(id));
		
		
	}

	private static void saveEmpleado(OficinaController controller) {
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

	private static void deleteEmpleado(OficinaController controller) {
		UUID id = IO.readUUID("ID:  ? ");
		Empleado empleado = new Empleado(id);

		IO.println(controller.deleteEmpleado(empleado) ? "Eliminado correctamente"
				: "No se ha encontrado un Empleado con el ID introducido");
	}

	private static void updateEmpleado(OficinaController controller) {
		UUID id = IO.readUUID("id ?");
		String nombre = IO.readString("Nombre ? ");
		Double salario = IO.readDoubleOptional("Salario?: ");
		LocalDate nacido = IO.readLocalDateOptional("Nacido ? ");
		UUID departamento = IO.readUUIDOptional("Departamento ? ");
		Empleado empleado = null;
		if (departamento != null) {
			empleado = new Empleado(id,nombre, salario, nacido, new Departamento(departamento));
		} else {
			empleado = new Empleado(id,nombre, salario, nacido);
		}
		IO.println(controller.updateEmpleado(empleado) ? "Actualizado correctamente"
				: "\nRegistro no encontrado o Información no válida\n" + "Asegúrese de:\n"
						+ "- Haber rellenado al menos 1 campo\n"
						+ "- Que el ID del empleado a modificar exista en la tabla empleado\n"
						+ "- Que el ID del departamento exista en la tabla departamento");
	}
}
