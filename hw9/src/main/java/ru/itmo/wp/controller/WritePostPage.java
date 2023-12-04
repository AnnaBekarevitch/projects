package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.form.PostCredentials;
import ru.itmo.wp.form.validator.PostCredentialsWritePostValidator;
import ru.itmo.wp.form.validator.UserCredentialsEnterValidator;
import ru.itmo.wp.repository.TagRepository;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.service.TagService;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
public class WritePostPage extends Page {
    private final UserService userService;
    private final TagService tagService;
    private final PostCredentialsWritePostValidator postCredentialsWritePostValidator;



    public WritePostPage(UserService userService, TagService tagService, PostCredentialsWritePostValidator postCredentialsWritePostValidator) {
        this.userService = userService;
        this.tagService = tagService;
        this.postCredentialsWritePostValidator = postCredentialsWritePostValidator;
    }

    @InitBinder("enterForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(postCredentialsWritePostValidator);
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @GetMapping("/writePost")
    public String writePostGet(Model model) {
        model.addAttribute("postForm", new PostCredentials());
        return "WritePostPage";
    }


    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @PostMapping("/writePost")
    public String writePostPost(@Valid @ModelAttribute("postForm") PostCredentials postForm,
                                BindingResult bindingResult,
                                HttpSession httpSession) {
        if (bindingResult.hasErrors() ) {
            return "WritePostPage";
        }


        Post post = new Post();
        Set<Tag> tags = new HashSet<>();
        for (String name : postForm.getTags().trim().split("\\s+")) {
            Tag foundTag = tagService.findByName(name);
            if (foundTag == null) {
                foundTag = new Tag(name);
                tagService.save(foundTag);
            }
            tags.add(foundTag);
        }
        post.setTags(tags);
        post.setTitle(postForm.getTitle());
        post.setText(postForm.getText());

        userService.writePost(getUser(httpSession), post);
        putMessage(httpSession, "You published new post");

        return "redirect:/myPosts";
    }
}
