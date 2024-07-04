package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_producto")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductoEntity {
	
	@Id
	@Column(name = "id_producto", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idProducto;
	
	@Column(name = "nom_prod", columnDefinition = "VARCHAR(40)")
	private String nomProd;
	
	@Column(nullable = false)
	private Double precio;
	
	@Column(nullable = false)
	private Integer stock;
	
	@ManyToOne
	@JoinColumn(name = "id_categoria", nullable = false)
	private CategoriaEntity idCategoria;
}
