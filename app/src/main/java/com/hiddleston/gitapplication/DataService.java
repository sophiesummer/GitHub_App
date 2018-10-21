package com.hiddleston.gitapplication;

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
