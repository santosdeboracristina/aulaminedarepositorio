package br.gov.sp.fatec.springbootapp.service;

import br.gov.sp.fatec.springbootapp.entity.Livro;

public interface LivroService {

    public Livro adicionarLivro(String identificadorUsuario, String titulo);

}
