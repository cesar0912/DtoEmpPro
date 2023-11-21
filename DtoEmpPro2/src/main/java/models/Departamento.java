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
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;
	
	private String nombre;
	
	@OneToMany(mappedBy = "departamento", fetch = FetchType.EAGER)
	private Set<Empleado> empleados = new HashSet<>();

	public Departamento(String nombre) {
		this.id = UUID.randomUUID();
		this.nombre = nombre;	
		}
	
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
	            .collect(Collectors.toList()); 
	    return String.format("Departamento [%s, %s, %s]", id, nombre, emps);
	}
}

