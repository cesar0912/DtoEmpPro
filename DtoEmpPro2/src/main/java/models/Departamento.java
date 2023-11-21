package models;

import java.util.*;
import java.util.stream.Collectors;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "hib_departamento")

@NamedQueries({
    @NamedQuery(name ="Departamento.findAll",
            query="SELECT d FROM Departamento d"),
    @NamedQuery(name="Departamento.findByNombre", 
            query="SELECT d FROM Departamento d WHERE d.nombre LIKE :nombre"),
    @NamedQuery(name="Departamento.findByEmpleado", 
            query="SELECT DISTINCT d FROM Empleado e JOIN e.departamento d WHERE e.id = :id")
})
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "nombre")
    private String nombre;

    // Relación OneToMany con la entidad Empleado
    @OneToMany(mappedBy = "departamento",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Empleado> empleados = new HashSet<>();

	public Departamento(String nombre, Empleado empleado) {
	    this.id = UUID.randomUUID();
	    this.nombre = nombre;
	    if (empleado != null) {
	        empleados.add(empleado);
	        empleado.setDepartamento(this);
	    }
	}
	public Departamento(UUID id) {
		this.id = id;
	}
	public void addEmpleado(Empleado e) {
	    empleados.add(e);
	    e.setDepartamento(this);
	}
	public void removeEmpleado(Empleado e) {
		e.setDepartamento(null);
		empleados.remove(e);
	}
	
	@Override
	public String toString() {
	    List<UUID> emps = empleados.stream()
	            .map(e -> e.getId())
	            .sorted()
	            .collect(Collectors.toList()); 
	    return String.format("Departamento [%s, %s, %s]", id, nombre, emps);
	}
}

