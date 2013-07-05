package main.com.example.helloworld;

import android.app.Activity;
import android.os.Bundle;

public class NewActivity extends Activity{
	
	String name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		name = getIntent().getExtras().getString("name");
	}
}
