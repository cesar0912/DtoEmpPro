import java.util.Scanner;
import java.util.UUID;

import controllers.OficinaController;
import io.IO;

public class MainProyectos {
	static void manageProyectos(OficinaController controller,Scanner scanner) {
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
			

			case 0:
				return; // Return to the main menu
			default:
				System.out.println("Invalid choice. Please enter a valid option.");
			}
		}
	}
	private static void buscarProyecto(OficinaController controller) {
		UUID id = IO.readUUID("ID:  ? ");
		System.out.println(controller.getProyectoPorId(id));
	}
	private static void updateProyecto(OficinaController controller) {
		// TODO Auto-generated method stub

	}

	private static void deleteProyecto(OficinaController controller) {
		// TODO Auto-generated method stub

	}

	private static void saveProyecto(OficinaController controller) {
		// TODO Auto-generated method stub

	}
}
