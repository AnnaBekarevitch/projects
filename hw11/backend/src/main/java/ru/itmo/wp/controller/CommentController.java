package ru.itmo.wp.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.CommentCredentials;
import ru.itmo.wp.form.GetCommentCredentials;
import ru.itmo.wp.form.PostCredentials;
import ru.itmo.wp.form.UserRegisterCredentials;
import ru.itmo.wp.service.CommentService;
import ru.itmo.wp.service.PostService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("posts/comment")
    public CommentCredentials writeComment(@RequestBody @Valid CommentCredentials  writeForm,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        commentService.writeComment(writeForm);
        return writeForm;
    }

    @GetMapping("posts/comments")
    public List<Comment> findComments(@Param("postId") long postId) {

        return commentService.findCommentsByPostId(postId);
    }

    @GetMapping("posts/comments_count")
    public long findCommentsCount(@Param("postId") long postId){
        return commentService.countPosts(postId);
    }
}
