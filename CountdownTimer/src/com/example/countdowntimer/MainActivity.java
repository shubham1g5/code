
package com.example.countdowntimer;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements View.OnClickListener{

	EditText NoOfSeconds;
	Button start;
	Button stop;
	Button reset;
	int secs;
	
	Messenger mService = null;
	class IncomingHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
			
			case CountDownService.MSG_DEC_TIMER:
				if(!CountDownService.quit){
					Log.e("CDTIMER", "msg received dec timer");
					secs--;
					NoOfSeconds.setText(String.valueOf(secs));
				}
				break;
			default:
				super.handleMessage(msg);	
			}
		}
	}
	
	Messenger mMessenger = new Messenger(new IncomingHandler());
	
	private ServiceConnection mServiceConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			mService = null;
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			mService = new Messenger(service);
			//Toast.makeText(getApplicationContext(), "Binded", Toast.LENGTH_LONG).show();
			Message msg = Message.obtain(null, CountDownService.MSG_REGISTER_ACTIVITY);
			msg.replyTo = mMessenger;
			try {
				mService.send(msg);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	};
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setVars();
        
		//bindService
			
			Intent i = new Intent(MainActivity.this, CountDownService.class);
			bindService(i, mServiceConnection, Context.BIND_AUTO_CREATE);
		
		//setting onclicklisteners
			start.setOnClickListener(this);
			stop.setOnClickListener(this);
		
       
    }

    
    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unbindService(mServiceConnection);
	}


	private void setVars() {
		// TODO Auto-generated method stub
    	
    	NoOfSeconds = (EditText) findViewById(R.id.InputTime);
    	start = (Button) findViewById(R.id.StartButton);
    	stop = (Button) findViewById(R.id.StopButton);
    	
		
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		
		case R.id.StartButton:
			
			secs = new Integer(NoOfSeconds.getText().toString()).intValue();
			Message msg = Message.obtain(null, CountDownService.MSG_START_TIMER);
			try {
				mService.send(msg);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		
		case R.id.StopButton:
			
			CountDownService.quit = true;
			if(mService != null){
				Message mMsg = Message.obtain(null, CountDownService.MSG_STOP_TIMER);
				mMsg.replyTo = mMessenger;
				try {
					mService.send(mMsg);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
			break;
		}
		
	}
    
}
