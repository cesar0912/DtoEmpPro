import java.util.Scanner;
import java.util.UUID;

import controllers.OficinaController;
import io.IO;
import models.Empleado;
import models.Proyecto;

public class MainProyectos {
	static void manageProyectos(OficinaController controller,Scanner scanner) {
		while (true) {
			System.out.println("\n--- Proyecto Gestiones ---");
			System.out.println("1. Listar Proyecto");
			System.out.println("2. Buscar Proyecto");
			System.out.println("3. Guardar Proyecto");
			System.out.println("4. Borrar Proyecto");
			System.out.println("5. Modificar Proyecto");
			System.out.println("6. Añadir empleado a un Proyecto");
			System.out.println("7. Eliminar empleado de un Proyecto");
			
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
				buscarProyecto(controller);
				break;
			case 3:
				saveProyecto(controller);
				break;
			case 4:
				deleteProyecto(controller);
				break;
			case 5:
				updateProyecto(controller);
				break;
			case 6:
				sumarEmpProyecto(controller);
				break;
			case 7:
				quitarEmpProyecto(controller);
				break;
			

			case 0:
				return; // Return to the main menu
			default:
				System.out.println("Invalid choice. Please enter a valid option.");
			}
		}
	}
	private static void quitarEmpProyecto(OficinaController controller) {
		IO.print("ID del Proyecto ? ");
		UUID id = IO.readUUID();
		IO.print("Empleado ? ");
		UUID idEmp = IO.readUUIDOptional();
		if(id!=null && idEmp!=null) {
		IO.println(controller.deleteEmpPro(new Proyecto(id),new Empleado(idEmp)) ? "Eliminado correctamente"
				: "\nRegistro no encontrado o Información no válida\n");
		}else {
			System.out.println("Registros vacios");
		}
		
		
	}
	private static void sumarEmpProyecto(OficinaController controller) {
		IO.print("ID del Proyecto ? ");
		UUID id = IO.readUUID();
		IO.print("Empleado ? ");
		UUID idEmp = IO.readUUIDOptional();
		if(id!=null && idEmp!=null) {
		IO.println(controller.anadirEmpPro(new Proyecto(id),new Empleado(idEmp)) ? "Añadido correctamente"
				: "\nRegistro no encontrado o Información no válida\n");
		}else {
			System.out.println("Registros vacios");
		}
	}
	private static void buscarProyecto(OficinaController controller) {
		UUID id = IO.readUUID("ID:  ? ");
		System.out.println(controller.getProyectoPorId(id));
	}
	private static void updateProyecto(OficinaController controller) {
		UUID id = IO.readUUID("ID:  ? ");
		String nombre = IO.readString("Nombre ? ");
		Proyecto proyecto=new Proyecto(id,nombre);
		IO.println(controller.updateProyecto(proyecto) ? "Modificado correctamente"
				: "No se ha encontrado un empleado con el ID introducido");
	}

	private static void deleteProyecto(OficinaController controller) {
		UUID id = IO.readUUID("ID:  ? ");
		Proyecto proyecto=new Proyecto(id);
		IO.println(controller.deleteProyecto(proyecto) ? "Borrado correctamente"
				: "No se ha encontrado un empleado con el ID introducido");
	}

	private static void saveProyecto(OficinaController controller) {
		String nombre = IO.readString("Nombre ? ");
		Proyecto proyecto=new Proyecto(nombre);
		IO.println(controller.createProyecto(proyecto) ? "Insertado correctamente"
				: "No se ha encontrado un empleado con el ID introducido");

	}
}
