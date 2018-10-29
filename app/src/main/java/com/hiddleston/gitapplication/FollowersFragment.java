package com.hiddleston.gitapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FollowersFragment extends Fragment {

    private ListView follower_list_view;
    private String json_data;
    public List<FollowInfo> followInfoList;

    public FollowersFragment() {
        // Required empty public constructor
    }

    public static FollowersFragment newInstance(String json_data) {

        Bundle args = new Bundle();
        args.putString("json_data", json_data);
        FollowersFragment fragment = new FollowersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_followers, container, false);
        if (getArguments() != null) {
            json_data = getArguments().getString("json_data");
            follower_list_view = (ListView) view.findViewById(R.id.followers_list);
            final FollowersAdapter fa = new FollowersAdapter(getActivity(), json_data);
            followInfoList = fa.followersData;
            follower_list_view.setAdapter(fa);

            follower_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // set up new activity;
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    intent.putExtra("defaultUsername", fa.followersData.get(position).username);
                    startActivity(intent);

                }
            });
        }

        return view;
    }

}
