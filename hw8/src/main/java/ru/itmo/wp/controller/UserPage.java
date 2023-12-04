package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.wp.service.NoticeService;
import ru.itmo.wp.service.UserService;

@RequestMapping("user")
@Controller
public class UserPage extends Page {
    private final UserService userService;
    private final NoticeService noticeService;

    public UserPage(UserService userService, NoticeService noticeService) {
        this.userService = userService;
        this.noticeService = noticeService;
    }
    @GetMapping("")
    public String user(Model model) {
        //model.addAttribute("user", null);
        model.addAttribute("notices", noticeService.findAll());
        return "UserPage";
    }

    @GetMapping("{id}")
    public String user(Model model, @PathVariable String id) {
        try {
            Long currentId = Long.valueOf(id);
            model.addAttribute("user", userService.findById(currentId));

        } catch (NumberFormatException e) {
            //model.addAttribute("user", null);
        }
        model.addAttribute("notices", noticeService.findAll());
        return "UserPage";
    }

}
