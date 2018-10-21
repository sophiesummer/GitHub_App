package com.hiddleston.gitapplication;

import android.support.v4.app.Fragment;;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

    private User defaultUser;


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



    }

    @Override
    public void onItemSelected(int position) {
        repositoryDetailFragment = new RepositoryDetailFragment();  //put in a url
        Log.d(TAG, "reach detail");
        getSupportFragmentManager().beginTransaction().
                replace(R.id.content_layout, repositoryDetailFragment).commit();
    }
}
