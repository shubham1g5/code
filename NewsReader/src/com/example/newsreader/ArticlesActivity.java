package com.example.newsreader;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

public class ArticlesActivity extends SherlockFragmentActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_articles);
		
		setUpActionBar();

		if (findViewById(R.id.article_frag) != null){
			finish();
		}
		else {
			ArticleFragment mArticleFragment = new ArticleFragment();
			mArticleFragment.setArguments(getIntent().getExtras());
			FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
			mTransaction.replace(R.id.fragment_container, mArticleFragment);
			//mTransaction.addToBackStack(null);
			mTransaction.commit();
		}
		
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setUpActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar mActionBar = getSupportActionBar();
			mActionBar.setHomeButtonEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:
			Intent intent = new Intent(this,MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
