package com.example.testproject;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TypedArray a = obtainStyledAttributes(R.styleable.list_left_padding);
		float padd = a.getResources().getDimension(R.dimen.list_left_padding);
		Toast.makeText(getApplicationContext(), "Padd " + padd,
				Toast.LENGTH_LONG).show();
	}
}
