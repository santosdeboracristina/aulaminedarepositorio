package br.gov.sp.fatec.springbootapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.springbootapp.entity.Livro;
import br.gov.sp.fatec.springbootapp.service.LivroService;

@RestController
@RequestMapping(value ="livro")
@CrossOrigin

public class LivroController {

    @Autowired
    private LivroService livroService;

    @PostMapping(value = "/novolivro")
    public Livro cadastrarLivro (@RequestBody LivroDTO livro){
        return livroService.adicionarLivro(livro.getUsuario(),
        livro.getTitulo());
    }
}