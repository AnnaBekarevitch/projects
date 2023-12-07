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
public class PostController {
    private final PostService postService;

    public PostController(PostService postService, CommentService commentService) {

        this.postService = postService;
    }

    @GetMapping("posts")
    public List<Post> findPosts() {
        return postService.findAll();
    }

    @PostMapping("posts")
    public PostCredentials write(@RequestBody @Valid PostCredentials writeForm,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        postService.write(writeForm);
        return writeForm;
    }
}
