package models;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "empleado")
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name ="Empleado.findAll",
query="SELECT e FROM Empleado e")
public class Empleado {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "salario")
    private Double salario;

    @Column(name = "nacido")
    @Temporal(TemporalType.DATE)
    private LocalDate nacido;

    @ManyToOne
    @JoinColumn(name = "departamento", nullable = true)
    private Departamento departamento;

    @ManyToMany(mappedBy = "empleados")
    private List<Proyecto> proyectos; 
    public Empleado(UUID id,String nombre, Double salario,LocalDate nacido, Departamento departamento) {
    	setId(id);
		setNombre(nombre);
		setSalario(salario);
		setNacido(nacido);
		setDepartamento(departamento);
	}
	
	public Empleado(String nombre, Double salario,LocalDate nacido, Departamento departamento) {
		setId(UUID.randomUUID());
		setNombre(nombre);
		setSalario(salario);
		setNacido(nacido);
		setDepartamento(departamento);
	}
	
	public Empleado(String nombre, Double salario,LocalDate nacido) {
		setId(UUID.randomUUID());
		setNombre(nombre);
		setSalario(salario);
		setNacido(nacido);
	}
	public Empleado(UUID id, String nombre) {
		setId(id);
		setNombre(nombre);
	}
	
	public Empleado(UUID id) {
		setId(id);
	}
	
	@Override
	public String toString() {
		String format = "[ %s ][ %s ][ %s ][ %s ]";
		String salarioStr = (salario != null) ? Double.toString(this.salario) : "N/A";
		String departamentoInfo = (departamento != null) ? departamento.getId() + " | " + departamento.getNombre() : "N/A";
	    return String.format(format, this.id.toString(), this.nombre, salarioStr, departamentoInfo);
	}
}