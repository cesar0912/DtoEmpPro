package models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hib_empleado")
@NamedQuery(name="Empleado.noDepartamento", 
	query="SELECT e FROM Empleado e WHERE e.departamento IS NULL")

public class Empleado {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String nombre;
	
	private Double salario;
	
	private Date nacimiento;
	
	@ManyToOne()
	@JoinColumn(name="departamento")
	private Departamento departamento;
	
	@ManyToMany(mappedBy="empleado")
	 private Set<Proyecto> proyectos = new HashSet<>();
		
	public Empleado(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
			
	@Override
	public String toString() {
		String dep = departamento == null ? "¿?" : departamento.getNombre();
		return String.format("Empleado     [%-2d %-25s %s]", id, nombre, dep);
	}
	
	@Override
	public int hashCode() {
		return id;
	}
	
	public boolean equals(Empleado e) {
		return e != null && e.getId() != null && e.getId() == id;
	}
}
