package com.example.newsreader;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.example.newsreader.HeadlinesFragment.Holder;
import com.example.newsreader.HeadlinesFragment.NewsAdapter;
import com.example.newsreader.model.NewsArticle;

@RunWith(RobolectricTestRunner.class)
public class NewsAdapterTest {

	private NewsAdapter mNewsAdapter;
	private List<NewsArticle> mNewsArticleList;
	private NewsArticle mNewsArticle;
	private int count;
	private HeadlinesFragment mHeadlinesFragment;
	private View mView;
	private TextView mTextView;
	private View recycleView;
	private Holder mHolder;

	@Before
	public void setup() throws Exception {
		mNewsArticleList = new ArrayList<NewsArticle>();
		mHeadlinesFragment = new HeadlinesFragment();
		count = 0;
		while (count < 10) {

			mNewsArticle = new NewsArticle();
			mNewsArticle.setHeadline("Headline" + count);
			mNewsArticle.setSummary("Summary" + count);
			mNewsArticleList.add(mNewsArticle);
			count++;
		}
		// mActivity = (Activity)
		// Robolectric.buildActivity(Activity.class).get();
		mNewsAdapter = mHeadlinesFragment.new NewsAdapter(null,
				mNewsArticleList);
		mHolder = null;

	}

	@Test
	public void getCountTest() throws Exception {
		assertThat(mNewsAdapter.getCount(), equalTo(mNewsArticleList.size()));
	}

	@Test
	public void getViewTest() throws Exception {

		recycleView = View.inflate(new Activity(),
				android.R.layout.simple_list_item_activated_1, null);
		mHolder = mHeadlinesFragment.new Holder();
		mHolder.tvTitle = (TextView) recycleView
				.findViewById(android.R.id.text1);
		recycleView.setTag(mHolder);
		for (int position = 0; position < 10; position++) {
			mView = mNewsAdapter.getView(position, recycleView, null);
			mTextView = (TextView) mView.findViewById(android.R.id.text1);
			assertThat(mNewsArticleList.get(position).getHeadline(),
					equalTo(mTextView.getText()));
		}

	}
}
