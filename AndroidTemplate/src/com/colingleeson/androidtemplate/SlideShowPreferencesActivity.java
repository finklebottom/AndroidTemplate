package com.colingleeson.androidtemplate;

import android.app.Activity;
import android.os.Bundle;

public class SlideShowPreferencesActivity extends Activity {
	
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  // TODO Auto-generated method stub
	  super.onCreate(savedInstanceState);
	  
	  getFragmentManager().beginTransaction().replace(android.R.id.content,
	                new PrefsFragment()).commit();
	 }
}

