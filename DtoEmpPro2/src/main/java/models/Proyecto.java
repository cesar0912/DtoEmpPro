package models;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "proyectos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "Proyecto.findAll", query = "SELECT p FROM Proyecto p")
public class Proyecto {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "nombre")
    private String nombre;

    @ManyToMany
    @JoinTable(
        name = "proyecto_empleado",
        joinColumns = @JoinColumn(name = "proyecto_id"),
        inverseJoinColumns = @JoinColumn(name = "empleado_id")
    )
    private List<Empleado> empleados;
	
	public Proyecto(UUID id) {
		setId(id);
	}
	
	public Proyecto(String nombre) {
		setId(UUID.randomUUID());
		setNombre(nombre);
	}

	public Proyecto(UUID id, String nombre) {
		setId(id);
		setNombre(nombre);
	}
	public void add(Empleado e) {
		empleados.add(e);
	}
	public void remove(Empleado e) {
		empleados.remove(e);
	}
	@Override
	public String toString() {
	    StringBuilder empleadosStr = new StringBuilder("[ ");
	    for (Empleado empleado : empleados) {
	        empleadosStr.append(empleado.getId()).append(" | ").append(empleado.getNombre()).append(", ");
	    }
	    if (!empleados.isEmpty()) {
	        empleadosStr.setLength(empleadosStr.length() - 2);  // Elimina la coma y el espacio al final
	    }
	    empleadosStr.append(" ]");
	 

	    return String.format("[ %s ][ %s ][ %s ]", this.id.toString(), this.nombre, empleadosStr.toString());
	}


}