package br.gov.sp.fatec.springbootapp.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity //se eu so colocar @entity, vai dar erro, pois o nome da minha classe n eh o mesmo do bd
@Table(name = "aut_autorizacao") //resolvendo problema de cima
public class Autorizacao {

    @Id //mapeando a chave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //n posso receber o id, ele eh auto increment
    @Column(name = "aut_id") //mapeando a tabela
    private Long id;

    @Column(name = "aut_nome")
    private String nome;

    @ManyToMany (fetch = FetchType.LAZY, mappedBy = "autorizacoes") //em que lugar de usuario esta mapeado esse relacionamento "many2many?" autorizacao + usuario no atributo autorizacoes
    private Set<Usuario> usuarios;


    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Usuario> getUsuarios(){
        return this.usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios){
        this.usuarios = usuarios;
    }
    
}