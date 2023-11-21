package models;

import java.time.LocalDate;
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
            query="SELECT e FROM Empleado e"),
    @NamedQuery(name="Empleado.findByNombre", 
            query="SELECT e FROM Empleado e WHERE e.nombre LIKE :nombre")
})
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "salario")
    private Double salario;
    
    @Column(name = "fecha_nacimiento")
    private LocalDate nacimiento;
    
    @ManyToOne
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;
    
    @ManyToMany(mappedBy = "empleados")
    private Set<Proyecto> proyectos = new HashSet<>();
    public Empleado(String nombre, Double salario, LocalDate nacimiento) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.salario = salario;
        this.nacimiento = nacimiento;
        
    }

    public Empleado(UUID id) {
        this.id = id;
    }
    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
    @Override
    public String toString() {
        String dep = (departamento == null) ? "¿?" : departamento.getId().toString();
        String proyectosStr = (proyectos == null) ? "¿?" : proyectos.toString();
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
