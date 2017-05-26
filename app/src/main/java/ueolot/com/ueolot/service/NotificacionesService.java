package ueolot.com.ueolot.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import ueolot.com.ueolot.Connexio.Last_Notificacion_Task;

public class NotificacionesService  extends Service {

	private Timer timer;
	
	public void onCreate(){
		super.onCreate();
		Log.d("UEOLOT", "Service :: onCreate");
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.d("UEOLOT", "Service :: onBind");
		return null;
	}
	
	public int onStartCommand(Intent intent, int flags, int startId){
		if(timer == null) {
			Log.d("UEOLOT", "Service :: onStartCommand");
			startNotificacionesService();
		}
		return START_STICKY;
	}
	
	private void startNotificacionesService(){
		final Handler handler = new Handler();
	    timer = new Timer();
	    TimerTask doAsynchronousTask = new TimerTask() {
	        @Override
	        public void run() {
	            handler.post(new Runnable() {
	                public void run() {     
                        new Last_Notificacion_Task(NotificacionesService.this).execute();
	                }
	            });
	        }
	    };
	    timer.schedule(doAsynchronousTask, 0, 15000);
	}

	public void onDestroy(){
		Log.d("UEOLOT", "Service :: onDestroy");
		if(timer != null)
			timer.cancel();
	}

}
