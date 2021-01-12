package br.gov.sp.fatec.springbootapp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import br.gov.sp.fatec.springbootapp.entity.Autorizacao;
import br.gov.sp.fatec.springbootapp.entity.Livro;
import br.gov.sp.fatec.springbootapp.entity.Usuario;
import br.gov.sp.fatec.springbootapp.repository.AutorizacaoRepository;
import br.gov.sp.fatec.springbootapp.repository.LivroRepository;
import br.gov.sp.fatec.springbootapp.repository.UsuarioRepository;
import br.gov.sp.fatec.springbootapp.service.SegurancaService;

@SpringBootTest //quando eu rodar esse teste, ele vai inciar o spring, criar os repositorios, conectar com o banco de dados, e posso usar o autowired
@Transactional //ele abre uma transacao, cada metodo dessa classe abre uma transacao nova
class SpringBootAppApplicationTests {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired //eu tenho que colocar o autowired aqui para que o spring coloque alguma coisa dentro desse autRepo, caso contrario, ficaria como NULL
    private AutorizacaoRepository autRepo;

    @Autowired //eu tenho que colocar o autowired aqui para que o spring coloque alguma coisa dentro desse autRepo, caso contrario, ficaria como NULL
    private LivroRepository livRepo;

    @Autowired
    private SegurancaService segService;

    @BeforeAll
    static void init(@Autowired JdbcTemplate jdbcTemplate){
        jdbcTemplate.update("insert into usr_usuario (usr_nome, usr_senha) values (?,?)", "Debora", "SenhaFr4ca");
        jdbcTemplate.update("insert into aut_autorizacao (aut_nome) values(?)","ROLE_ADMIN");
        jdbcTemplate.update("insert into liv_livros (liv_titulo) values(?)","Anne_of_Green_Gables"); //add para livros
        jdbcTemplate.update("insert into uau_usuario_autorizacao (usr_id, aut_id) values (?,?)",1L, 1L);
        jdbcTemplate.update("insert into liv_titulo_usr_id (usr_id, liv_titulo) values (?,?)",1L, 1L); //add para livros
    }

	@Test
	void contextLoads() {
        
    }
    
    @Test
    void testaInsercao(){
        Usuario usuario = new Usuario();
        usuario.setNome("Joseph");
        usuario.setSenha("SenhaF0rte");
        usuario.setAutorizacoes(new HashSet<Autorizacao>());
        Autorizacao aut = new Autorizacao();
        aut.setNome("ROLE_AUTOR");
        autRepo.save(aut);
        usuario.getAutorizacoes().add(aut);
        usuarioRepo.save(usuario);
        assertNotNull(usuario.getAutorizacoes().iterator().next().getId());
    }
    
    @Test
    void testaInsercaoAutorizacao(){
        Usuario usuario = new Usuario();
        usuario.setNome("Dean");
        usuario.setSenha("SenhaF0rte");
        usuarioRepo.save(usuario);
        Autorizacao aut = new Autorizacao(); //crio uma nova autorizacao
        aut.setNome("ROLE_LEITOR"); //nomeio ela 
        aut.setUsuarios(new HashSet<Usuario>()); //
        aut.getUsuarios().add(usuario);
        autRepo.save(aut);
        assertNotNull(aut.getUsuarios().iterator().next().getId());
    }

    //testando insercao de novos livros
    @Test
    void testaInsercaoLivro(){
        Usuario usuario = new Usuario();
        usuario.setNome("Autor1");
        usuario.setSenha("SenhaLivroTeste");
        usuarioRepo.save(usuario);
        Livro liv = new Livro(); //crio um novo livro
        liv.setTituloLivro("Annie_Com_E"); //nomeio ele
        liv.setUsuario(new HashSet<Usuario>()); //
        liv.getUsuario().add(usuario);
        livRepo.save(liv);
        assertNotNull(liv.getUsuarios().iterator().next().getId());
    }

    @Test
    void testaAutorizacao(){
        Usuario usuario = usuarioRepo.findById(1L).get();
        assertEquals("ROLE_AUTOR", usuario.getAutorizacoes().iterator().next().getNome());
    }
    
    @Test
    void testaUsuario(){
        Autorizacao aut = autRepo.findById(1l).get();
        assertEquals("Joseph", aut.getUsuarios().iterator().next().getNome());
    }

    @Test
    void testaBuscaUsuarioNomeContains(){
        List<Usuario> usuarios = usuarioRepo.findByNomeContainsIgnoreCase("e");
        assertFalse(usuarios.isEmpty());
    }

    @Test
    void testaBuscaUsuarioNome(){
        Usuario usuario = usuarioRepo.findByNome("Debora");
        assertNotNull(usuario);
    }

    @Test
    void testaBuscaUsuarioNomeQuery(){
        Usuario usuario = usuarioRepo.buscaUsuarioPorNome("Debora");
        assertNotNull(usuario);
    }

    @Test
    void testaBuscaUsuarioNomeSenha(){
        Usuario usuario = usuarioRepo.findByNomeAndSenha("Debora", "SenhaFr4ca");
        assertNotNull(usuario);
    }

     @Test
    void testaBuscaUsuarioNomeSenhaQuery(){
        Usuario usuario = usuarioRepo.buscaUsuarioPorNomeESenha("Debora", "SenhaFr4ca");
        assertNotNull(usuario);
    }

    @Test
    void testaBuscaUsuarioNomeAutorizacao(){
        List<Usuario> usuarios = usuarioRepo.findByAutorizacoesNome("ROLE_ADMIN");
        assertFalse(usuarios.isEmpty());
    }

    @Test
    void testaBuscaUsuarioNomeAutorizacaoQuery(){
        List<Usuario> usuarios = usuarioRepo.buscaPorNomeAutorizacao("ROLE_AUTOR");
        assertFalse(usuarios.isEmpty());
    }

    @Test
    void testaServicoCriaUsuario(){
       Usuario usuario = segService.criarUsuario("normal", "senha123", "ROLE_LEITOR");
       assertNotNull(usuario);
    }
}


