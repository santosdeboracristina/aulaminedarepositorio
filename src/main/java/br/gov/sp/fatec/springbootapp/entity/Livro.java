package br.gov.sp.fatec.springbootapp.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity //se eu so colocar @entity, vai dar erro, pois o nome da minha classe n eh o mesmo do bd
@Table(name = "liv_livros") //resolvendo problema de cima

public class Livro {
    @Id //mapeando a chave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //n posso receber o id, ele eh auto increment
    @Column(name = "liv_id") //mapeando a tabela
    private Long id;

    @Column(name = "liv_titulo", length=150, nullable = false)
    private String tituloLivro;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "liv_titulo_usr_id")
  
    private Usuario livroUsr;

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getLivroUsr(){
        return livroUsr;
    }

    public void setLivroUsr(Usuario livroUsr){
        this.livroUsr = livroUsr;
    
    }
    
}