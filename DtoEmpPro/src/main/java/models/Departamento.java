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
	private Integer id;
	
	private String nombre;
	
	@OneToMany(mappedBy = "departamento")
//	@OneToMany(mappedBy = "departamento", orphanRemoval = true)
    private Set<Empleado> empleados = new HashSet<>();

	public Departamento(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	public Departamento(Integer id) {
		this.id = id;
	}

	public void addEmpleado(Empleado e) {
		if (e.getDepartamento() != null) {
			e.getDepartamento().getEmpleados().remove(e);
		}
		e.setDepartamento(this);
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
