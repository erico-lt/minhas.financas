
package com.erico.minhasfinancas.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erico.minhasfinancas.entites.Usuario;
import com.erico.minhasfinancas.repositorys.UsuarioRepository;
import com.erico.minhasfinancas.services.UsuarioServices;
import com.erico.minhasfinancas.services.impl.exceptions.RecursoNaoEncontradoException;
import com.erico.minhasfinancas.services.impl.exceptions.RegraNegocioException;

import jakarta.transaction.Transactional;

@Service
public class UsuarioServicesImpl implements UsuarioServices {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario obterPorId(Long id) {

        Optional<Usuario> obj = usuarioRepository.findById(id);

        return obj.orElseThrow(() -> new RecursoNaoEncontradoException(id));
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> user = usuarioRepository.findByEmail(email);

        if (!user.isPresent()) {
            throw new RegraNegocioException("Usuario nao encontrado, verifique se o email informado esta correto");
        }

        if (!user.get().getSenha().equals(senha)) {
            throw new RegraNegocioException("Senha incorreta");
        }

        return user.get();
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
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
