package com.example.pptviewer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class ViewPagerActivity extends FragmentActivity {

	public static final String ARG_FILE_NAMES = "fileNames";
	public static final String ARG_PAGE_NO = "pageToOpen";

	private int numPages;
	private String[] fileNames;

	private int pageToOpen;
	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_pager_activity);

		Bundle args = getIntent().getExtras();
		fileNames = args.getStringArray(ARG_FILE_NAMES);
		pageToOpen = args.getInt(ARG_PAGE_NO);
		numPages = fileNames.length;

		mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mPagerAdapter);
		mPager.setCurrentItem(pageToOpen);
		mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);
				invalidateOptionsMenu();
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.view_pager_activity, menu);
		menu.findItem(R.id.action_previous).setEnabled(
				mPager.getCurrentItem() > 0);
		MenuItem item = menu
				.add(Menu.NONE,
						R.id.action_next,
						Menu.NONE,
						(mPager.getCurrentItem() == mPagerAdapter.getCount() - 1) ? "FINISH"
								: "NEXT");
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM
				| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.action_next:
			mPager.setCurrentItem(mPager.getCurrentItem() + 1);
			break;

		case R.id.action_previous:
			mPager.setCurrentItem(mPager.getCurrentItem() - 1);
			break;

		}
		return super.onOptionsItemSelected(item);

	}

	public class ViewPagerAdapter extends FragmentStatePagerAdapter {
		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int PageNumber) {
			return ContentFragment.newInstance(PageNumber);
		}

		@Override
		public int getCount() {
			return numPages;
		}
	}
}
