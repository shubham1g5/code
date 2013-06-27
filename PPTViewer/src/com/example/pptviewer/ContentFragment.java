package com.example.pptviewer;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class ContentFragment extends Fragment {

	private static final String ARG_PAGE = "page";
	private int mPageNumber;
	private String[] FileNames;
	private String mFileName;

	public static ContentFragment newInstance(int PageNumber) {
		ContentFragment mContentFragment = new ContentFragment();

		Bundle mBundle = new Bundle();
		mBundle.putInt(ARG_PAGE, PageNumber);
		mContentFragment.setArguments(mBundle);
		return mContentFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPageNumber = getArguments().getInt(ARG_PAGE);
		AssetManager mAssetManager = getActivity().getAssets();
		try {
			FileNames = mAssetManager.list("html");
		} catch (IOException e) {
			e.printStackTrace();
		}
		mFileName = FileNames[mPageNumber];
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View RootView = inflater.inflate(R.layout.content_fragment, container,
				false);

		if (mPageNumber % 4 == 0) {
			RootView.setBackgroundColor(getResources().getColor(
					android.R.color.holo_blue_bright));
		} else if (mPageNumber % 4 == 1) {
			RootView.setBackgroundColor(getResources().getColor(
					android.R.color.holo_green_light));
		} else if (mPageNumber % 4 == 2) {
			RootView.setBackgroundColor(getResources().getColor(
					android.R.color.holo_orange_light));
		} else {
			RootView.setBackgroundColor(getResources().getColor(
					android.R.color.holo_purple));
		}

		WebView mWebView = (WebView) RootView.findViewById(R.id.webview);
		String url = "File:///android_asset/html/" + mFileName; 
		mWebView.loadUrl(url);
		return RootView;
	}

}
