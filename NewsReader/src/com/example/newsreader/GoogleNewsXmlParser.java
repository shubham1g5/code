package com.example.newsreader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class GoogleNewsXmlParser {

	private static final String ns = null;

	public List<Item> Parse(InputStream is) throws IOException,
			XmlPullParserException {
		// instantaniates XmlPullParser and set the inputStream
		XmlPullParser parser = Xml.newPullParser();

		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		parser.setInput(is, null);
		parser.nextTag();
		return readFeed(parser);
	}

	private List<Item> readFeed(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		// TODO Auto-generated method stub
		// process the feed.. look for item tag
		List<Item> items = new ArrayList<Item>();
		parser.next();
		parser.require(XmlPullParser.START_TAG, ns, "channel");
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() == XmlPullParser.START_TAG) {
				String name = parser.getName();
				if (name.equals("item")) {
					items.add(readItem(parser));
				} else {
					skip(parser);
				}
			}
		}
		return items;
	}

	private Item readItem(XmlPullParser parser) throws XmlPullParserException,
			IOException {
		// TODO Auto-generated method stub
		// extract data for the item tag and its nested tags title and link
		parser.require(XmlPullParser.START_TAG, ns, "item");
		String title = null;
		String link = null;
		String summary = null;
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() == XmlPullParser.START_TAG) {
				String name = parser.getName();
				if (name.equals("title")) {
					// readtitle
					title = readTitle(parser);
				} else if (name.equals("link")) {
					// readlink
					link = readLink(parser);
				} else if (name.equals("description")) {
					summary = readsummary(parser);
				} else {
					skip(parser);
				}
			}
		}
		Item mItem = new Item(title, link,summary);
		return mItem;
	}

	private String readsummary(XmlPullParser parser) throws XmlPullParserException, IOException{
		String summary = "";
		parser.require(XmlPullParser.START_TAG,ns,"description");
		summary = readText(parser);
		parser.require(XmlPullParser.END_TAG,ns,"description");
		return summary;
	}

	private String readText(XmlPullParser parser) throws XmlPullParserException, IOException{
		// TODO 
		String result = "";
		if(parser.next() == XmlPullParser.TEXT){
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}

	private String readLink(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		// TODO Auto-generated method stub
		String link = "";
		parser.require(XmlPullParser.START_TAG, ns, "link");
		// read link
		if (parser.next() == XmlPullParser.TEXT) {
			link = parser.getText();
			parser.nextTag();
		}
		parser.require(XmlPullParser.END_TAG, ns, "link");
		return link;
	}

	private String readTitle(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		// TODO Auto-generated method stub
		String result = "";
		parser.require(XmlPullParser.START_TAG, ns, "title");
		// readtext
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			parser.nextTag();
		}
		parser.require(XmlPullParser.END_TAG, ns, "title");
		return result;
	}

	private void skip(XmlPullParser parser) throws XmlPullParserException,
			IOException {
		// TODO Auto-generated method stub
		if (parser.getEventType() == XmlPullParser.START_TAG) {
			int depth = 1;
			while (depth != 0) {
				switch (parser.next()) {

				case XmlPullParser.START_TAG:
					depth++;
					break;
				case XmlPullParser.END_TAG:
					depth--;
					break;
				}
			}
		}
	}

	public class Item {
		public String Title;
		public String Link;
		public String Summary;

		private Item(String title, String link, String summary) {
			this.Title = title;
			this.Link = link;
			this.Summary = summary;
		}
	}

}
