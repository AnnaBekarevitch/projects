package ru.itmo.wp.form.validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.PostCredentials;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.service.PostService;
import ru.itmo.wp.service.UserService;


@Component
public class PostCredentialsWritePostValidator implements Validator {
    private final PostService postService;

    public PostCredentialsWritePostValidator(PostService postService) {
        this.postService = postService;
    }

    public boolean supports(Class<?> clazz) {
        return UserCredentials.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            PostCredentials enterForm = (PostCredentials) target;

            //String s = enterForm.getTags();
            //s.trim().split(" ")

            //if (userService.findByLoginAndPassword(enterForm.getLogin(), enterForm.getPassword()) == null) {
              //  errors.rejectValue("password", "password.invalid-login-or-password", "invalid login or password");
            //}
        }
    }
}
