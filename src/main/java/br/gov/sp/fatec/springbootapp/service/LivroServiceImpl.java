package br.gov.sp.fatec.springbootapp.service;

import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import br.gov.sp.fatec.springbootapp.entity.Usuario;
import br.gov.sp.fatec.springbootapp.entity.Autorizacao;
import br.gov.sp.fatec.springbootapp.entity.Livro;
import br.gov.sp.fatec.springbootapp.repository.UsuarioRepository;
import br.gov.sp.fatec.springbootapp.repository.AutorizacaoRepository;
import br.gov.sp.fatec.springbootapp.repository.LivroRepository;


@Service("livroService")
public class LivroServiceImpl implements LivroService {


    @Autowired
    private UsuarioRepository usuarioRepo; //same here

    @Autowired
    private AutorizacaoRepository autRepo; //same here

     @Autowired
    private LivroRepository livRepo; //adicionando para livros

    @Transactional  //ele garante que tudo que ocorre a baixo eh uma trasacao, so vai comitar se passar por tudo ate o final do metodo (caso contrario ele da rollback em tudo)
    public Usuario criarUsuario(String nome, String senha, String livro){
        Livro liv = livRepo.findByTitulo(livro); //procura o livro por titulo
        Autorizacao aut = autRepo.findByNome(nome); //procura a autorizacao por nome
        if (liv == null){ //se nao encontrar, aí ...
            liv = new Livro(); //ele cria um novo livro..
            liv.setTitulo(livro); //poe o titulo nele e
            livRepo.save(liv); //o salva =)
        }
        Usuario usuario = new Usuario(); //ja aqui ele cria um novo usuario...
        usuario.setNome(nome); //seta um nome pra ele...
        usuario.setSenha(senha); //uma senha...
        usuario.setAutorizacoes(new HashSet<Autorizacao>()); //atribui uma autorizacao a ele..
        usuario.getAutorizacoes().add(aut); // adiciona essa autorizacao as autorizacoes...
        usuarioRepo.save(usuario); //salva esse usuario que eu criei e...
        return usuario; //por fim, retorna o usuario que eu criei na linha 21 =)

    }

    @Override
    public Livro criarLivro(String titulo) {
        // TODO Auto-generated method stub
        return null;
    }
    
}