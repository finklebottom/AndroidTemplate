package com.colingleeson.androidtemplate;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	
	String[] templates;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
		ListView listView = getListView();
		
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setTextFilterEnabled(true);
		
		// get the templates array from strings.xml
		templates = getResources().getStringArray(R.array.templates_array);
		
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, templates));
		
		setContentView(listView); // create the list programmatically
	}

	
	public void onListItemClick(ListView parent, View v, int position, long id) {

		
		// toast debug
		Toast.makeText(this,"Selected: " + templates[position], Toast.LENGTH_SHORT).show();
					
					
		switch (position) {
		case 0:
			startActivityForResult(new Intent("com.colingleeson.androidtemplate.FormActivity"), 1);			
			break;
		case 1:
			startActivityForResult(new Intent("com.colingleeson.androidtemplate.NotifyActivity"), 2);			
			break;	
		case 2:
			startActivityForResult(new Intent("com.colingleeson.androidtemplate.WebViewActivity"), 3);			
			break;			
		case 3:
			startActivityForResult(new Intent("com.colingleeson.androidtemplate.FragmentTestActivity"), 4);			
			break;		
		case 4:
			startActivityForResult(new Intent("com.colingleeson.androidtemplate.DialogActivity"), 5);			
			break;	
		case 5:
			startActivityForResult(new Intent("com.colingleeson.androidtemplate.OrientationActivity"), 5);			
			break;	
		case 6:
			startActivityForResult(new Intent("com.colingleeson.androidtemplate.BasicViewsActivity"), 5);			
			break;				
		case 7:
			startActivityForResult(new Intent("com.colingleeson.androidtemplate.GalleryActivity"), 5);			
			break;
		case 8:
			startActivityForResult(new Intent("com.colingleeson.androidtemplate.ImageSwitcherActivity"), 5);			
			break;		
		case 9:
			startActivityForResult(new Intent("com.colingleeson.androidtemplate.GridActivity"), 5);			
			break;		
		case 10:
			startActivityForResult(new Intent("com.colingleeson.androidtemplate.MenusActivity"), 5);			
			break;	
		case 11:
			startActivityForResult(new Intent("com.colingleeson.androidtemplate.MapsActivity"), 5);			
			break;			
		case 12:
			startActivityForResult(new Intent("com.colingleeson.androidtemplate.SlideShowActivity"), 5);			
			break;	
		case 13:
			startActivityForResult(new Intent("com.colingleeson.androidtemplate.MapsLocationActivity"), 5);			
			break;	
		case 14:
			startActivityForResult(new Intent("com.colingleeson.androidtemplate.ServiceActivity"), 5);			
			break;				
		case 15:
			startActivityForResult(new Intent("com.colingleeson.androidtemplate.ThreadingActivity"), 5);			
			break;				
		case 16:
			startActivityForResult(new Intent("com.colingleeson.androidtemplate.ProviderActivity"), 5);			
			break;		
		case 17:
			startActivityForResult(new Intent("com.colingleeson.androidtemplate.ContentProviderActivity"), 5);			
			break;
		case 18:
			startActivityForResult(new Intent("com.colingleeson.androidtemplate.SMSActivity"), 5);			
			break;
		case 19:
			startActivityForResult(new Intent("com.colingleeson.androidtemplate.EmailsActivity"), 5);			
			break;
		case 20:
			startActivityForResult(new Intent("com.colingleeson.androidtemplate.NetworkingActivity"), 5);			
			break;	
			
			
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == 1)
		{
			if(resultCode == RESULT_OK)
			{
				String resultString = data.getData().toString();
				Toast.makeText(this, "Received: " + resultString, Toast.LENGTH_LONG).show();
			}
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
