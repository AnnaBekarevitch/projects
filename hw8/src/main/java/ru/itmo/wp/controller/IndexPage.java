package ru.itmo.wp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itmo.wp.service.NoticeService;

import javax.servlet.http.HttpSession;

@Controller
public class IndexPage extends Page {

    @Autowired
    private NoticeService noticeService;

    @GetMapping({"", "/"})
    public String index(Model model) {
        model.addAttribute("notices", noticeService.findAll());
        return "IndexPage";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        unsetUser(httpSession);
        setMessage(httpSession, "Good bye!");
        return "redirect:";
    }


}
