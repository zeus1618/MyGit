package com.assignment.myapplication.api;

public class UserInfo {
    private int id;
    private String name;
    private String login;
    private String avatar_url;
    private String company;
    private String blog;
    private String location;
    private String email;
    private int followers;
    private int following;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getAvatarUrl() {
        return avatar_url;
    }

    public String getCompany() {
        return company;
    }

    public String getBlog() {
        return blog;
    }

    public String getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public String getFollowers() {
        return convertCount(followers) + " followers";
    }

    public String getFollowing() {
        return convertCount(following) + " following";
    }

    private String convertCount(int count) {
        if (count >= 1000) {
            return count / 1000.0 + "k";
        } else {
            return String.valueOf(count);
        }
    }
}
