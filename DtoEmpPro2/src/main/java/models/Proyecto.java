package models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

public class Proyecto {
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String nombre;
	
	@ManyToMany(mappedBy="empleados")
	 private Set<Empleado> empleados = new HashSet<>();

}
