package com.colingleeson.androidtemplate;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;


public class BasicViewsActivity extends Activity {
    String[] presidents = {
            "Dwight D. Eisenhower",
            "John F. Kennedy",
            "Lyndon B. Johnson",
            "Richard Nixon",
            "Gerald Ford",
            "Jimmy Carter",
            "Ronald Reagan",
            "George H. W. Bush",
            "Bill Clinton",
            "George W. Bush",
            "Barack Obama"
        };
	
	public void btnSaved_clicked (View view) {
		DisplayToast("You have clicked the Save button1");
	}
		
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basicview_main);
		
		//Autocomplete start
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, presidents);

            AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.txtPresidents);

            textView.setThreshold(3);
            textView.setAdapter(adapter);
        // Autocomplete end
            
        //Spinner start
    		Spinner s1 = (Spinner) findViewById(R.id.spinner1);

    		/*
    		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    				android.R.layout.simple_spinner_item, presidents);
            */
            ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_single_choice, presidents);
            
    		s1.setAdapter(adapterSpinner);
    		s1.setOnItemSelectedListener(new OnItemSelectedListener()
    		{
    			@Override
    			public void onItemSelected(AdapterView<?> arg0, 
    			View arg1, int arg2, long arg3)
    			{
    				int index = arg0.getSelectedItemPosition();
    				Toast.makeText(getBaseContext(),
    						"You have selected item : " + presidents[index],
    						Toast.LENGTH_SHORT).show();
    			}

    			@Override
    			public void onNothingSelected(AdapterView<?> arg0) { }
    		});
    	//Spinner end
        
    		
    	// DialogFragment
        Fragment2 dialogFragment = Fragment2.newInstance(
                "Are you sure you want to do this?");
            dialogFragment.show(getFragmentManager(), "dialog");  
            

		
		//---Button view---
		Button btnOpen = (Button) findViewById(R.id.btnOpen);
		btnOpen.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				DisplayToast("You have clicked the Open button");
			}
		});
        
		
		//---Button view---
		Button btnSave = (Button) findViewById(R.id.btnSave);
		btnSave.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) {
				DisplayToast("You have clicked the Save button");
			}
		});
        

		//---CheckBox---
		CheckBox checkBox = (CheckBox) findViewById(R.id.chkAutosave);
		checkBox.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) {
				if (((CheckBox)v).isChecked()) 
					DisplayToast("CheckBox is checked");
				else
					DisplayToast("CheckBox is unchecked");
			}
		});

		//---RadioButton---
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rdbGp1);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton rb1 = (RadioButton) findViewById(R.id.rdb1);
				if (rb1.isChecked()) {
					DisplayToast("Option 1 checked!");
				} else {
					DisplayToast("Option 2 checked!");
				}
			}
		});

		//---ToggleButton---
		ToggleButton toggleButton =
				(ToggleButton) findViewById(R.id.toggle1);
		toggleButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v) {
				if (((ToggleButton)v).isChecked())
					DisplayToast("Toggle button is On");
				else
					DisplayToast("Toggle button is Off");
			}
		});
	}

	private void DisplayToast(String msg)
	{
		Toast.makeText(getBaseContext(), msg,
				Toast.LENGTH_SHORT).show();
	}
	
    public void doPositiveClick() {
        //---perform steps when user clicks on OK---
        Log.d("DialogFragmentExample", "User clicks on OK");
    }

    public void doNegativeClick() {
        //---perform steps when user clicks on Cancel---
        Log.d("DialogFragmentExample", "User clicks on Cancel");
    }


}