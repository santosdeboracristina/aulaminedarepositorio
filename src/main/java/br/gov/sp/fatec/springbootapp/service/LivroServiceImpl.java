package br.gov.sp.fatec.springbootapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import br.gov.sp.fatec.springbootapp.entity.Livro;
import br.gov.sp.fatec.springbootapp.entity.Usuario;
import br.gov.sp.fatec.springbootapp.repository.LivroRepository;
import br.gov.sp.fatec.springbootapp.repository.UsuarioRepository;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service("livroService")
public class LivroServiceImpl implements LivroService {

    @Autowired
    private LivroRepository livroRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, 'ROLE_USER')")
    public Livro adicionarLivro(String identificadorUsuario, String titulo) {
        Usuario usuario = usuarioRepo.findTop1ByNomeOrEmail(identificadorUsuario, identificadorUsuario);
        if (usuario == null) {
            throw new UsernameNotFoundException(
                "Usuario com identificador " + identificadorUsuario + " não foi encontrado");
        }

        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setAutor(usuario);
        livroRepo.save(livro);
        return livro;

        }
    }