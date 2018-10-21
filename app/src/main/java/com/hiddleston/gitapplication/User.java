package com.hiddleston.gitapplication;

public class User {
    public String userName;
    public String realName;
    public String website;
    public String bio;
    public String email;
    public String repoUrl;


    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }


    public User() {

    }

    public User(String userName, String realName, String website,
                String bio, String email) {
        this.userName = userName;
        this.realName = realName;
        this.website = website;
        this.bio = bio;
        this.email = email;
    }
}
