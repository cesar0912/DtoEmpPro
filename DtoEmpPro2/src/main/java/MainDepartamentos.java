import java.util.Scanner;
import java.util.UUID;

import controllers.OficinaController;
import io.IO;
import models.Departamento;
import models.Empleado;

public class MainDepartamentos {
	
	static void manageDepartamentos(OficinaController controller,Scanner scanner) {
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
				buscarDepartamento(controller);
				break;
			case 3:
				saveDepartamento(controller);
				break;
			case 4:
				deleteDepartamento(controller);
				break;
			case 5:
				updateDepartamento(controller);
				break;

			case 0:
				return; // Return to the main menu
			default:
				System.out.println("Invalid choice. Please enter a valid option.");
			}
		}
	}
	private static void buscarDepartamento(OficinaController controller) {
		UUID id = IO.readUUID("ID:  ? ");
		System.out.println(controller.getDepartamentoPorId(id));
	}
	private static void updateDepartamento(OficinaController controller) {
		IO.print("ID ? ");
		UUID id = IO.readUUID();
		IO.print("Nombre ? ");
		String nombre = IO.readStringOptional();
		IO.print("Jefe ? ");
		UUID jefe = IO.readUUIDOptional();
		Departamento departamento =null;
		if(jefe==null) {
			departamento = new Departamento(id, nombre);
		}else{
			departamento = new Departamento(id, nombre, new Empleado(jefe));
		}
		IO.println(controller.updateDepartamento(departamento) ? "Actualizado correctamente"
				: "\nRegistro no encontrado o Información no válida\n" + "Asegúrese de:\n"
						+ "- Haber rellenado los campos\n"
						+ "- Que el ID del departamento a modificar exista en la tabla departamento\n"
						+ "- Que el ID del jefe exista en la tabla empleado");
	}

	private static void deleteDepartamento(OficinaController controller) {
		UUID jefe = IO.readUUID("ID Departamento ? ");
		Departamento departamento = new Departamento(jefe);

		IO.println(controller.deleteDepartamento(departamento) ? "Eliminado correctamente"
				: "No se ha encontrado un Departamento con el ID introducido");
	}

	private static void saveDepartamento(OficinaController controller) {
		String nombre = IO.readString("Nombre ? ");
		UUID jefe = IO.readUUIDOptional("Jefe ? ");
		Departamento departamento = new Departamento(nombre, new Empleado(jefe));

		IO.println(controller.createDepartamento(departamento) ? "Insertado correctamente"
				: "No se ha encontrado un empleado con el ID introducido");

	}
}
