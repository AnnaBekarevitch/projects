package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class TalksPage extends Page {

    private final TalkService talkService = new TalkService();
    private final UserService userService = new UserService();

    protected void action(HttpServletRequest request, Map<String, Object> view) {
        User user = getUser();
        if (user == null) {
            throw new RedirectException("/index");
        }
        view.put("talks", talkService.findByUserId(user.getId()));
        view.put("users", userService.findAll());
    }

    private void submit(HttpServletRequest request, Map<String, Object> view)throws ValidationException {
        Talk talk = new Talk();
        talk.setSourceUserId(getUser().getId());
        User targetUser = userService.findByLogin(request.getParameter("targetUserLogin"));
        if (targetUser == null) {
            setMessage("No such user");
            throw new RedirectException("/talks");
        }
        talk.setTargetUserId(targetUser.getId());

        talk.setText(request.getParameter("text"));
        talkService.submit(talk);
        setMessage("You are successfully send message!");
        throw new RedirectException("/talks");
    }

}
