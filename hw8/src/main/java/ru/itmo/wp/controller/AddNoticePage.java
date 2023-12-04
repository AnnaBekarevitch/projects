package ru.itmo.wp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Notice;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.NoticeCredentials;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class AddNoticePage extends Page {
    @Autowired
    private NoticeService noticeService;

    @GetMapping("/addNotice")
    public String addNotice(Model model) {

        model.addAttribute("addNoticeForm", new NoticeCredentials());
        //model.addAttribute("notices", noticeService.findAll());
        return "AddNoticePage";
    }

    @PostMapping("/addNotice")
    public String addNotice(@Valid @ModelAttribute("addNoticeForm") NoticeCredentials addNoticeForm,
                            BindingResult bindingResult,
                            HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "AddNoticePage";
        }
        User user = getUser(httpSession);
        if (user == null || user.getDisabled()) {
            setMessage(httpSession, "You can\'t make notice without login or if you are disabled");
            return "redirect:/";
        }
        setMessage(httpSession, "Congrats, your notice have been created!");
        return "redirect:/";
        //return "addNotice";
    }
}
