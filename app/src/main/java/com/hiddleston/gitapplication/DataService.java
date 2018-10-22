package com.hiddleston.gitapplication;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataService {

    public static List<Repository> getRepoData() {
        List<Repository> repoData = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            repoData.add(
                    new Repository("Repo" + i, "dkajlda",
                            "This is a huge repo", "www.github"));
        }
        return repoData;
    }


}
