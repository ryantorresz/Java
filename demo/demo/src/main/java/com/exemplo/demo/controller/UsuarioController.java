package com.exemplo.demo.controller;

import com.exemplo.demo.dto.UsuarioDTO;
import com.exemplo.demo.model.Usuario;
import com.exemplo.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Criar um novo usuário
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setAtivo(true);
        Usuario salvo = usuarioRepository.save(usuario);
        return ResponseEntity.ok(salvo);
    }

    // Listar usuários ativos
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuariosAtivos() {
        List<Usuario> usuarios = usuarioRepository.findByAtivoTrue();
        return ResponseEntity.ok(usuarios);
    }

    // Obter usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obterUsuario(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty() || !usuario.get().isAtivo()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario.get());
    }

    // Atualizar usuário
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO dadosAtualizados) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isEmpty() || !usuarioExistente.get().isAtivo()) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuario = usuarioExistente.get();
        usuario.setNome(dadosAtualizados.getNome());
        usuario.setEmail(dadosAtualizados.getEmail());
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(usuario);
    }

    // Exclusão lógica
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Long id) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isEmpty() || !usuarioExistente.get().isAtivo()) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuario = usuarioExistente.get();
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);

        return ResponseEntity.noContent().build();
    }
}