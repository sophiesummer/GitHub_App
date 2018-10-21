package com.hiddleston.gitapplication;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RepositoryFragment extends Fragment {

    OnItemSelectListener repoCallback;

    private ListView repoListView;

    public RepositoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            repoCallback = (OnItemSelectListener) context;
        } catch (ClassCastException e) {
            //do something
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_repository, container, false);
        repoListView = (ListView) view.findViewById(R.id.repo_list);
        repoListView.setAdapter(new RepositoryAdapter(getActivity()));

        repoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                repoCallback.onItemSelected(i);
            }
        });
        return view;
    }



    public interface OnItemSelectListener {
        public void onItemSelected(int position);
    }

}
