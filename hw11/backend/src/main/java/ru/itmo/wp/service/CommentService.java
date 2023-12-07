package ru.itmo.wp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.form.CommentCredentials;
import ru.itmo.wp.repository.CommentRepository;
import ru.itmo.wp.repository.PostRepository;

import java.util.List;
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(@Autowired CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findCommentsByPostId(long postId) {
        return commentRepository.findAllByPostIdOrderByCreationTimeDesc(postId);
    }
    public List<Comment> findAll() {
        return commentRepository.findAllByOrderByCreationTimeDesc();
    }

    public long countPosts(long postId){
        return commentRepository.countAllByPostId(postId);
    }
    public void writeComment(CommentCredentials writeForm) {

        Comment comment = new Comment();
        comment.setText(writeForm.getText());
        comment.setPost(writeForm.getPost());
        comment.setUser(writeForm.getUser());

        commentRepository.save(comment);

    }

}
