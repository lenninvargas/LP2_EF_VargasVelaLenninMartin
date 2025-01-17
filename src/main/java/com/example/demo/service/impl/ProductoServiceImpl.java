package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ProductoEntity;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.service.ProductoService;
@Service
public class ProductoServiceImpl implements ProductoService{

	@Autowired
	private ProductoRepository productoRepository;
	
	@Override
	public List<ProductoEntity> buscarTodosProductos() {
		return productoRepository.findAll();
	}

	@Override
	public ProductoEntity buscarProductoPorId(Integer id) {
		return productoRepository.findById(id).get();
	}

	@Override
	public ProductoEntity guardarProducto(ProductoEntity productoEntity) {
		return productoRepository.save(productoEntity);
	}

	@Override
	public void eliminarProductoPorId(Integer id) {
		productoRepository.deleteById(id);
	}

	@Override
	public ProductoEntity editarProducto(ProductoEntity producto, Integer id) {
		ProductoEntity productoEntity = productoRepository.findById(id).get();
		productoEntity.setNomProd(producto.getNomProd());
		productoEntity.setPrecio(producto.getPrecio());
		productoEntity.setStock(producto.getStock());
		productoEntity.setIdCategoria(producto.getIdCategoria());
		return productoEntity;
	}

}
