package com.example.countdowntimer;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class CountDownService extends Service {

	static final int MSG_START_TIMER = 1;
	static final int MSG_DEC_TIMER = 2;
	static final int MSG_STOP_TIMER = 3;
	private static final int HEARTBEAT = 10;
	protected static final int MSG_REGISTER_ACTIVITY = 100;

	static boolean quit;
	Messenger mclient;
	Handler mHandler = new Handler();
	
	//list of activities/clients
	List<Messenger> mListMessengers = null;

	class IncomingHandler extends Handler {

		@Override
		public void handleMessage(final Message msg) {
			switch (msg.what) {
			case MSG_REGISTER_ACTIVITY:
				//add activity in list 
				if (mListMessengers == null) {
					mListMessengers = new ArrayList<Messenger>();
				}
				mListMessengers.add(msg.replyTo);

				break;

			case MSG_START_TIMER:
				
				// start timer
				quit = false;
				Message beep_msg = this.obtainMessage(HEARTBEAT);
				this.sendMessageDelayed(beep_msg, 1000);
				break;

			case HEARTBEAT:
				// send beep signal to all listeners
				for(Messenger m : mListMessengers) {
					try {
						m.send(Message.obtain(null, MSG_DEC_TIMER));
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				this.removeMessages(HEARTBEAT);
				if(!quit){
					this.sendMessageDelayed(this.obtainMessage(HEARTBEAT), 1000);
				}
				break;

			case MSG_STOP_TIMER:
				// stop count down
				quit = true;
				break;
				
			default:
				super.handleMessage(msg);

			}

		}

	}

	Messenger mMessenger = new Messenger(new IncomingHandler());

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mMessenger.getBinder();
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	//	Message mMessage = Message.obtain(null, MSG_DEC_TIMER);

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		quit = true;
	}

}