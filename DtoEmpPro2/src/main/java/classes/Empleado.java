package classes;

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
	
	@ManyToOne()
	@JoinColumn(name="departamento")
	private Departamento departamento;
		
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
