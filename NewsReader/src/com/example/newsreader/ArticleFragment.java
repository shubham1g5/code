package com.example.newsreader;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.widget.ShareActionProvider;

public class ArticleFragment extends SherlockFragment {

	public static final String TITLE = "title";
	public static final String SUMMARY = "summary";
	private ShareActionProvider mActionProvider;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.article_view, container, false);
	}

	@Override
	public void onStart() {

		super.onStart();
		Bundle args = getArguments();
		if (args != null) {
			updateTextView(args.getString(SUMMARY));
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	public void updateTextView(String summary) {
	
		WebView article_view = (WebView) getActivity().findViewById(
				R.id.webview);
		if (article_view != null) {
			article_view.loadData(summary, "text/html", null);
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_article_fragment, menu);
		mActionProvider =  (ShareActionProvider) menu.findItem(R.id.menu_share)
				.getActionProvider();
		mActionProvider.setShareIntent(getDefaultShareIntent());
	}

	private Intent getDefaultShareIntent() {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_TEXT, getArguments().getString(TITLE));
		intent.setType("text/plain");
		return intent;
	}

}
