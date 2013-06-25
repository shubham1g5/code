package com.example.pptviewer;

import java.io.IOException;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity {

	public static final String FILE_NAMES = "files";

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		AssetManager mAssestManager = getAssets();
		String[] files = null;
		try {
				files = mAssestManager.list("");
		} catch (IOException e) {
			e.printStackTrace();
		}
		TitlesFragment mTitlesFragment = new TitlesFragment();
		Bundle args = new Bundle();
		args.putStringArray(FILE_NAMES, files);
		mTitlesFragment.setArguments(args);
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mTitlesFragment).commit();
	}


}
