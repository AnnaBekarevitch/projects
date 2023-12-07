package ru.itmo.wp.form;

import ru.itmo.wp.domain.Post;

import javax.servlet.http.PushBuilder;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class GetCommentCredentials {
    @NotNull
    int postId;

    public int getPostId(){
        return postId;
    }
}
