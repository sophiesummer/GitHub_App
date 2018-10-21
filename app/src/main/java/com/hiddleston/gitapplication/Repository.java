package com.hiddleston.gitapplication;

public class Repository {
    public String repoName;
    public String repoOwner;
    public String repoDescription;
    public String url;

    public Repository(){

    }

    public Repository(String name, String owner, String description, String url) {
        repoName = name;
        repoOwner = owner;
        repoDescription = description;
        this.url = url;
    }


}
