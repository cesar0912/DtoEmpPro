package models;

import java.util.Date;
import java.util.HashSet;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "hib_proyecto")
@NamedQuery(name = "Proyecto.findAll", query = "SELECT p FROM Proyecto p"
)
public class Proyecto {
	@Id
	@GeneratedValue
	private UUID id;

	@Column(nullable = false)
	private String nombre;
	
	@ManyToMany(mappedBy="proyecto")
	 private Set<Empleado> empleados = new HashSet<>();
	
	public void setEmpleados(Empleado e) {
		empleados.add(e);
	}
	public Proyecto(String nombre) {
		this.id = UUID.randomUUID();
		this.nombre = nombre;
		this.empleados=null;
	}
}
