package com.hiddleston.gitapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * present user info on screen
 */
public class ProfileFragment extends Fragment {

    public static User user;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * constructor with parameter
     * @param user a user object constructed from API information
     * @return a new fragment shows the user info
     */
    public static ProfileFragment newInstance(User user) {

        Bundle args = new Bundle();
        args.putSerializable("user", user);
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        if (getArguments() != null) {
            Log.i("user","user is exist");
            user = (User) getArguments().getSerializable("user");
            TextView name = (TextView)view.findViewById(R.id.name_text);
            if (!user.realName.equals("null")) {
                name.setText(user.realName);
            } else {
                name.setVisibility(View.GONE);
            }
            TextView username = (TextView)view.findViewById(R.id.username_text);
            username.setText(user.userName);

            TextView website = (TextView)view.findViewById(R.id.website_text);
            if (!user.website.equals("null")) {
                website.setText(user.website);
            } else {
                website.setVisibility(View.GONE);
            }
            TextView bio = (TextView)view.findViewById(R.id.bio_text);
            if (!user.bio.equals("null")) {
                bio.setText(user.bio);
            } else {
                bio.setVisibility(View.GONE);
            }
            TextView email = (TextView)view.findViewById(R.id.email_text);
            if (!user.email.equals("null")) {
                Log.d("email", user.email);
                email.setText(user.email);
            } else {
                email.setVisibility(View.GONE);
            }

            TextView createDate = (TextView)view.findViewById(R.id.create_date);
            String create = "Create Date : " + user.create_date;
            createDate.setText(create);

            ImageView imageView = (ImageView)view.findViewById(R.id.user_avatar);
            Picasso.get().load(user.avatar_url).into(imageView);

        }
        return view;
    }

}
