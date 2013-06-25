package com.example.newsreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends FragmentActivity implements
		OnHeadlineSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_articles);

		// add the first headlineFragment if in portrait layout
		if (getSupportFragmentManager().findFragmentById(
				R.id.fragment_container) == null) {
			HeadlinesFragment firstFragment = new HeadlinesFragment();
			firstFragment.setArguments(getIntent().getExtras());
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.fragment_container, firstFragment).commit();
		}
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
			//start Activity
			Intent DisplayArticle = new Intent(MainActivity.this,ArticlesActivity.class);
			DisplayArticle.putExtra("TITLE", title);
			DisplayArticle.putExtra(ArticleFragment.SUMMARY, summary);
			startActivity(DisplayArticle);
		}
		

	}

}
