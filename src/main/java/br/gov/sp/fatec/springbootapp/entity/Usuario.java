package br.gov.sp.fatec.springbootapp.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity // se eu so colocar @entity, vai dar erro, pois o nome da minha classe n eh o
        // mesmo do bd
@Table(name = "usr_usuario") // resolvendo problema de cima
public class Usuario {

    @Id // mapeando a chave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // n posso receber o id, ele eh auto increment
    @Column(name = "usr_id") // mapeando a tabela
    private Long id;

    @Column(name = "usr_nome", unique=true, length = 20, nullable = false)
    private String nome;

    @Column(name = "usr_senha", unique=true, length = 100, nullable = false)
    private String senha;

    @ManyToMany(fetch = FetchType.EAGER) //quando eu procurar um usuario, o EAGER ja tras as autorizacoes tambem
    @JoinTable(name = "uau_usuario_autorizacao",
        joinColumns = { @JoinColumn(name="usr_id") },
        inverseJoinColumns = { @JoinColumn(name = "aut_id")})
    private Set<Autorizacao> autorizacoes;

    public Long getId() {
        return id;
    }

    public Set<Autorizacao> getAutorizacoes() {
        return this.autorizacoes;
    }

    public void setAutorizacoes(Set<Autorizacao> autorizacoes) {
        this.autorizacoes = autorizacoes;
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "livroUsr")
	private Set<Livro> livros;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    public void setLivros(Set<Livro> livros) {
		this.livros = livros;
	}
	
}