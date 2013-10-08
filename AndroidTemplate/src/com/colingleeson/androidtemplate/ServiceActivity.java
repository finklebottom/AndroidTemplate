package com.colingleeson.androidtemplate;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class ServiceActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_service, menu);
		return true;
	}
	
	
	 public void startService(View view) {
        startService(new Intent(getBaseContext(), ServiceTest.class));
        startService(new Intent(getBaseContext(), ServiceIntentTest.class));
	
    	/*
    	// Create Intent, add data to intent with putExtra()
        Intent intent = new Intent(getBaseContext(), MyService.class);
        try {
            URL[] urls = new URL[] {
                    new URL("http://www.amazon.com/somefiles.pdf"),
                    new URL("http://www.wrox.com/somefiles.pdf"),
                    new URL("http://www.google.com/somefiles.pdf"),
                    new URL("http://www.learn2develop.net/somefiles.pdf")};
            intent.putExtra("URLs", urls);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        startService(intent);
        */        
        
    }
	    
    public void stopService(View view) {
        stopService(new Intent(getBaseContext(), ServiceTest.class));
    }

}
