package models;

import java.util.Date;
import java.util.HashSet;

import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "hib_proyecto")
@NamedQuery(name = "Proyecto.findAll", query = "SELECT p FROM Proyecto p")
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "nombre")
    private String nombre;

    @ManyToMany
    @JoinTable(
        name = "hib_empleado_hib_proyecto",
        joinColumns = @JoinColumn(name = "proyecto_id"),
        inverseJoinColumns = @JoinColumn(name = "empleado_id")
    )
    private Set<Empleado> empleados = new HashSet<>();


    public Proyecto(String nombre, Set<Empleado> empleados) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.empleados = new HashSet<>();
    }
    public Proyecto(String nombre) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.empleados = new HashSet<>();
    }
    public Proyecto(UUID id) {
        this.id = id;
    }
	
	public void agregarEmpleado(Empleado empleado) {
	    empleados.add(empleado);
	    empleado.getProyectos().add(this);
	}

	public void quitarEmpleado(Empleado empleado) {
	    empleados.remove(empleado);
	    empleado.getProyectos().remove(this);
	}
}
