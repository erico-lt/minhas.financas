package com.erico.minhasfinancas.services.impl;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.erico.minhasfinancas.entites.Usuario;
import com.erico.minhasfinancas.exceptions.ErroAutenticacaoException;
import com.erico.minhasfinancas.exceptions.RegraNegocioException;
import com.erico.minhasfinancas.repositorys.UsuarioRepository;
import com.erico.minhasfinancas.services.UsuarioServices;

import jakarta.transaction.Transactional;

@Service
public class UsuarioServicesImpl implements UsuarioServices {
   
    private UsuarioRepository usuarioRepository;

    public UsuarioServicesImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> user = usuarioRepository.findByEmail(email);

        if (!user.isPresent()) {
            throw new ErroAutenticacaoException("Usuario não encontrado, verifique o email");
        }

        if (!user.get().getSenha().equals(senha)) {
            throw new ErroAutenticacaoException("Senha incorreta");
        }

        return user.get();
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {

        if(usuario.getNome().equals(null)) {
            throw new RegraNegocioException("Nome de usuario vazio revise seus dados");
        }

        try {

            validarEmail(usuario.getEmail());
            usuario.setDataCadastro(Instant.now());
            return usuarioRepository.save(usuario);

        } catch (NullPointerException e) {
            throw new RegraNegocioException(e.getMessage());
        }      
    }

    @Override
    public void validarEmail(String email) {

        boolean exists = usuarioRepository.existsByEmail(email);
        if (exists) {
            throw new RegraNegocioException("Email já cadastrado no banco de dados");
        }
    }

}
