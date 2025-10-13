package com.generation.blogpessoal.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

	@RestController
	@RequestMapping("/postagens") 
	@CrossOrigin(origins = "*", allowedHeaders = "*") //libere requisição de qualquer origem e qualquer cabeçalho
	
	public class PostagemController {
		
		@Autowired //anotação para injeção de dependência
		private PostagemRepository postagemRepository;  //injeção de dependência
		
		@GetMapping //mapeia o método para responder a requisição GET do HTTP
		public ResponseEntity<List<Postagem>> getAll() {
			return ResponseEntity.ok(postagemRepository.findAll());
		}
		
		
		
		
		
		
		
}

