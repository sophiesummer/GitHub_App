package com.hiddleston.gitapplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Repository> getRepository(String repo_json) {
        List<Repository> repos = new ArrayList<>();
        try {
            JSONArray repositories = new JSONArray(repo_json);
            int i = 0;
            while (!repositories.isNull(i)) {
                JSONObject eachRepo = repositories.getJSONObject(i);
                Repository repo = new Repository(eachRepo.getString("name"),"",
                        eachRepo.getString("description"), eachRepo.getString("html_url"));
                JSONObject owner = eachRepo.getJSONObject("owner");
                repo.repoOwner = owner.getString("login");
                repos.add(repo);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return repos;
    }


}
