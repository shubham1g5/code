package com.example.newsreader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class ArticleFragment extends Fragment {

	public static final String TITLE = "title";
	public static final String SUMMARY = "summary";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.article_view, container, false);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Bundle args = getArguments();
		if (args != null) {
			updateTextView(args.getString(SUMMARY));
		}
	}

	public void updateTextView(String summary) {
		// TODO Auto-generated method stub
		WebView article_view = (WebView) getActivity().findViewById(
				R.id.webview);
		if (article_view != null) {
			article_view.loadData(summary, "text/html", null);
		}
	}

}
