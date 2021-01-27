package br.gov.sp.fatec.springbootapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.fatec.springbootapp.entity.Livro;

public interface LivroRepository extends JpaRepository<Livro,Long> {

    public List <Livro> findByAutorNomeOrAutorEmail (String nome, String email);
    
}