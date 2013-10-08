package com.colingleeson.androidtemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ServiceTest extends Service {
	 	private int counter = 0;
	    private URL[] urls;
	    private DoBackgroundTask myBackgroundTask = new DoBackgroundTask();

	    static final int UPDATE_INTERVAL = 1000;
	    private Timer timer = new Timer();

	    private final IBinder binder = new MyBinder();

	    public class MyBinder extends Binder {
	    	ServiceTest getService() {
	            return ServiceTest.this;
	        }
	    }

	    @Override
	    public IBinder onBind(Intent arg0) {
	        //return null;
	        return binder;
	    }

	    @Override
	    public int onStartCommand(Intent intent, int flags, int startId) {
	        // We want this service to continue running until it is explicitly
	        // stopped, so return sticky.
	        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
	        
	        // Timer
	        doSomethingRepeatedly();
	        
	        try {
	            urls = new URL[] {
	                    new URL("http://www.amazon.com/somefiles.pdf"),
	                    new URL("http://www.wrox.com/somefiles.pdf"),
	                    new URL("http://www.google.com/somefiles.pdf"),
	                    new URL("http://www.learn2develop.net/somefiles.pdf")};
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        }
	        
	        myBackgroundTask.execute(urls);
	        
	        return START_STICKY;
	    }
		
		/*
		@Override
		public int onStartCommand(Intent intent, int flags, int startId) {
			// We want this service to continue running until it is explicitly
			// stopped, so return sticky.
			
			Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
			
			/*
	        doSomethingRepeatedly();
			
	        try {
	            new DoBackgroundTask().execute(
	                    new URL("http://www.amazon.com/somefiles.pdf"),
	                    new URL("http://www.wrox.com/somefiles.pdf"),
	                    new URL("http://www.google.com/somefiles.pdf"),
	                    new URL("http://www.learn2develop.net/somefiles.pdf"));

	        } catch (MalformedURLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }		
			*/
		
	        /*	
	        // Get data from intent
	        Object[] objUrls = (Object[]) intent.getExtras().get("URLs");
	        URL[] urls = new URL[objUrls.length];
	        for (int i=0; i<objUrls.length-1; i++) {
	            urls[i] = (URL) objUrls[i];
	        }
	        new DoBackgroundTask().execute(urls);
			
		
			return START_STICKY;
		}
	    */

	    private void doSomethingRepeatedly() {
	        timer.scheduleAtFixedRate( new TimerTask() {
	            public void run() {
	                Log.d("ServiceTest", String.valueOf(++counter));
	            }
	        }, 0, UPDATE_INTERVAL);
	    }
		
	    private int DownloadFile(URL url) {
	        try {
	            //---simulate taking some time to download a file---
	            Thread.sleep(5000);
	        } catch (InterruptedException e) {
	             e.printStackTrace();
	        }
	        //---return an arbitrary number representing 
	        // the size of the file downloaded---
	        return 100;
	    }

	    private class DoBackgroundTask extends AsyncTask<URL, Integer, Long> {
	        protected Long doInBackground(URL... urls) {
	            int count = urls.length;
	            long totalBytesDownloaded = 0;
	            for (int i = 0; i < count; i++) {
	                totalBytesDownloaded += DownloadFile(urls[i]);
	                //---calculate percentage downloaded and
	                // report its progress---
	                this.publishProgress((int) (((i+1) / (float) count) * 100));
	                
	                if(isCancelled())
	                {
	                	return (long)0;
	                }
	            }
	            return totalBytesDownloaded;
	        }

	        protected void onProgressUpdate(Integer... progress) {
	            Log.d("Downloading files",
	                    String.valueOf(progress[0]) + "% downloaded");
	            Toast.makeText(getBaseContext(),
	                String.valueOf(progress[0]) + "% downloaded",
	                Toast.LENGTH_LONG).show();
	        }

	        protected void onPostExecute(Long result) {
	            Toast.makeText(getBaseContext(),
	                    "Downloaded " + result + " bytes",
	                    Toast.LENGTH_LONG).show();
	            
	            stopSelf(); // KILL THE SERVICE
	        }

			@Override
			protected void onCancelled(Long result) {
				// TODO Auto-generated method stub

	            Toast.makeText(getBaseContext(),
	                    "DoBackgroundTask.onCancelled ",
	                    Toast.LENGTH_SHORT).show();
				
			}


	        
	    }    
	        
		@Override
		public void onDestroy() {
			super.onDestroy();
			
	        if (timer != null){
	            timer.cancel();
	        }
	        
	        // Cancel the background task
	        if(myBackgroundTask != null)
	        {
	        	myBackgroundTask.cancel(true);
	        }
			
			Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
		}
	}

