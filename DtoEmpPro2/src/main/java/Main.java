import java.util.Scanner;

import controllers.OficinaController;
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
			System.out.println("\n--- INICIO ---");
			System.out.println("1. Gestiona Empleados");
			System.out.println("2. Gestiona Proyectos");
			System.out.println("3. Gestiona Departamentos");
			System.out.println("0. Exit");
			System.out.print("Elige: ");

			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume the newline character

			switch (choice) {
			case 1:
				MainEmpleados.manageEmpleados(controller, scanner);
				break;
			case 2:
				MainProyectos.manageProyectos(controller, scanner);
				break;
			case 3:
				MainDepartamentos.manageDepartamentos(controller, scanner);
				break;

			case 0:
				System.out.println("Exiting the program. Goodbye!");
				System.exit(0);
			default:
				System.out.println("Invalid choice. Please enter a valid option.");
			}
		}
	}

}
