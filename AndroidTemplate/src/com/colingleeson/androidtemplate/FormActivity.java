package com.colingleeson.androidtemplate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class FormActivity extends Activity {
	
	public static final String INTERNALSTORAGE_FILE = "MyFile.txt";
	
	public static final String PREFERENCES_FILE = "com.colingleeson.androidtemplate_preferences.xml";
	public static final String NAME_KEY = "name";
	public static final String EMAIL_KEY = "email";
	
	private SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form);
		
		// Add back
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		
		// Shared Preferences
		loadFromSharedPreferences();
		
		// Internal Storage
		loadFromInternalStorage();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_form, menu);
		return true;
	}
	
	public void onClickSave(View v){
		
		// Persistent Save pending...
		EditText editName = (EditText)findViewById(R.id.editTextName);
		EditText editEmail = (EditText)findViewById(R.id.editTextEmail);
		EditText editPhone = (EditText)findViewById(R.id.editTextPhone);
		
		String name =  editName.getText().toString();
		String email =  editEmail.getText().toString();
		String phone =  editPhone.getText().toString();
		//Toast.makeText(this,"Saved: " + name + " | " + email + " | " + phone, Toast.LENGTH_LONG).show(); 
		
		// Go back to to list
		//  Pass parameter
		Intent result = new Intent();
		result.setData(Uri.parse(name + " | " + email + " | " + phone));
		setResult(RESULT_OK, result);
		
		finish();
		
		// Save using Shared Preferences
		saveWithSharePreferences(v);
		
		// Save using Internal Storage
		saveWithInternalStorage();
	}

	// Shared Preferences LOAD
	public void loadFromSharedPreferences(){
		//Load data from shared preferences
		sharedPreferences = getSharedPreferences(PREFERENCES_FILE,MODE_PRIVATE);
		String name = sharedPreferences.getString(NAME_KEY,"");
		String email = sharedPreferences.getString(EMAIL_KEY,"aaa@bbb.com");
		
		Toast.makeText(this,"Retreived from Shared Preferences: " + name + " | " + email, Toast.LENGTH_LONG).show();
		
	}
	
	// Shared Preferences SAVE
	public void saveWithSharePreferences(View v){
		//Save to preferences		
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(NAME_KEY, ((EditText)findViewById(R.id.editTextName)).getText().toString());
		editor.putString(EMAIL_KEY, ((EditText)findViewById(R.id.editTextEmail)).getText().toString());
		editor.commit();
	}
	
	public void loadFromInternalStorage(){
		
		try {
			FileInputStream fileInput = openFileInput(INTERNALSTORAGE_FILE);
			Scanner scanner = new Scanner(fileInput);

			String found = "";
			int i = 1;
			
			while(i < 4){
				
				i++;
				
				if(scanner.hasNextLine()){			
					// simple example.. only 3 lines
					found += scanner.nextLine();
					found += " | ";						
				}
			}
			
			
			Toast.makeText(this,"Retreived from Internal Storage: " + found, Toast.LENGTH_LONG).show();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// Internal Storage SAVE
	public void saveWithInternalStorage(){
		
		try{
			FileOutputStream fileOut = openFileOutput(INTERNALSTORAGE_FILE, MODE_PRIVATE);
			PrintWriter printWriter = new PrintWriter(fileOut);
			
			//save the strings on their own line .. simple case
			printWriter.println(((EditText)findViewById(R.id.editTextName)).getText().toString());
			printWriter.println(((EditText)findViewById(R.id.editTextEmail)).getText().toString());
			printWriter.println(((EditText)findViewById(R.id.editTextPhone)).getText().toString());
			
			printWriter.close();
			fileOut.close(); // close the stream
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
}
