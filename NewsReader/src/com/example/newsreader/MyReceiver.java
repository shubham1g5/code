package com.example.newsreader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		int state = intent.getExtras().getInt(WifiManager.EXTRA_WIFI_STATE);
		if(state==WifiManager.WIFI_STATE_DISABLED){
			Toast.makeText(context, "PLEASE ENABLE WIFI IN ORDER TO GET LATEST NEWS CONTENT", Toast.LENGTH_LONG).show();
		}
		
	}

}
