package com.assignment.myapplication.api;

public class RepoInfo {
    private int id;
    private String name;
    private String full_name;
    private String description;
    private int watchers;
    private int forks;
    private int open_issues;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return full_name;
    }

    public String getDescription() {
        return description;
    }

    public String getWatchers() {
        return convertCount(watchers);
    }

    public String getForks() {
        return convertCount(forks);
    }

    public String getOpenIssues() {
        return convertCount(open_issues);
    }

    private String convertCount(int count) {
        if (count >= 1000) {
            return count / 1000.0 + "k";
        } else {
            return String.valueOf(count);
        }
    }
}
