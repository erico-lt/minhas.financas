package com.erico.minhasfinancas.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erico.minhasfinancas.entites.Usuario;
import com.erico.minhasfinancas.exceptions.ErroAutenticacaoException;
import com.erico.minhasfinancas.exceptions.RegraNegocioException;
import com.erico.minhasfinancas.repositorys.UsuarioRepository;
import com.erico.minhasfinancas.services.UsuarioServices;

import jakarta.transaction.Transactional;

@Service
public class UsuarioServicesImpl implements UsuarioServices {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario obterPorId(Long id) {

        Optional<Usuario> obj = usuarioRepository.findById(id);

        return obj.orElseThrow(() -> new RegraNegocioException("Usuario nao encontrado com o id informado"));
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> user = usuarioRepository.findByEmail(email);

        if (!user.isPresent()) {
            throw new ErroAutenticacaoException("Usuario nao encontrado com email informado esta incorreto");
        }

        if (!user.get().getSenha().equals(senha)) {
            throw new ErroAutenticacaoException("Senha incorreta");
        }

        return user.get();
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {

        if (usuario.getNome().equals(null) || usuario.getNome().strip().equals("")) {
            throw new RegraNegocioException("Nome de usuario vazio, por favor adicione um nome");
        }

        try {

            validarEmail(usuario.getEmail());
            return usuarioRepository.save(usuario);
        } catch (RegraNegocioException e) {
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
