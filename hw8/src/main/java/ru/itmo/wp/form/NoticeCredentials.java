package ru.itmo.wp.form;

import javax.validation.constraints.*;

@SuppressWarnings("unused")
public class NoticeCredentials {
    @NotNull(message = "Your notice shouldn't be null")
    @NotBlank(message = "Your notice shouldn't be empty")
    @Size(min = 1, max = 255, message = "Your notice should be 1 - 255 symbols")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
