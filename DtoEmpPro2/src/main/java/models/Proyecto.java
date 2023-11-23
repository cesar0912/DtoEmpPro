package models;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "proyectos")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
		setNombre(nombre);
	}

	public Proyecto(UUID id, String nombre) {
		setId(id);
		setNombre(nombre);
	}
	
	@Override
	public String toString() {
	    String format = "[ %-36s ][ %-20s ]";
		return String.format(format, this.id.toString(), this.nombre);
	}
}