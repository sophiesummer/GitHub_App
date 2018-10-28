package com.hiddleston.gitapplication;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FollowersAdapter extends BaseAdapter {

    List<FollowInfo> followersData;
    Context context;

    public FollowersAdapter(Context context, String follow_json) {
        this.context = context;
        followersData = FollowInfo.getFollowInfo(follow_json); // change Data Service
    }

    @Override
    public int getCount() {
        return followersData.size();
    }

    @Override
    public Object getItem(int position) {
        return followersData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // Modify!!!!
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.follow_info_item,
                    parent, false);
        }

        TextView username = (TextView) convertView.findViewById(R.id.username_text);
        ImageView user_img = (ImageView) convertView.findViewById(R.id.user_img);


        FollowInfo followInfo = followersData.get(position);
        username.setText(followInfo.username);
        Picasso.get().load(followInfo.img_url).into(user_img);

        return convertView;
    }
}
