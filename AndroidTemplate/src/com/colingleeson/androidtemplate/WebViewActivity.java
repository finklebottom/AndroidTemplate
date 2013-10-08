package com.colingleeson.androidtemplate;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewActivity extends Activity {
	
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_web_view);
		
		// Add back
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		 
	    loadWebView("http://colingleeson.grandportfolio.com/");
	}
	
	public void loadWebView(String url){
		webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setBuiltInZoomControls(true);	// Add zoom
		webView.loadUrl(url);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_web_view, menu);
		return true;
	}
	
	// for the Navigate UP (back) button
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // This is called when the Home (Up) button is pressed
	            // in the Action Bar.
	            Intent parentActivityIntent = new Intent(this, MainActivity.class);
	            parentActivityIntent.addFlags(
	                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
	                    Intent.FLAG_ACTIVITY_NEW_TASK);
	            startActivity(parentActivityIntent);
	            finish();
	            return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		Toast.makeText(this,"Item clicked = " + item.getTitle() + ": id = " + item.getItemId(), Toast.LENGTH_SHORT).show();
		
		if(item.getTitle().equals("Home")){
				loadWebView("http://colingleeson.grandportfolio.com/");
				return true;	
		}
		else if(item.getTitle().equals("Google") ){
				Toast.makeText(this,"Google clicked", Toast.LENGTH_LONG).show();
				loadWebView("http://google.com/");
				return true;	
		}
		else if(item.getTitle().equals("Bookmark")){
				Toast.makeText(this,"Bookmark clicked", Toast.LENGTH_LONG).show();
				return true;						
		}	
		return false;
		
		// TODO Auto-generated method stub
		//return super.onMenuItemSelected(featureId, item);
	}

	private class MyWebViewClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	    	view.loadUrl(url);
	        return true;
	    }
	}

}




