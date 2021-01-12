package br.gov.sp.fatec.springbootapp.service;

import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import br.gov.sp.fatec.springbootapp.entity.Usuario;
import br.gov.sp.fatec.springbootapp.entity.Autorizacao;
import br.gov.sp.fatec.springbootapp.repository.UsuarioRepository;
import br.gov.sp.fatec.springbootapp.repository.AutorizacaoRepository;


@Service("segurancaService")
public class SegurancaServiceImpl implements SegurancaService {

    @Autowired
    private AutorizacaoRepository autRepo; //importo o repositorio de autorizacao

    @Autowired
    private UsuarioRepository usuarioRepo; //same here

    @Transactional  //ele garante que tudo que ocorre a baixo eh uma trasacao, so vai comitar se passar por tudo ate o final do metodo (caso contrario ele da rollback em tudo)
    public Usuario criarUsuario(String nome, String senha, String autorizacao){
        Autorizacao aut = autRepo.findByNome(autorizacao); //procura a autorizacao por nome
        if (aut == null){ //se nao encontrar, aí ...
            aut = new Autorizacao(); //ele cria uma nova autorizacao..
            aut.setNome(autorizacao); //poe o nome dela e
            autRepo.save(aut); //a salva =)
        }
        Usuario usuario = new Usuario(); //ja aqui ele cria um novo usuario...
        usuario.setNome(nome); //seta um nome pra ele...
        usuario.setSenha(senha); //uma senha...
        usuario.setAutorizacoes(new HashSet<Autorizacao>()); //atribui uma autorizacao a ele..
        usuario.getAutorizacoes().add(aut); //adiciona essa autorizacao as autorizacoes...
        usuarioRepo.save(usuario); //salva esse usuario que eu criei e...
        return usuario; //por fim, retorna o usuario que eu criei na linha 23 =)

    }
    
}