package br.gov.sp.fatec.springbootapp.service;

import br.gov.sp.fatec.springbootapp.entity.Livro;

import br.gov.sp.fatec.springbootapp.entity.Usuario;

public interface UsuarioService extends UserDetailsService{

    public Usuario novoUsuario (String nome, String email, String senha, String nomeAutorizacao);

}