package com.hiddleston.gitapplication;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Helper class, manage transfering json data to object data
 */
public class APIUtil {
    public static JSONObject jsonResult;

    public static JSONObject getUserInfo(String username) {
        String url = "http://api.github.com/users/" + username;
        HttpGet httpGet = new HttpGet(url);
        HttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "sophiesummer");
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (Exception e)  {
            Log.e("DID NOT CONNECT", "Connect fail");
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonResult = jsonObject;
        return jsonObject;
    }

    /**
     * transfer JSONObject data to User object
     * @param jsonResult the result from API call
     * @return a new User
     */
    public static User getUser(JSONObject jsonResult) {
        User user = new User();
        try {
            user.userName = jsonResult.getString("login");
            user.realName = jsonResult.getString("name");
            user.bio = jsonResult.getString("bio");
            user.email = jsonResult.getString("email");
            user.followers = jsonResult.getInt("followers");
            user.following = jsonResult.getInt("following");
            user.repoUrl = jsonResult.getString("repos_url");
            user.avatar_url = jsonResult.getString("avatar_url");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

}
