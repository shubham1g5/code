package com.example.newsreader;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;

@RunWith(RobolectricTestRunner.class)
public class ActivityFragmentTest {

	private ArticleFragment mArticleFragment;
	private final String html_summary = "<h1>This is Summary</h1>";
	private Bundle args;
	private android.support.v4.app.FragmentManager mFragmentManager;

	public static class TestActivity extends SherlockFragmentActivity {

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.news_articles_test);
		}
	}

	@Before
	public void setup() throws Exception {

		mArticleFragment = new ArticleFragment();
		args = new Bundle();
		args.putString(ArticleFragment.SUMMARY, html_summary);
		mArticleFragment.setArguments(args);

		FragmentActivity activity = Robolectric
				.buildActivity(TestActivity.class).create().get();

		// TestActivity activity = new TestActivity();
		// activity.onCreate(null);

		mFragmentManager = activity.getSupportFragmentManager();
		FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
		mTransaction.replace(R.id.fragment_container, mArticleFragment)
				.commit();

	}

	@Test
	public void webViewShouldDisplaySummary() throws Exception {

		assertThat(
				html_summary,
				equalTo(mArticleFragment.getArguments().get(
						ArticleFragment.SUMMARY)));
	}

}
