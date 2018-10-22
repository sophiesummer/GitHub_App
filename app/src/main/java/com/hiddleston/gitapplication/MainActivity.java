package com.hiddleston.gitapplication;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements RepositoryFragment.OnItemSelectListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Fragment profileFragment;
    private Fragment repositoryFragment;
    private Fragment followersFragment;
    private Fragment followingFragment;

    private Fragment repositoryDetailFragment;

    private Button profileButton;
    private Button repositoryButton;
    private Button followersButton;
    private Button followingButton;
    private Button searchButton;

    private String defaultUserName = "sophiesummer";
    private User defaultUser = new User();

    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JSONObject j = APIUtil.getUserInfo("sophiesummer");
        User u = APIUtil.getUser(j);
        Log.d(TAG,"here is json " + u.repoUrl);

        if (profileFragment == null) {
            profileFragment = new ProfileFragment();
        }

        if (repositoryFragment == null) {
            repositoryFragment = new RepositoryFragment();
        }

        if (followersFragment == null) {
            followersFragment = new FollowersFragment();
        }

        if (followingFragment == null) {
            followingFragment = new FollowingFragment();
        }


        repositoryDetailFragment = new RepositoryDetailFragment();  //put in a url
        Log.d(TAG, "reach detail");
        getSupportFragmentManager().beginTransaction().
                replace(R.id.content_layout, repositoryDetailFragment).commit();

//        getSupportFragmentManager().beginTransaction().
//                replace(R.id.content_layout, profileFragment).commit();

        profileButton = findViewById(R.id.profile_button);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.content_layout, profileFragment).commit();
            }
        });

        repositoryButton = findViewById(R.id.repositories_button);
        repositoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.content_layout, repositoryFragment).commit();
            }
        });

        followersButton = findViewById(R.id.followers_button);
        followersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.content_layout, followersFragment).commit();
            }
        });


        followingButton = findViewById(R.id.following_button);
        followingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.content_layout, followingFragment).commit();
            }
        });

        setCount();
        editText = findViewById(R.id.search_text);

        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncTask asyncTask = new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] objects) {
                        OkHttpClient client = new OkHttpClient();

                        String text = editText.getText().toString();
                        if (text == null) {
                            text = defaultUserName;
                        }
                        String url = "http://api.github.com/users/" + text;
                        Request request = new Request.Builder().url(url).
                                header("User-Agent", "sophiesummer").
                                build();
                        Response response = null;
                        try {
                            response = client.newCall(request).execute();
                            return response.body().string();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        try {
                            JSONObject jsonObject = new JSONObject(o.toString());
                            User.transToUser(defaultUser, jsonObject);
                            setCount();
                            setFragment();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.print(o.toString());
                    }
                }.execute();
            }
        });
    }

    @Override
    public void onItemSelected(int position) {
        repositoryDetailFragment = new RepositoryDetailFragment();  //put in a url
        Log.d(TAG, "reach detail");
        getSupportFragmentManager().beginTransaction().
                replace(R.id.content_layout, repositoryDetailFragment).commit();
    }

    public void setCount() {
        String buttonTextRepo = "Repository:\n" + defaultUser.repo_count;
        repositoryButton.setText(buttonTextRepo);
        String buttonTextFollowers = "Followers:\n" + defaultUser.followers;
        followersButton.setText(buttonTextFollowers);
        String buttonTextFollowing = "Following:\n" + defaultUser.following;
        followingButton.setText(buttonTextFollowing);

    }

    public void setFragment() {
        profileFragment = new ProfileFragment().newInstance(defaultUser);

    }
}
