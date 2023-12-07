package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.CommentCredentials;
import ru.itmo.wp.form.PostCredentials;
import ru.itmo.wp.form.UserRegisterCredentials;
import ru.itmo.wp.repository.CommentRepository;
import ru.itmo.wp.repository.PostRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;


    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreationTimeDesc();
    }

    public void write(PostCredentials writeForm) {
        Post post = new Post();
        post.setTitle(writeForm.getTitle());
        post.setUser(writeForm.getUser());
        post.setText(writeForm.getText());
        postRepository.save(post);
    }



}
