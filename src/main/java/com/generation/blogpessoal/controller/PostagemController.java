package com.generation.blogpessoal.controller;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;
import com.generation.blogpessoal.repository.TemaRepository;

import jakarta.validation.Valid;

	@RestController
	@RequestMapping("/postagens") 
	@CrossOrigin(origins = "*", allowedHeaders = "*") //libere requisição de qualquer origem e qualquer cabeçalho
	
	public class PostagemController {
		
		@Autowired //anotação para injeção de dependência
		private PostagemRepository postagemRepository;  //injeção de dependência
		
		@Autowired //anotação para injeção de dependência
		private TemaRepository temaRepository;  //injeção de dependência
		
		@GetMapping //mapeia o método para responder a requisição GET do HTTP
		public ResponseEntity<List<Postagem>> getAll() {
			return (ResponseEntity<List<Postagem>>) ResponseEntity.ok(postagemRepository.findAll());
		}
		
		@GetMapping("/{id}")
		public ResponseEntity<Postagem> getById(@PathVariable Long id) {
			return (ResponseEntity<Postagem>) postagemRepository.findById(id)
					.map(resposta -> ResponseEntity.ok(resposta))
					.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
		}
		@GetMapping("/titulo/{titulo}") //mapeia o método para responder a requisição GET do HTTP
		public ResponseEntity<List<Postagem>> getAllByTitulo(@PathVariable String titulo) {
			return (ResponseEntity<List<Postagem>>) ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
		}
		
		@PostMapping
		public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem) {
			
			if (temaRepository.existsById(postagem.getTema().getId())) {
				
			
			postagem.setId(null);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
			
			// INSERT INTO tb_postagens (titulo, texto, data) VALUES (?, ?, ?);
		}
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O tema não existe!", null);
		
	}

		    	
		@PutMapping
		public ResponseEntity<Postagem> put(@Valid @RequestBody Postagem postagem){
			if (postagemRepository.existsById(postagem.getId())) {
				if (temaRepository.existsById(postagem.getTema().getId())) {
				postagem.setId(null);
				return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
				
				}
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O tema não existe!", null);
			}
			return ResponseEntity.notFound().build();
		}
		
		@ResponseStatus(HttpStatus.NO_CONTENT)  //indica que a resposta não tem conteúdo
		@DeleteMapping("/{id}")
		public void delete(@PathVariable Long id) {
				
			Optional<Postagem> postagem = postagemRepository.findById(id);
			
			if (postagem.isEmpty())
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			
			postagemRepository.deleteById(id);
}
	}
