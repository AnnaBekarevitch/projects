package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequestMapping("post")
@Controller
public class PostPage extends Page {
    final private PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }



    @GetMapping({"", "/"})
    public String postPage(Model model) {
        return "PostPage";
    }


    @GetMapping("{id}")
    public String postPage(Model model, @PathVariable String id, HttpSession httpSession) {
        model.addAttribute("comment", new Comment());
        try {
            Long currentId = Long.valueOf(id);
            model.addAttribute("post", postService.findById(currentId));
        } catch (NumberFormatException e) {
            // ignore
        }
        model.addAttribute("user", getUser(httpSession));
        return "PostPage";
    }

    @PostMapping("{id}")
    public String postPageWriteComment(@PathVariable String id,
                                @Valid @ModelAttribute("comment") Comment comment,
                                BindingResult bindingResult,
                                HttpSession httpSession) {
        try {
            Long currentId = Long.valueOf(id);

            if (bindingResult.hasErrors()) {
                putMessage(httpSession, "Not valid comment");
                return "redirect:/post/"+id;
            }

            postService.writeComment(postService.findById(currentId), comment, getUser(httpSession));
            putMessage(httpSession, "You published new comment");

            return "redirect:/post/"+id;

        } catch (NumberFormatException e) {
            putMessage(httpSession, "No such post");
            return "PostPage";
        }
    }
}
