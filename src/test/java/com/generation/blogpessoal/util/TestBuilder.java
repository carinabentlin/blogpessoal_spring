package com.generation.blogpessoal.util;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.model.UsuarioLogin;

public class TestBuilder { // classe de utilidade para criar objetos de teste

    public static Usuario criarUsuario(Long id, String nome, String usuario, String senha) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setId(id);
        novoUsuario.setNome(nome);
        novoUsuario.setUsuario(usuario);
        novoUsuario.setSenha(senha);
        novoUsuario.setFoto("-");
        return novoUsuario;
    }

    public static UsuarioLogin criarUsuarioLogin(Long id, String nome, String usuario, String senha, String token) {
        UsuarioLogin novoUsuarioLogin = new UsuarioLogin();
        novoUsuarioLogin.setId(id);
        novoUsuarioLogin.setNome(nome);
        novoUsuarioLogin.setUsuario(usuario);
        novoUsuarioLogin.setSenha(senha);
        novoUsuarioLogin.setToken(token);
        novoUsuarioLogin.setFoto("-");
        return novoUsuarioLogin;
    }

    // Sobrecarga simples (só para login sem precisar de id/nome/foto)
    public static UsuarioLogin criarUsuarioLogin(String usuario, String senha) {
        UsuarioLogin novoUsuarioLogin = new UsuarioLogin();
        novoUsuarioLogin.setUsuario(usuario);
        novoUsuarioLogin.setSenha(senha);
        return novoUsuarioLogin;
    }
}
