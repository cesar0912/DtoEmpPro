package models;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "departamento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name ="Departamento.findAll",
query="SELECT d FROM Departamento d")
public class Departamento {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "nombre")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "jefe", nullable = true)
    private Empleado jefe;


    @OneToMany(mappedBy = "departamento", cascade = CascadeType.REMOVE, orphanRemoval = false)
    private List<Empleado> empleados;
	
	public Departamento(UUID id) {
		setId(id);
	}
	
	public Departamento(String nombre) {
		setId(UUID.randomUUID());
		setNombre(nombre);
	}

	public Departamento(UUID id, String nombre) {
		setId(id);
		setNombre(nombre);
		setJefe(null);
	}
	
	public Departamento(String nombre, Empleado jefe) {
		setId(UUID.randomUUID());
		setNombre(nombre);
		setJefe(jefe);
	}
	
	public Departamento(UUID id, String nombre, Empleado jefe) {
		setId(id);
		setNombre(nombre);
		setJefe(jefe);
	}
	
	@Override
	public String toString() {
	    String format = "[ %s ][ %s ][ %s ]";
	    String jefeInfo = (jefe != null) ? jefe.getId() + " | " + jefe.getNombre() : "N/A";
		return String.format(format, this.id.toString(), this.nombre, jefeInfo);
	}
}