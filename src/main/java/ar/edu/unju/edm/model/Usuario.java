package ar.edu.unju.edm.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
@Entity
@Component
public class Usuario {
	private String nombre;
	private String apellido;
	private String email;
	
	
	@NotEmpty
	private String contrasena;
	private Boolean estado;
	@Id
	@NotNull 
	@Max (value = 99999999, message="DNI debe ser menor que 99999999")
	@Min(value = 1000000, message="DNI debe ser mayor que 1000000")
	private Integer dni;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate fechaNacimiento;

	public Usuario() {
		// TODO Auto-generated constructor stub
	}
	public Usuario(String email, String contrasena) {
		super();
		this.email = email;
		this.contrasena = contrasena;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	public Integer getDni() {
		return dni;
	}
	public void setDni(Integer dni) {
		this.dni = dni;
	}
	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(LocalDate fechaNac) {
		this.fechaNacimiento = fechaNac;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	

}

