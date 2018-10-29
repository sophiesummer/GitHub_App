package com.hiddleston.gitapplication;


import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable{

    public String userName;
    public String realName;
    public String website;
    public String bio;
    public String email;
    public String repoUrl;
    public String avatar_url;
    public String create_date;

    public String followers_url;
    public String following_url;

    public int followers;
    public int following;
    public int repo_count;
    public String idKey;

    public List<Repository> repos;
    public List<FollowInfo> followersList;
    public List<FollowInfo> followingList;



    public Repository repository;


    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }


    public User() {

    }


    public static void transToUser(User user, JSONObject jsonResult) {

        try {
            user.userName = jsonResult.getString("login");
            user.realName = jsonResult.getString("name");
            user.bio = jsonResult.getString("bio");
            user.email = jsonResult.getString("email");
            user.followers = jsonResult.getInt("followers");
            user.following = jsonResult.getInt("following");
            user.repoUrl = jsonResult.getString("repos_url");
            user.avatar_url = jsonResult.getString("avatar_url");
            user.website = jsonResult.getString("html_url");
            user.repoUrl = jsonResult.getString("repos_url");
            user.repo_count = jsonResult.getInt("public_repos");
            user.create_date = jsonResult.getString("created_at").substring(0, 10);
            user.followers_url = jsonResult.getString("followers_url");
            String following_url_orig = jsonResult.getString("following_url");
            int ind = following_url_orig.indexOf('{');
            user.following_url = following_url_orig.substring(0, ind);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
