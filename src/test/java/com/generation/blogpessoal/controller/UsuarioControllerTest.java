package com.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;
import com.generation.blogpessoal.util.JwtHelper;
import com.generation.blogpessoal.util.TestBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private static final String BASE_URL = "/usuarios";
	private static final String USUARIO = "root@root.com";
	private static final String SENHA = "rootroot";
	
	@BeforeAll
	void inicio() {
		usuarioRepository.deleteAll();
		usuarioService.cadastrarUsuario(TestBuilder.criarUsuario(null, "Root", USUARIO, SENHA));
	}
	
	@Test
	@DisplayName("01 - Deve cadastrar um novo usuário com sucesso")
	void deveCadastrarUsuario() {
		//Given - o que quer testar
		Usuario usuario = TestBuilder.criarUsuario(null, "Thuany", "thuany@email.com.br",  "12345678");
		
		//When - ação principal do teste
		HttpEntity<Usuario> requisicao = new HttpEntity<>(usuario);
		ResponseEntity<Usuario> resposta = testRestTemplate.exchange(
				BASE_URL + "/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
		
		//Then - verificar se a resposta foi a esperada
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertNotNull(resposta.getBody());
		
	}	
	@Test
	@DisplayName("02 - não deve cadastrar usuario duplicado")
	void naodeveCadastrarUsuarioDuplicado() {
		//Given
		Usuario usuario = TestBuilder.criarUsuario(null,"Rafa", "rafa@email.com.br"
		, "12345678");
		usuarioService.cadastrarUsuario(usuario);
		//when
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(usuario);
		ResponseEntity<Usuario> resposta = testRestTemplate
				.exchange(BASE_URL + "/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
		
		//then
		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
		assertNull(resposta.getBody());	
	
	}
	
	@Test
	@DisplayName("03 - Deve atualizar os dados do usuario com sucesso")
	void DeveAtualizarUmUsuarioo() {
		//Given
		Usuario usuario = TestBuilder.criarUsuario(null,"Nadia", "nadiaa@email.com.br", "12345678");
		Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(usuario);
		
		Usuario usuarioUpdate = TestBuilder.criarUsuario(usuarioCadastrado.get().getId(), "Nadia Caricatto", "nadia@email.com.br", "abc12345");
		
		//when
		String token = JwtHelper.obterToken(testRestTemplate, USUARIO, SENHA);
		HttpEntity<Usuario> requisicao = JwtHelper.criarRequisicaoComToken(usuarioUpdate, token);
		ResponseEntity<Usuario> resposta = testRestTemplate.exchange(
				BASE_URL + "/atualizar", HttpMethod.PUT, requisicao, Usuario.class);
		
		//then
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertNotNull(resposta.getBody());	
	}
	
	@Test
	@DisplayName("04 - Deve listar todos os usuários com sucesso")
	void deveListarTodosUsuarios() {
		
		// Given
		usuarioService.cadastrarUsuario(TestBuilder.criarUsuario(null, "Carina da Silva", "carinas@email.com.br", "abcd1234"));
		usuarioService.cadastrarUsuario(TestBuilder.criarUsuario(null, "Carina Bentlin", "carina_bentlina@email.com.br", "@1234abcd"));
		
		// When
		String token = JwtHelper.obterToken(testRestTemplate, USUARIO, SENHA);
		HttpEntity<Void> requisicao = JwtHelper.criarRequisicaoComToken(token);
		ResponseEntity<Usuario[]> resposta = testRestTemplate.exchange(
				BASE_URL, HttpMethod.GET, requisicao, Usuario[].class);
		
		// Then
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertNotNull(resposta.getBody());
	}
	
	@Test
	@DisplayName("05 - Deve buscar um usuário por ID com sucesso")
	void deveBuscarUsuarioPorId() {

	    // Given 
	    Usuario usuario = TestBuilder.criarUsuario(null, "Julia Andrade", "julia@email.com.br", "12345678");
	    Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(usuario);
	    Long idUsuario = usuarioCadastrado.get().getId();

	    // When 
	    String token = JwtHelper.obterToken(testRestTemplate, USUARIO, SENHA);
	    HttpEntity<Void> requisicao = JwtHelper.criarRequisicaoComToken(token);

	    ResponseEntity<Usuario> resposta = testRestTemplate.exchange(
	            BASE_URL + "/" + idUsuario, HttpMethod.GET, requisicao, Usuario.class);

	    // Then 
	    assertEquals(HttpStatus.OK, resposta.getStatusCode());
	    assertNotNull(resposta.getBody());
	    assertEquals("Julia Andrade", resposta.getBody().getNome());
	    assertEquals("julia@email.com.br", resposta.getBody().getUsuario());
	}

}
