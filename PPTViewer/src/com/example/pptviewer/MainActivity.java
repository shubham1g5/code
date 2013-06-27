package com.example.pptviewer;

import java.io.IOException;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity implements
		OnTitleSelectedListener {

	public static final String FILE_NAMES = "files";
	String[] FileNames = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		AssetManager mAssestManager = getAssets();

		try {
			FileNames = mAssestManager.list("html");

		} catch (IOException e) {
			e.printStackTrace();
		}
		TitlesFragment mTitlesFragment = new TitlesFragment();
		Bundle args = new Bundle();
		args.putStringArray(FILE_NAMES, FileNames);
		mTitlesFragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.fragment_container, mTitlesFragment).commit();
	}

	@Override
	public void OnTitleSelected(int position) {
		Intent StartViewpagerActivity = new Intent(MainActivity.this,
				ViewPagerActivity.class);
		StartViewpagerActivity.putExtra(ViewPagerActivity.ARG_FILE_NAMES,
				FileNames);
		StartViewpagerActivity
				.putExtra(ViewPagerActivity.ARG_PAGE_NO, position);
		startActivity(StartViewpagerActivity);
	}
}
