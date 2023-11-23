package models;

import java.time.LocalDate;
import java.util.UUID;

//import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "empleado")
@NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM empleado e")
@NamedQuery(name = "Empleado.findAllWithDepartamentoInfo", query = "SELECT e, d.id, d.nombre FROM empleado e LEFT JOIN e.departamento d")
public class Empleado {
	
	@Id
	private UUID id = UUID.randomUUID();
	private String nombre;
	private Double salario;
	private LocalDate nacido;
	
	@ManyToOne
	@JoinColumn(name="departamento", nullable = true)
	private Departamento departamento;
	
	public Empleado(String nombre, Double salario,LocalDate nacido, Departamento departamento) {
		setNombre(nombre);
		setSalario(salario);
		setNacido(nacido);
		setDepartamento(departamento);
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