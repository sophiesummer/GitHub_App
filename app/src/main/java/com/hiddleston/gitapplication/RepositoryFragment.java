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
 * provide list elements contents of repository list
 */
public class RepositoryFragment extends Fragment {

    OnItemSelectListener repoCallback;

    private ListView repoListView;

    String repo_json;

    public RepositoryFragment() {
        // Required empty public constructor
    }

    /**
     * constructor with json string parameter
     * @param repo_json json string of repository object JSONArray
     * @return a new RepositoryFragment lists repo elements.
     */
    public static RepositoryFragment newInstance(String repo_json) {

        Bundle args = new Bundle();
        args.putString("repo_json", repo_json);
        RepositoryFragment fragment = new RepositoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            repoCallback = (OnItemSelectListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_repository, container, false);
        if (getArguments() != null) {
            repo_json = getArguments().getString("repo_json");
            repoListView = (ListView) view.findViewById(R.id.repo_list);
            final RepositoryAdapter ra = new RepositoryAdapter(getActivity(), repo_json);
            repoListView.setAdapter(ra);

            repoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    repoCallback.onItemSelected(i, ra.repoData.get(i).url);
                }
            });
        }
        return view;
    }

    /**
     * invoke main activity to call repository detail fragment.
     */
    public interface OnItemSelectListener {
        public void onItemSelected(int position, String url);
    }

}
