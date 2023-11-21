package models;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hib_empleado")
@NamedQueries({
    @NamedQuery(name ="Empleado.findAll",
            query="SELECT d FROM Empleado d"),
    @NamedQuery(name="Empleado.findByNombre", 
            query="SELECT d FROM Empleado d WHERE d.nombre LIKE :nombre")
})
public class Empleado {
	
	@Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;
	
	private String nombre;
	
	private Double salario;
	
	private LocalDate nacimiento;
	
	@ManyToOne()
	@JoinColumn(name="departamento")
	private Departamento departamento;
	
	@ManyToMany
	@JoinTable(
	    name = "hib_empleado_hib_proyecto",
	    joinColumns = @JoinColumn(name = "empleado_id"),
	    inverseJoinColumns = @JoinColumn(name = "proyecto_id")
	)
	private Set<Proyecto> proyectos = new HashSet<>();
		
	public Empleado(String nombre, Double salario, LocalDate nacimiento) {
	    this.id = UUID.randomUUID();
	    this.nombre = nombre;
	    this.salario = salario;
	    this.nacimiento = nacimiento;
	    this.departamento = null; 
	    this.proyectos = new HashSet<>();
	}
	public Empleado(UUID id) {
	    this.id = id;
	}	
	@Override
	public String toString() {
	    String dep = (departamento == null) ? "¿?" : departamento.getNombre();
	    String proyectosStr = (proyectos == null) ? "[]" : proyectos.toString();
	    return String.format("Empleado [ID: %s, Nombre: %s, Salario: %.2f, Departamento: %s, Proyectos: %s]",
	            Objects.toString(id), nombre, salario, dep, proyectosStr);
	}
	public void addProyecto(Proyecto proyecto) {
	    proyectos.add(proyecto);
	    proyecto.getEmpleados().add(this);
	}

	public void removeProyecto(Proyecto proyecto) {
	    proyectos.remove(proyecto);
	    proyecto.getEmpleados().remove(this);
	}
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Empleado empleado = (Empleado) obj;
	    return Objects.equals(id, empleado.id);
	}
}
