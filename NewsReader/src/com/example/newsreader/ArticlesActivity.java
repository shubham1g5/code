package com.example.newsreader;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class ArticlesActivity extends FragmentActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_articles);
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
	

}
