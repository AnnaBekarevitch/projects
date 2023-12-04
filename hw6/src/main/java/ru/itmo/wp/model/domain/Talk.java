package ru.itmo.wp.model.domain;

import java.util.Date;

public class Talk {

    private long id;
    private long sourceUserId;
    private long targetUserId;
    private Date creationTime;
    private String targetUserLogin;

    private String sourceUserLogin;

    private String text;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSourceUserId() {
        return sourceUserId;
    }

    public void setSourceUserId(long sourceUserId) {
        this.sourceUserId = sourceUserId;
    }
    public long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public void setSourceUserLogin(String login) { this.sourceUserLogin = login;}
    public String getSourceUserLogin() { return sourceUserLogin;}

    public void setTargetUserLogin(String login) { this.targetUserLogin = login;}
    public String getTargetUserLogin() { return targetUserLogin;}
}
