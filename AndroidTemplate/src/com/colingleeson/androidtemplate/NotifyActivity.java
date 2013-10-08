package com.colingleeson.androidtemplate;

import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class NotifyActivity extends Activity {
	
	private int notificationID = 1;
	private TimePicker timePickerNotify;
	
	private int hour;
	private int minute;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notify);
		
		// Add back
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		
		setCurrentTimeOnView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_notify, menu);
		return true;
	}
	
	// display current time
	public void setCurrentTimeOnView() {
		
		timePickerNotify = (TimePicker)findViewById(R.id.timePickerNotify);
		
		final Calendar c = Calendar.getInstance();
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);
		
		// set current time into time picker
		timePickerNotify.setCurrentHour(hour);
		timePickerNotify.setCurrentMinute(minute);
		
	}
	
	
	//time picker listener
	private TimePickerDialog.OnTimeSetListener timePickerListener = 
			new TimePickerDialog.OnTimeSetListener() {
				@Override
				public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
					hour = selectedHour;
					minute = selectedMinute;
				}
			};
	
	
	public void onClickCreate(View v){
		
		// Persistent Save pending...
		EditText editTitle = (EditText)findViewById(R.id.editTextTitle);
		EditText editText = (EditText)findViewById(R.id.editTextText);
		
		String notifyTitle =  editTitle.getText().toString();
		String notifyText =  editText.getText().toString();
		
		Toast.makeText(this,"Picked: " + notifyTitle + " | " + notifyText + " | " + hour + " | " + minute, Toast.LENGTH_LONG).show(); 
		
		
		// Create notification
		Intent myIntent = new Intent("com.colingleeson.androidtemplate.NotifyActivity");
		myIntent.putExtra("notificationID",notificationID);
		
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, myIntent, 0);
		
		NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		
		Notification notif = new Notification.Builder(this)
			.setContentTitle(notifyTitle)
			.setContentText(notifyText)
			.setSmallIcon(R.drawable.ic_launcher)
			.setContentIntent(pendingIntent)
			.build();
		
		//Need AlarmManager to set notification at specific time... investigate this
		nm.notify(notificationID,notif);
		
		
		// Go back to to list
		//  Pass parameter
		Intent result = new Intent();
		result.setData(Uri.parse(notifyTitle + " | " + notifyText + " | "));
		setResult(RESULT_OK, result);
		
		finish();
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
