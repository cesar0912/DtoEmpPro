package models;

import java.util.*;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "hib_departamento")
@NamedQueries({
	@NamedQuery(name="Departamento.findByNombre", 
			query="SELECT d FROM Departamento d WHERE d.nombre LIKE :nombre"),
	@NamedQuery(name="Departamento.findByEmpleado", 
			query="SELECT DISTINCT d FROM Empleado e JOIN e.departamento d WHERE e.id = :id")
})
public class Departamento {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private UUID id;
	
	private String nombre;
	
	@OneToMany(mappedBy = "departamento")
    private Set<Empleado> empleados = new HashSet<>();

	public Departamento(String nombre) {
		this.id = UUID.randomUUID();
		this.nombre = nombre;
		this.empleados=null;	}
	
	public Departamento(UUID id) {
		this.id = id;
	}
	public void addEmpleado(Empleado e) {
		empleados.add(e);
    }
	public void removeEmpleado(Empleado e) {
		e.setDepartamento(null);
		empleados.remove(e);
	}
	
	@Override
	public String toString() {
		List<String> emps = empleados.stream()
				.map(e -> e.getNombre())
				.sorted()
				.toList();
		return String.format("Departamento [%-2d %-25s %s]", id, nombre, emps);
	}
}

