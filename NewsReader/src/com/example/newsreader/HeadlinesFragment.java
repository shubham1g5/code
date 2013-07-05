package com.example.newsreader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.example.newsreader.GoogleNewsXmlParser.Item;
import com.example.newsreader.model.NewsArticle;

public class HeadlinesFragment extends SherlockListFragment {

	private static final String[] URL = {
			"https://news.google.co.in/news/feeds?pz=1&cf=all&ned=in&hl=en&output=rss",
			"https://news.google.co.in/news/feeds?pz=1&cf=all&ned=in&hl=en&topic=b&output=rss",
			"http://news.google.co.in/news?pz=1&cf=all&ned=in&hl=en&topic=s&output=rss",
			"https://news.google.co.in/news/feeds?pz=1&cf=all&ned=in&hl=en&topic=tc&output=rss",
			"https://news.google.com/news/feeds?pz=1&cf=all&ned=in&hl=en&topic=m&output=rss" };
	protected static final String ARG_POSITION = "drawer_item_position";
	public static List<NewsArticle> mNewsArticles = new ArrayList<NewsArticle>();
	OnHeadlineSelectedListener mCallback;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// verify that activity has implemented the callback interface
		try {
			mCallback = (OnHeadlineSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ "must implement OnHeadlineSelectedListener");
		}
	}

	public class RetreiveFeedTask extends AsyncTask<String, Void, List<Item>> {

		@Override
		protected List<Item> doInBackground(String... params) {
			List<Item> mItems = null;
			String url = params[0];
			GoogleNewsXmlParser mParser = new GoogleNewsXmlParser();
			try {
				InputStream content = downloadUrl(url);

				try {
					mItems = mParser.Parse(content);
				} finally {
					if (content != null)
						content.close();
				}
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return mItems;
		}

		@Override
		protected void onPostExecute(List<Item> result) {
			// mNewsArticles = new ArrayList<NewsArticle>();
			if (mNewsArticles == null) {
				mNewsArticles = new ArrayList<NewsArticle>();
			}
			if (getFragmentManager() != null) {
				super.onPostExecute(result);
				for (Item iItem : result) {
					NewsArticle article = new NewsArticle();
					article.setHeadline(iItem.Title);
					article.setSummary(iItem.Summary);
					mNewsArticles.add(article);
				}
				if (getListView().getAdapter() == null) {
					NewsAdapter mNewsAdapter = new NewsAdapter(getActivity(),
							mNewsArticles);
					setListAdapter(mNewsAdapter);
				} else {
					((BaseAdapter) getListView().getAdapter())
							.notifyDataSetChanged();
				}
			}
		}
	}

	public InputStream downloadUrl(String url) throws ClientProtocolException,
			IOException {

		InputStream content = null;
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response;
		response = client.execute(httpGet);
		StatusLine statusline = response.getStatusLine();
		if (statusline.getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			content = entity.getContent();
		} else {
			Log.e("Mainactivity String Conversion", "Error in network");
		}
		return content;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		// setListAdapter(mNewsAdapter);
		// if (mNewsArticles == null) {
		new RetreiveFeedTask()
				.execute(URL[getArguments().getInt(ARG_POSITION)]);
		// }
	}

	@Override
	public void onStart() {
		// TODO find reason behind why findViewById(R,id.article_frag) coming
		// null
		super.onStart();
		int position = getListView().getCheckedItemPosition();
		// if(getView().findViewById(R.id.article_frag) !=null) {
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
				&& position >= 0) {
			NewsArticle selectedNewsArticle = mNewsArticles.get(position);
			mCallback.OnArticleSelected(selectedNewsArticle.getHeadline(),
					selectedNewsArticle.getSummary());
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mNewsArticles = null;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Holder holder = (Holder) v.getTag();
		getListView().setItemChecked(position, true);
		mCallback.OnArticleSelected(holder.title, holder.summary);

	}

	public class NewsAdapter extends BaseAdapter {

		private List<NewsArticle> data;

		public NewsAdapter(Context context, List<NewsArticle> data) {
			this.data = data;
		}

		@Override
		public int getCount() {
			return data != null ? data.size() : 0;
		}

		@Override
		public Object getItem(int position) {
			return position < data.size() ? data.get(position) : null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder = null;
			if (convertView == null) {
				convertView = getLayoutInflater(null).inflate(
						android.R.layout.simple_list_item_activated_1, null);
				holder = new Holder();
				holder.tvTitle = (TextView) convertView
						.findViewById(android.R.id.text1);
			} else {
				holder = (Holder) convertView.getTag();
			}
			NewsArticle article = data.get(position);
			holder.tvTitle.setText(article.getHeadline());
			holder.title = article.getHeadline();
			holder.summary = article.getSummary();
			convertView.setTag(holder);
			return convertView;
		}

	}

	public class Holder {
		TextView tvTitle;
		String title, summary;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.main, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			mNewsArticles.clear();
			Toast.makeText(getActivity(), "Refreshing..", Toast.LENGTH_LONG)
					.show();
			new RetreiveFeedTask().execute(URL[getArguments().getInt(
					ARG_POSITION)]);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
