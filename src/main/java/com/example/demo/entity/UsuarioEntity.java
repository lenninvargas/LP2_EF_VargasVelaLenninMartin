package com.example.demo.entity;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_usuario")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEntity {
	
	@Id
	@Column(nullable = false)
	private String correo;
	
	@Column(columnDefinition = "VARCHAR(50)")
	private String nombre;
	
	@Column(columnDefinition = "VARCHAR(50)")
	private String apellido;	
	
	@Column(nullable = false)
	private String password;
	
	@Column(name = "fecha_nac", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private LocalDate fechaNacimiento;
	
	private String urlImagen;
}
