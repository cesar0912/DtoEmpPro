package models;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
	private UUID id;
	
	private String nombre;
	
	private Double salario;
	
	private LocalDate nacimiento;
	
	@ManyToOne()
	@JoinColumn(name="departamento")
	private Departamento departamento;
	
	@ManyToMany(mappedBy="proyectos")
	 private Set<Proyecto> proyectos = new HashSet<>();
		
	public Empleado(String nombre, Double salario, LocalDate nacimiento) {
		this.id = UUID.randomUUID();
		this.nombre = nombre;
		this.salario=salario;
		this.nacimiento=nacimiento;
		this.departamento=null;
		this.proyectos=null;
	}
			
	@Override
	public String toString() {
		String dep = departamento == null ? "¿?" : departamento.getNombre();
		return String.format("Empleado     [%-2d %-25s %s]", id, nombre, dep);
	}
	public void addProyecto(Proyecto e) {
		proyectos.add(e);
    }
	public void removeProyecto(Proyecto p) {
		p.setEmpleados(null);
		proyectos.remove(p);
	}
	public boolean equals(Empleado e) {
		return e != null && e.getId() != null && e.getId() == id;
	}
}
