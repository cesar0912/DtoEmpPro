import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import controllers.OficinaController;
import io.IO;
import models.Empleado;
import models.Proyecto;
import models.Departamento;
import repositories.Departamento.DepRepositoryImpl;
import repositories.Empleado.EmpRepositoryImpl;
import repositories.Proyecto.ProRepositoryImpl;

public class Main {
	public static void main(String[] args) {


		List<String> opciones = List.of("1: Mostrar departamentos", "2: Mostrar empleados","3: Mostrar proyectos",
				"4: Añadir departamento","5: Añadir empleado","6: Añadir proyectos",
				"7: Eliminar departamento", "8: Eliminar empleado", "9: Eliminar proyecto",
				"10: Modificar departamento","11: Modificar empleado","13: Modificar proyecto",
				"13: Salir");
		var controller = new OficinaController(
                new DepRepositoryImpl(),
                new EmpRepositoryImpl(),
                new ProRepositoryImpl()
                 
        );

		while (true) {
			System.out.println(opciones);
			switch (IO.readInt()) {
			case 1:
				var departamentos = controller.getDepartamentos();
		        departamentos.forEach(System.out::println);
				break;
			case 2:
				var empleados = controller.getEmpleados();
				empleados.forEach(System.out::println);
				break;
			case 3:
				var proyectos = controller.getProyectos();
				proyectos.forEach(System.out::println);
				break;
			case 4:
				controller.createDepartamento(anadirDep());
				break;
			case 5:
				controller.createEmpleado(anadirEmp());
				break;
			case 6:
				controller.createProyecto(anadirPro());
				break;
				
			case 7:
				controller.deleteDepartamento(eliminarDep());
				break;
			case 8:
				controller.deleteEmpleado(null);
				break;
			case 9:
				controller.deleteProyecto(null);
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

	private static Departamento eliminarDep() {
		// TODO Auto-generated method stub
		return null;
	}

	private static Proyecto anadirPro() {
		IO.print("Nombre ? ");
		String nombre = IO.readString();
		return new Proyecto(nombre);
	}

	private static Empleado anadirEmp() {
		IO.print("Nombre ? ");
		String nombre = IO.readString();
		IO.print("Salario ? ");
		Double salario = IO.readDouble();
		IO.print("fecha nacimiento");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate nacimiento = LocalDate.parse(IO.readString(), formatter);
		return new Empleado(nombre, salario, nacimiento);
	}

	private static Departamento anadirDep() {
		// TODO Auto-generated method stub
		IO.print("Nombre ? ");
		String nombre = IO.readString();
		return new Departamento(nombre);
	}

}
