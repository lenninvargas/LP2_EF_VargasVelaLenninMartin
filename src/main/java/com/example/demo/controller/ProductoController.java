package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.CategoriaEntity;

import com.example.demo.entity.ProductoEntity;
import com.example.demo.entity.UsuarioEntity;

import com.example.demo.service.CategoriaService;
import com.example.demo.service.ProductoService;
import com.example.demo.service.UsuarioService;
import com.example.demo.service.impl.PdfService;
import com.itextpdf.io.exceptions.IOException;


import jakarta.servlet.http.HttpSession;

@Controller
public class ProductoController {
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private PdfService pdfService;
	
	@GetMapping("/lista_productos")
	public String showMenu(HttpSession session, Model model) {
		if(session.getAttribute("usuario") == null) {
			return "redirect:/";
		}
		
		String correo = session.getAttribute("usuario").toString();
		UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCorreo(correo);
		model.addAttribute("nombre", usuarioEntity.getNombre());
		model.addAttribute("foto", usuarioEntity.getUrlImagen());
		
		List<ProductoEntity>productos = productoService.buscarTodosProductos();
		model.addAttribute("productos", productos);
		
											
		return "lista_productos";
	}
	
	@GetMapping("/registrar_producto")
	public String showRegistrarEmpleado(HttpSession session, Model model) {
		if(session.getAttribute("usuario") == null) {
			return "redirect:/";
		}
		String correo = session.getAttribute("usuario").toString();
		UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCorreo(correo);
		model.addAttribute("nombre", usuarioEntity.getNombre());
		model.addAttribute("foto", usuarioEntity.getUrlImagen());
		
		List<CategoriaEntity> listaCategoria = categoriaService.buscarTodasCategorias();
		model.addAttribute("listaCategoria", listaCategoria);
		model.addAttribute("producto", new ProductoEntity());
		return "registrar_producto";
	}
	
	@PostMapping("/registrar_producto")
	public String registrarEmpleado(HttpSession session, Model model, @ModelAttribute ProductoEntity producto) {
		if(session.getAttribute("usuario") == null) {
			return "redirect:/";
		}
		String correo = session.getAttribute("usuario").toString();
		UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCorreo(correo);
		model.addAttribute("nombre", usuarioEntity.getNombre());
		model.addAttribute("foto", usuarioEntity.getUrlImagen());
		
		List<CategoriaEntity> listaCategoria = categoriaService.buscarTodasCategorias();
		model.addAttribute("listaCategoria", listaCategoria);
		model.addAttribute("producto", new ProductoEntity());
		productoService.guardarProducto(producto);
		return "redirect:/lista_productos"; 
	}
		
	@GetMapping("/detalle_producto/{id}")
	public String showDetalleEmpleado(HttpSession session, Model model, @PathVariable("id")Integer id) {
		if(session.getAttribute("usuario") == null) {
			return "redirect:/";
		}
		String correo = session.getAttribute("usuario").toString();
		UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCorreo(correo);
		model.addAttribute("nombre", usuarioEntity.getNombre());
		model.addAttribute("foto", usuarioEntity.getUrlImagen());
		
		ProductoEntity producto = productoService.buscarProductoPorId(id);
		model.addAttribute("producto", producto);
		return "detalle_producto";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminarEmpleado(HttpSession session, @PathVariable("id")Integer id) {
		if(session.getAttribute("usuario") == null) {
			return "redirect:/";
		}
		productoService.eliminarProductoPorId(id);
		return "redirect:/lista_productos";
	}
	
	@GetMapping("/editar_producto/{id}")
	public String showEditarEmpleado(HttpSession session, Model model, @PathVariable("id")Integer id) {
		if(session.getAttribute("usuario") == null) {
			return "redirect:/";
		}
		String correo = session.getAttribute("usuario").toString();
		UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCorreo(correo);
		model.addAttribute("nombre", usuarioEntity.getNombre());
		model.addAttribute("foto", usuarioEntity.getUrlImagen());
		
		ProductoEntity producto = productoService.buscarProductoPorId(id);
		List<CategoriaEntity> listaCategoria = categoriaService.buscarTodasCategorias();
		model.addAttribute("listaCategoria", listaCategoria);
		model.addAttribute("producto", producto);
		return "editar_producto";
	}
	
	@PostMapping("/editar_producto/{id}")
	public String editarEmpleado(@ModelAttribute ProductoEntity producto, @PathVariable("id")Integer id) {
		ProductoEntity productoEntity = productoService.editarProducto(producto, id);
		productoService.guardarProducto(productoEntity);
		return "redirect:/lista_productos";
	}
	
	
	@GetMapping("/generar_pdf")
	public ResponseEntity<InputStreamResource>generarPdf(HttpSession session) throws IOException, java.io.IOException {
		String correo = session.getAttribute("usuario").toString();
		UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCorreo(correo);
		
		List<ProductoEntity> listaProducto = productoService.buscarTodosProductos();
		
		Map<String, Object>datosPdf = new HashMap<String, Object>();
		datosPdf.put("listaProducto", listaProducto);
		datosPdf.put("nombre", usuarioEntity.getNombre());
		
		ByteArrayInputStream pdfBytes = pdfService.generarPdfDeHtml("template_pdf", datosPdf);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Disposition", "inline; filename=productos.pdf");
		
		return ResponseEntity.ok()
				.headers(httpHeaders)
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(pdfBytes));
	}
	
}
