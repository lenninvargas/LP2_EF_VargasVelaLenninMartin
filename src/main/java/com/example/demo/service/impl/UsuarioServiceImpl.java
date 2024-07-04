package com.example.demo.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.UsuarioEntity;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.UsuarioService;
import com.example.demo.utils.Utilitarios;

import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public void crearUsuario(UsuarioEntity usuarioEntity, Model model, MultipartFile foto) {
		String nombreFoto = Utilitarios.guardarImagen(foto);
		usuarioEntity.setUrlImagen(foto.getOriginalFilename());
				
		String passwordHash = Utilitarios.extraerHash(usuarioEntity.getPassword());
		usuarioEntity.setPassword(passwordHash);
		
		UsuarioEntity usuario = usuarioRepository.save(usuarioEntity);
		
		if(usuario!=null) {
			try {
				File saveFile = new ClassPathResource("static/usuario_foto").getFile();
				Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+foto.getOriginalFilename());
				
				Files.copy(foto.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
				
		model.addAttribute("registroCorrecto", "Registro Correcto");
		model.addAttribute("usuario", new UsuarioEntity());
		
	}

	@Override
	public boolean validarUsuario(UsuarioEntity usuarioEntity, HttpSession session) {
		UsuarioEntity usuarioEncontradoPorCorreo = 
				usuarioRepository.findByCorreo(usuarioEntity.getCorreo());
	
		if(usuarioEncontradoPorCorreo == null) {
			return false;
		}

		if(!Utilitarios.checkPassword(usuarioEntity.getPassword(), 
				usuarioEncontradoPorCorreo.getPassword())) {
			return false;
		}
		
		session.setAttribute("usuario", usuarioEncontradoPorCorreo.getCorreo());
		
		return true;
	}

	@Override
	public UsuarioEntity buscarUsuarioPorCorreo(String correo) {
		// TODO Auto-generated method stub
		return usuarioRepository.findByCorreo(correo);
	}

}
