package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.ProductoEntity;

public interface ProductoService {
	List<ProductoEntity> buscarTodosProductos();
	ProductoEntity buscarProductoPorId(Integer id);
	ProductoEntity guardarProducto(ProductoEntity productoEntity);
	void eliminarProductoPorId(Integer id);
	ProductoEntity editarProducto(ProductoEntity productoEntity, Integer id);
}
