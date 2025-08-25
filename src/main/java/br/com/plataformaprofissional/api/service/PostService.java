package br.com.plataformaprofissional.api.service;

import br.com.plataformaprofissional.api.model.Post;
import br.com.plataformaprofissional.api.repository.ForumRepository;
import br.com.plataformaprofissional.api.repository.PostRepository;
import br.com.plataformaprofissional.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Post createPost(Long forumId, Long autorId, Post post) {
        // Associa o post ao fórum e ao autor antes de salvar
        return forumRepository.findById(forumId).map(forum -> {
            return usuarioRepository.findById(autorId).map(autor -> {
                post.setForum(forum);
                post.setAutor(autor);
                return postRepository.save(post);
            }).orElseThrow(() -> new RuntimeException("Autor não encontrado!"));
        }).orElseThrow(() -> new RuntimeException("Fórum não encontrado!"));
    }
}