package com.hiddleston.gitapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 * provide detailed contents of each repository
 */
public class RepositoryDetailFragment extends Fragment {

    public RepositoryDetailFragment() {
        // Required empty public constructor
    }

    /**
     * a constructor with arguments.
     * @return a new ReportDetailFragment
     */
    public static RepositoryDetailFragment newInstance(String url) {
        Bundle args = new Bundle(); // get in url
        args.putString("repo_detail_url", url);
        RepositoryDetailFragment fragment = new RepositoryDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_report_detail, container, false);
        if (getArguments() != null) {
            WebView webView = (WebView) view.findViewById(R.id.web_view);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            String url = getArguments().getString("repo_detail_url");
            webView.loadUrl(url);
        }
        return view;
    }

}
