package ru.itmo.wp.form;

import javax.validation.constraints.*;

public class PostCredentials {

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 2, max = 60)
    private String title;

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 2, max = 360)
    private String text;


    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 2, max = 60)
    @Pattern(regexp = "\\s*([a-zA-Z]+\\s+)*[a-zA-Z]*\\s*",
            message = "Expected lowercase or uppercase Latin letters or whitespaces")
    String tags;

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
