package ru.itmo.web.hw4.model;

public class User {
    private final long id;
    private final String handle;
    private final String name;

    private final Color color;
    private boolean logged;
    private int numberOfPosts = 0;


    public User(long id, String handle, String name, Color color, boolean logged, int numberOfPosts) {
        this.id = id;
        this.handle = handle;
        this.name = name;
        this.color = color;
        this.logged = logged;
        this.numberOfPosts = numberOfPosts;
    }

    public long getId() {
        return id;
    }

    public String getHandle() {
        return handle;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public boolean getLogged() {
        return logged;
    }

    public void setLogged(boolean logged) { this.logged = logged; }

    public void setNumberOfPosts(int numberOfPosts) { this.numberOfPosts = numberOfPosts; }

    public int getNumberOfPosts() { return numberOfPosts; }
}