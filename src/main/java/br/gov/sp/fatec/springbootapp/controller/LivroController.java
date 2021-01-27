package br.gov.sp.fatec.springbootapp.controller;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.springbootapp.entity.Livro;
import br.gov.sp.fatec.springbootapp.repository.LivroRepository;
import br.gov.sp.fatec.springbootapp.service.LivroService;

@RestController
@RequestMapping(value ="livro")
@CrossOrigin

public class LivroController {

    @Autowired
    private LivroService livroService;

    @Autowired
    private LivroRepository livroRepo;

    @PostMapping(value = "/novolivro")
    @JsonView(View.LivroCompleto.class)
    public Livro cadastrarLivro (@RequestBody LivroDTO livro){
        return livroService.adicionarLivro(livro.getUsuario(),
        livro.getTitulo());
    }

    @GetMapping(value="/busca/{autor}")
    @JsonView(View.LivroCompleto.class)
    public List<Livro> buscaPorTitulo(
        @PathVariable("autor") String autor) {
            return livroRepo.findByAutorNomeOrAutorEmail(autor, autor);

        }
}