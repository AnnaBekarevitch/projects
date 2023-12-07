package ru.itmo.wp.form;

import org.hibernate.annotations.CreationTimestamp;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class CommentCredentials {



    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 10000)
    private String text;

    @NotNull
    private User user;
    @NotNull
    private Post post;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
