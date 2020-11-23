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
import br.gov.sp.fatec.springbootapp.entity.Usuario;
import br.gov.sp.fatec.springbootapp.repository.AutorizacaoRepository;
import br.gov.sp.fatec.springbootapp.repository.UsuarioRepository;

@SpringBootTest //quando eu rodar esse teste, ele vai inciar o spring, criar os repositorios, conectar com o banco de dados, e posso usar o autowired
@Transactional //ele abre uma transacao, cada metodo dessa classe abre uma transacao nova
class SpringBootAppApplicationTests {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired //eu tenho que colocar o autowired aqui para que o spring coloque alguma coisa dentro desse autRepo, caso contrario, ficaria como NULL
    private AutorizacaoRepository autRepo;

    @BeforeAll
    static void init(@Autowired JdbcTemplate jdbcTemplate){
        jdbcTemplate.update("insert into usr_usuario (usr_nome, usr_senha) values (?,?)", "Debora", "SenhaFr4ca");
        jdbcTemplate.update("insert into aut_autorizacao (aut_nome) values(?)","ROLE_ADMIN");
        jdbcTemplate.update("insert into uau_usuario_autorizacao (usr_id, aut_id) values (?,?)",1L, 1L);
    }

	@Test
	void contextLoads() {
        
    }
    
    @Test
    void testaInsercao(){
        final Usuario usuario = new Usuario();
        usuario.setNome("Brian");
        usuario.setSenha("SenhaF0rte");
        usuario.setAutorizacoes(new HashSet<Autorizacao>());
        final Autorizacao aut = new Autorizacao();
        aut.setNome("ROLE_USUARIO");
        autRepo.save(aut);
        usuario.getAutorizacoes().add(aut);
        usuarioRepo.save(usuario);
        assertNotNull(usuario.getAutorizacoes().iterator().next().getId());
    }
    
    @Test
    void testaInsercaoAutorizacao(){
        final Usuario usuario = new Usuario();
        usuario.setNome("Angel");
        usuario.setSenha("SenhaF0rte");
        usuarioRepo.save(usuario);
        final Autorizacao aut = new Autorizacao(); //crio uma nova autorizacao
        aut.setNome("ROLE_USUARIO2"); //nomeio ela 
        aut.setUsuarios(new HashSet<Usuario>()); //
        aut.getUsuarios().add(usuario);
        autRepo.save(aut);
        assertNotNull(aut.getUsuarios().iterator().next().getId());
    }

    @Test
    void testaAutorizacao(){
        final Usuario usuario = usuarioRepo.findById(1L).get();
        assertEquals("ROLE_ADMIN", usuario.getAutorizacoes().iterator().next().getNome());
    }
    
    @Test
    void testaUsuario(){
        final Autorizacao aut = autRepo.findById(1l).get();
        assertEquals("Debora", aut.getUsuarios().iterator().next().getNome());
    }

    @Test
    void testaBuscaUsuarioNomeContains(){
        final List<Usuario> usuarios = usuarioRepo.findByNomeContainsIgnoreCase("e");
        assertFalse(usuarios.isEmpty());
    }

    @Test
    void testaBuscaUsuarioNome(){
        final Usuario usuario = usuarioRepo.findByNome("Debora");
        assertNotNull(usuario);
    }

    @Test
    void testaBuscaUsuarioNomeQuery(){
        final Usuario usuario = usuarioRepo.buscaUsuarioPorNome("Debora");
        assertNotNull(usuario);
    }

    @Test
    void testaBuscaUsuarioNomeSenha(){
        final Usuario usuario = usuarioRepo.findByNomeAndSenha("Debora", "SenhaFr4ca");
        assertNotNull(usuario);
    }

     @Test
    void testaBuscaUsuarioNomeSenhaQuery(){
        final Usuario usuario = usuarioRepo.buscaUsuarioPorNomeESenha("Debora", "SenhaFr4ca");
        assertNotNull(usuario);
    }

    @Test
    void testaBuscaUsuarioNomeAutorizacao(){
        final List<Usuario> usuarios = usuarioRepo.findByAutorizacoesNome("ROLE_ADMIN");
        assertFalse(usuarios.isEmpty());
    }

    @Test
    void testaBuscaUsuarioNomeAutorizacaoQuery(){
        final List<Usuario> usuarios = usuarioRepo.buscaPorNomeAutorizacao("ROLE_ADMIN");
        assertFalse(usuarios.isEmpty());
    }
}

