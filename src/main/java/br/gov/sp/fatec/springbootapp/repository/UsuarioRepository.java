package br.gov.sp.fatec.springbootapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.gov.sp.fatec.springbootapp.entity.Usuario;

/* O Repositório da entidade Usuario, possui uma consulta
 que busca e tras O PRIMEIRO REGISTRO de um Usuário pelo Nome ou Endereço de E-mail. 
 
 E o a anotacao @Query faz a consulta buscando todos os usuários que possuem o texto contido
no parâmetro "nome" em qualquer lugar de seu atributo nome e é
equivalente ao query method findByNomeContains String nome)*/

public interface UsuarioRepository extends JpaRepository<Usuario,Long>{

    public Usuario findTop1ByNomeOrEmail (String nome, String email);

    @Query("select u from Usuario u where u.nome = ?1") //dentro dele a gente coloca a consulta, SEM USAR o SQL, e sim usar o jpql.
    public Usuario buscaUsuarioPorNome(String nome); //Por eu nao estar usando o padrao do spring data, ele nao vai gerar o metodo, para isso uso a @QUERY

    
}