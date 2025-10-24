package com.generation.blogpessoal.model;

import java.time.LocalDateTime;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@table(name = "tb_postagens")  

public class Postagem {
	
	@Id  //primary key (id)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(length = 100)
	@NotBlank(message = "O Atributo título é obrigatório")
	@Size(min = 5, max = 100, message = "O Atributo título deve conter no mínimo 05 e no máximo 100 caracteres")
	private String titulo;
	
	@Column(length = 1000)
	@NotBlank(message = "O Atributo texto é obrigatório")
	@Size(min = 10, max = 1000, message = "O Atributo texto deve conter no mínimo 10 e no máximo 1000 caracteres")
	private String texto;
	
	@UpdateTimestamp	
	private LocalDateTime data;
	
	@ManyToOne //muitas postagens para um tema
	@JsonIgnoreProperties("postagem") //evita o loop infinito na requisição
	private Tema tema;


	@ManyToOne //muitas postagens para um tema
	@JsonIgnoreProperties("postagem") //evita o loop infinito na hora de fazer a requisição
	private Usuario usuario;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}


	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	
	}
	
	




	