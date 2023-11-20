import java.util.logging.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import models.Departamento;
import models.Empleado;


public class Main {
	
	static EntityManager em = null;


	public static void main(String[] args) {
		Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);



		em = Persistence.createEntityManagerFactory("unidad-persistencia").createEntityManager();

		showResult("Inicial");
		em.getTransaction().begin();

	}
	
	/**
	 * Muestra el contenido del EntityManager
	 */
	private static void showResult(String msg) {
		System.out.println("* " + msg);
		em.createQuery("FROM Departamento").getResultList().forEach(System.out::println);
		em.createQuery("FROM Empleado").getResultList().forEach(System.out::println);
		System.out.println("-".repeat(80));
	}


}
