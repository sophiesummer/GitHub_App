package com.hiddleston.gitapplication;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.util.List;

/**
 * front page activity of whole app
 */
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

    public String defaultUserName = "sophiesummer";
    private User defaultUser = new User();

    private EditText editText;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                defaultUserName = extras.getString("defaultUsername");
            }
        } else {
            defaultUserName = (String)savedInstanceState.getSerializable("defaultUsername");
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();

        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                OkHttpClient client = new OkHttpClient();

                String url = "http://api.github.com/users/" + defaultUserName;
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
                    mDatabase.child("users").child(defaultUser.userName).child("userInfo").setValue(defaultUser);
                    setCount();
                    setFragment();

                    System.out.print(o.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();


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
                // call REST API for repo info
                AsyncTask asyncTask = new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] objects) {
                        OkHttpClient client = new OkHttpClient();

                        String url = defaultUser.repoUrl;
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
                            repositoryFragment = new RepositoryFragment().newInstance(o.toString());
                            List<Repository> repositoriesInfo = Repository.getRepository(o.toString());
                            for (Repository r : repositoriesInfo) {
                                String repoTag = r.repoName.replaceAll("\\.|#|\\[\\]|\\$","");
                                if (repoTag != null) {
                                    mDatabase.child("users").child(defaultUser.userName).child("repositoriesInfo").child(repoTag).setValue(r);
                                } else {
                                    mDatabase.child("users").child(defaultUser.userName).child("repositoriesInfo").child("  ").setValue(r);
                                }
                            }
                            getSupportFragmentManager().beginTransaction().
                                    replace(R.id.content_layout, repositoryFragment).commit();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }.execute();
            }
        });

        followersButton = findViewById(R.id.followers_button);
        followersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncTask asyncTask = new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] objects) {
                        OkHttpClient client = new OkHttpClient();

                        String url = defaultUser.followers_url;
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
                            followersFragment = new FollowersFragment().newInstance(o.toString());
                            List<FollowInfo> followingInfo = FollowInfo.getFollowInfo(o.toString());
                            for (FollowInfo f : followingInfo) {
                                mDatabase.child("users").child(defaultUser.userName).child("followersInfo").child(f.username).setValue(f);
                            }
                            getSupportFragmentManager().beginTransaction().
                                    replace(R.id.content_layout, followersFragment).commit();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }.execute();

            }
        });


        followingButton = findViewById(R.id.following_button);
        followingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask asyncTask = new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] objects) {
                        OkHttpClient client = new OkHttpClient();

                        String url = defaultUser.following_url;
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
                            followersFragment = new FollowersFragment().newInstance(o.toString());
                            List<FollowInfo> followingInfo = FollowInfo.getFollowInfo(o.toString());
                            for (FollowInfo f : followingInfo) {
                                mDatabase.child("users").child(defaultUser.userName).child("followingInfo").child(f.username).setValue(f);
                            }
                            getSupportFragmentManager().beginTransaction().
                                    replace(R.id.content_layout, followersFragment).commit();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.execute();

            }
        });

        setCount();
        editText = findViewById(R.id.search_text);

        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // call REST API for user info
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
                            mDatabase.child("users").child(defaultUser.userName).child("userInfo").setValue(defaultUser);
                            setCount();
                            setFragment();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.execute();
            }
        });
    }

    /**
     * create RepositoryDetailFragment to display repo web page.
     * @param position the index of list view
     * @param url the url of the repository web page.
     */
    @Override
    public void onItemSelected(int position, String url) {
        repositoryDetailFragment = new RepositoryDetailFragment().newInstance(url);  //put in a url
        getSupportFragmentManager().beginTransaction().
                replace(R.id.content_layout, repositoryDetailFragment).commit();
    }

    /*
     * set count number of repo, followers, following on button.
     */
    public void setCount() {
        String buttonTextRepo = "Repository:\n" + defaultUser.repo_count;
        repositoryButton.setText(buttonTextRepo);
        String buttonTextFollowers = "Followers:\n" + defaultUser.followers;
        followersButton.setText(buttonTextFollowers);
        String buttonTextFollowing = "Following:\n" + defaultUser.following;
        followingButton.setText(buttonTextFollowing);
    }

    /*
     * update profilefragment with arguments.
     */
    public void setFragment() {
        profileFragment = new ProfileFragment().newInstance(defaultUser);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.content_layout, profileFragment).commit();
    }
}
