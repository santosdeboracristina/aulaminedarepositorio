package br.gov.sp.fatec.springbootapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import br.gov.sp.fatec.springbootapp.entity.Autorizacao;
import br.gov.sp.fatec.springbootapp.entity.Usuario;
import br.gov.sp.fatec.springbootapp.repository.AutorizacaoRepository;
import br.gov.sp.fatec.springbootapp.repository.UsuarioRepository;

@SpringBootTest //quando eu rodar esse teste, ele vai inciar o spring, criar os repositorios, conectar com o banco de dados, e posso usar o autowired
@Transactional //ele abre uma transacao, cada metodo dessa classe abre uma transacao nova
@Rollback //ao final de cada transacao, ele nao vai comitar, ele vai dar rollback (so deve ser usado no teste, pq no meu programa, n vai fazer nada)
class SpringBootAppApplicationTests {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired //eu tenho que colocar o autowired aqui para que o spring coloque alguma coisa dentro desse autRepo, caso contrario, ficaria como NULL
    private AutorizacaoRepository autRepo;

	@Test
	void contextLoads() {
        
    }
    
    @Test
    void testaInsercao(){
        Usuario usuario = new Usuario();
        usuario.setNome("Brian");
        usuario.setSenha("SenhaF0rte");
        usuario.setAutorizacoes(new HashSet<Autorizacao>());
        Autorizacao aut = new Autorizacao();
        aut.setNome("ROLE_USUARIO");
        autRepo.save(aut);
        usuario.getAutorizacoes().add(aut);
        usuarioRepo.save(usuario);
        assertNotNull(usuario.getAutorizacoes().iterator().next().getId());
    }
    
    @Test
    void testaInsercaoAutorizacao(){
        Usuario usuario = new Usuario();
        usuario.setNome("Angel");
        usuario.setSenha("SenhaF0rte");
        usuarioRepo.save(usuario);
        Autorizacao aut = new Autorizacao(); //crio uma nova autorizacao
        aut.setNome("ROLE_USUARIO2"); //nomeio ela 
        aut.setUsuarios(new HashSet<Usuario>()); //
        aut.getUsuarios().add(usuario);
        autRepo.save(aut);
        assertNotNull(aut.getUsuarios().iterator().next().getId());
    }

    @Test
    void testaAutorizacao(){
        Usuario usuario = usuarioRepo.findById(1L).get();
        assertEquals("ROLE_ADMIN", usuario.getAutorizacoes().iterator().next().getNome());
    }
    
    @Test
    void testaUsuario(){
        Autorizacao aut = autRepo.findById(1l).get();
        assertEquals("Debora", aut.getUsuarios().iterator().next().getNome());
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
    void testaBuscaUsuarioNomeSenha(){
        Usuario usuario = usuarioRepo.findByNomeAndSenha("Debora", "SenhaFr4ca");
        assertNotNull(usuario);
    }

    @Test
    void testaBuscaUsuarioNomeAutorizacao(){
        List<Usuario> usuarios = usuarioRepo.findByAutorizacoesNome("ROLE_ADMIN");
        assertFalse(usuarios.isEmpty());
    }
}

