package com.hiddleston.gitapplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FollowInfo {
    public String username;
    public String img_url;
    public String url;

    public FollowInfo() {

    }

    public FollowInfo(String username, String img_url, String url) {
        this.username = username;
        this.img_url = img_url;
        this.url = url;
    }

    public static List<FollowInfo> getFollowInfo(String json_data) {
        List<FollowInfo> result = new ArrayList<>();
        try {
            JSONArray follows = new JSONArray(json_data);
            int i = 0;
            while (!follows.isNull(i)) {
                JSONObject eachFollow = follows.getJSONObject(i);
                FollowInfo f = new FollowInfo(eachFollow.getString("login"),
                        eachFollow.getString("avatar_url"),
                        eachFollow.getString("url"));

                result.add(f);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
