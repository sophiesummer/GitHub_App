package com.hiddleston.gitapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class RepositoryAdapter extends BaseAdapter {
    List<Repository> repoData;
    Context context;

    public RepositoryAdapter(Context context) {
        this.context = context;
        repoData = DataService.getRepoData(); // change Data Service
    }

    @Override
    public int getCount() {
        return repoData.size();
    }

    @Override
    public Object getItem(int position) {
        return repoData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.repo_item,
                    parent, false);
        }

        TextView repoName = (TextView) convertView.findViewById(R.id.repo_name);
        TextView repoOwner = (TextView) convertView.findViewById(R.id.repo_owner);
        TextView repoDescription = (TextView) convertView.findViewById(R.id.repo_description);

        Repository r = repoData.get(position);
        repoName.setText(r.repoName);
        repoOwner.setText(r.repoOwner);
        repoDescription.setText(r.repoDescription);

        return convertView;
    }
}
