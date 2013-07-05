package com.example.newsreader;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends SherlockFragmentActivity implements
		OnHeadlineSelectedListener {

	private MyReceiver mReceiver;
	private String[] mCategories;
	private ListView mDrawerList;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_articles);
		// set up drawer
		
		mTitle = mDrawerTitle = getTitle();
		mCategories = getResources().getStringArray(R.array.category_names);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mCategories));
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				getSupportActionBar().setTitle(mTitle);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getSupportActionBar().setTitle(mDrawerTitle);
			}

		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);
		if (savedInstanceState == null) {
			selectItem(0);
		}

	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {

		// if (getSupportFragmentManager().findFragmentById(
		// R.id.fragment_container) == null) {
		HeadlinesFragment firstFragment = new HeadlinesFragment();
		Bundle args = new Bundle();
		args.putInt(HeadlinesFragment.ARG_POSITION, position);
		firstFragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.fragment_container, firstFragment).commit();
		// }

		mDrawerList.setItemChecked(position, true);
		mTitle = mCategories[position];
		getSupportActionBar().setTitle(mTitle);
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mReceiver == null) {
			mReceiver = new MyReceiver();
		}
		IntentFilter mIntentFilter = new IntentFilter(
				"android.net.wifi.WIFI_STATE_CHANGED");
		// register receiver
		this.registerReceiver(mReceiver, mIntentFilter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		this.unregisterReceiver(mReceiver);
	}

	@Override
	public void OnArticleSelected(String title, String summary) {

		if (findViewById(R.id.article_frag) != null) {
			// dual mode
			ArticleFragment newFragment = new ArticleFragment();
			Bundle args = new Bundle();
			args.putString(ArticleFragment.TITLE, title);
			args.putString(ArticleFragment.SUMMARY, summary);
			newFragment.setArguments(args);
			FragmentTransaction mTransaction = getSupportFragmentManager()
					.beginTransaction();
			mTransaction.replace(R.id.article_frag, newFragment);
			mTransaction.addToBackStack(null);
			mTransaction.commit();

		} else {
			// start Activity
			Intent DisplayArticle = new Intent(MainActivity.this,
					ArticlesActivity.class);
			DisplayArticle.putExtra("TITLE", title);
			DisplayArticle.putExtra(ArticleFragment.SUMMARY, summary);
			startActivity(DisplayArticle);
		}

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				mDrawerLayout.openDrawer(mDrawerList);
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
