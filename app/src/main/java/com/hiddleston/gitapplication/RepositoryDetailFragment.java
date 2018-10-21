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
 */
public class RepositoryDetailFragment extends Fragment {
    public String webUrl = "https://github.com/sophiesummer/donation-analytics";

    public RepositoryDetailFragment() {
        // Required empty public constructor
    }

    /**
     * a constructor with arguments.
     * @return a new ReportDetailFragment
     */
    public static RepositoryDetailFragment newInstance() {
        Bundle args = new Bundle(); // get in url

        RepositoryDetailFragment fragment = new RepositoryDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_report_detail, container, false);
        WebView webView = (WebView)view.findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(webUrl);
        return view;
    }

}
