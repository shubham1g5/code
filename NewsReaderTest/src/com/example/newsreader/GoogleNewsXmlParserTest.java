package com.example.newsreader;



import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.example.newsreader.GoogleNewsXmlParser.Item;

@RunWith(RobolectricTestRunner.class)
public class GoogleNewsXmlParserTest {
	
	private InputStream is;
	private File mFile;
	private List<Item> itemList;
	private int count;
	private int length;
	private GoogleNewsXmlParser mParser; 
	@Before
	public void setup() throws Exception{
		count = 1;
		mFile = new File("/Users/Sreekanth/googleNews.xml");
		is = new FileInputStream(mFile);
		mParser = new GoogleNewsXmlParser();
		itemList = new ArrayList<GoogleNewsXmlParser.Item>();
		itemList.add(new Item("title","summary","link"));
		while(count<10){
			itemList.add(new Item("title"+count,"summary"+count,"link"+count));
			count++;
		}
		
		
	}
	
	@Test
	public void paserShouldReturnFieldsCorrectly() throws Exception{
		mParser.Parse(is);
	//	assertThat(itemList.size(),  equalTo(length));
		//assertThat(itemList, equalTo(GoogleNewsXmlParser.Parse(is)));
	}
}
