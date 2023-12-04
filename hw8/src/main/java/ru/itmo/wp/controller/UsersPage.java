package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.NoticeCredentials;
import ru.itmo.wp.service.NoticeService;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UsersPage extends Page {
    private final UserService userService;
    private final NoticeService noticeService;

    public UsersPage(UserService userService, NoticeService noticeService) {
        this.noticeService = noticeService;
        this.userService = userService;
    }

    @GetMapping("/users/all")
    public String users(Model model, HttpSession httpSession) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("currentUser", getUser(httpSession));
        model.addAttribute("notices", noticeService.findAll());

        return "UsersPage";
    }

    @PostMapping("/users/all")
    public String updateDisabled(@RequestParam("action") String disabled,
                                 @RequestParam("id") String id,
                                 HttpSession httpSession) {
        User user = getUser(httpSession);
        if (user == null || user.getDisabled()) {
            setMessage(httpSession, "You can\\'t change status");
            return "redirect:logout";
        }
        userService.updateDisabled(Long.parseLong(id), !"Enable".equals(disabled));
        setMessage(httpSession, "Congrats, disabled status have been changed!");
        return "redirect:/users/all";
    }
}