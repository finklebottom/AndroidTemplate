package com.colingleeson.androidtemplate;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

public class OrientationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orientation);
		
		// Retrieve stored from RetainNonConfig during Orientation change
		String str = (String) getLastNonConfigurationInstance();
		((EditText)findViewById(R.id.editTextName3)).setText(str);
		
		Toast.makeText(this,"RetainNonConfig : " + str , Toast.LENGTH_SHORT).show(); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_orientation, menu);
		return true;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		//---save whatever you need to persist---
		outState.putString("editTextName3", ((EditText)findViewById(R.id.editTextName3)).getText().toString());
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		//---retrieve the information persisted earlier---
		String storedText = savedInstanceState.getString("editTextName3");
		
		((EditText)findViewById(R.id.editTextName3)).setText(storedText);
		
		Toast.makeText(this,"onRestoreInstanceState : " + storedText , Toast.LENGTH_SHORT).show(); 
	}
	
	
	@Override
	public Object onRetainNonConfigurationInstance() {
		//---save whatever you want here; it takes in an
		// Object type---
		return( ((EditText)findViewById(R.id.editTextName4)).getText().toString());
	}

}
