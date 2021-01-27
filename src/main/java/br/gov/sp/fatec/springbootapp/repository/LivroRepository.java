package br.gov.sp.fatec.springbootapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import br.gov.sp.fatec.springbootapp.entity.Livro;

public interface LivroRepository extends JpaRepository<Livro,Long> {

    @PreAuthorize("isAuthenticated()")
    public List <Livro> findByAutorNomeOrAutorEmail (String nome, String email);
    
}