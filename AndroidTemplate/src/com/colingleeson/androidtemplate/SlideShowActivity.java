package com.colingleeson.androidtemplate;

import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

public class SlideShowActivity extends Activity  implements ViewFactory {
	
	private int imagePosition = 0;
	private int delay = 3000;
	private int autoSwitch = 1;
	
	 //---the images to display---
    Integer[] imageIDs = {
            R.drawable.cmg1,
            R.drawable.cmg2,
            R.drawable.cmg3,
            R.drawable.cmg4,
            R.drawable.cmg5,
            R.drawable.cmg6,
            R.drawable.cmg7,
            R.drawable.cmg8,
            R.drawable.cmg9,
            R.drawable.cmg10,
            R.drawable.cmg11,
            R.drawable.cmg12,
    };
    
    Integer[] imageEnabled = {
            1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,
    };

    private ImageSwitcher imageSwitcher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slide_show);
		
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

		
        imageSwitcher = (ImageSwitcher) findViewById(R.id.switcher1);
        imageSwitcher.setFactory(this);
        
        
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out));
        
        
        
        switchImageThread();
        loadPref();
        
        
        imageSwitcher.setImageResource(imageIDs[imagePosition]);
        

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.slide_show, menu);
		return true;
	}
	
	
	
    @Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	
    	Toast.makeText(this, "menu item = " + item.getTitle(),
                Toast.LENGTH_LONG).show();
    	
    	if(item.getTitle().equals("Preferences") )
    	{	
    		 Intent intent = new Intent();
    	        intent.setClass(SlideShowActivity.this, SlideShowPreferencesActivity.class);
    	        startActivityForResult(intent, 0); 
            
            return true;
        }
    	
    	if(item.getTitle().equals("Pause") )
    	{	
    		// use touch / pause
    		if(autoSwitch == 0)
    		{
    			autoSwitch = 1;
    		}
    		else 
    		{
    			autoSwitch = 0;
    		}
            
            return true;
        }
        return false;
	}
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     // TODO Auto-generated method stub
     //super.onActivityResult(requestCode, resultCode, data);
     
     /*
      * To make it simple, always re-load Preference setting.
      */
     
    	loadPref();
    }
       
    private void loadPref(){
     SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
     
     	// do stuff
     	String my_delay = mySharedPreferences.getString("slideshow_speed", "5");
     	
     	//Retrieve the values
     	Set<String> set = mySharedPreferences.getStringSet("key_image_select", null);
     	
     	for (int x = 0; x < imageEnabled.length; x++)
 		{
 			imageEnabled[x] = 0;
 		}
     	
     	
     	// figure out better way ***
     	if(set != null)
     	{
	     	for (String str: set)
	     	{
	     		Log.d("cmg", str);
	
	     		if (str.equals("CMG1"))
	     		{
	     			imageEnabled[0] = 1;
	     		}
	     		if (str.equals("CMG2"))
	     		{
	     			imageEnabled[1] = 1;
	     		}
	     		if (str.equals("CMG3"))
	     		{
	     			imageEnabled[2] = 1;
	     		}
	     		if (str.equals("CMG4"))
	     		{
	     			imageEnabled[3] = 1;
	     		}
	     		if (str.equals("CMG5"))
	     		{
	     			imageEnabled[4] = 1;
	     		}
	     		if (str.equals("CMG6"))
	     		{
	     			imageEnabled[5] = 1;
	     		}
	     		if (str.equals("CMG7"))
	     		{
	     			imageEnabled[6] = 1;
	     		}
	     		if (str.equals("CMG8"))
	     		{
	     			imageEnabled[7] = 1;
	     		}
	     		if (str.equals("CMG9"))
	     		{
	     			imageEnabled[8] = 1;
	     		}
	     		if (str.equals("CMG10"))
	     		{
	     			imageEnabled[9] = 1;
	     		}
	     		if (str.equals("CMG11"))
	     		{
	     			imageEnabled[10] = 1;
	     		}
	     		if (str.equals("CMG12"))
	     		{
	     			imageEnabled[11] = 1;
	     		}
	   
	 		}
     	}
  		
 		int countSelected = 0;
     	for (int x = 0; x < imageEnabled.length; x++)
 		{
 			if( imageEnabled[x] == 1)
 				countSelected++;
 		}
     	
     	if (countSelected == 0)
     	{
     		imageEnabled[0]=1;
     		
     		Toast.makeText(this, "Please select at least one image in Preferences | Selected =  " + countSelected, Toast.LENGTH_LONG).show();
     	}

     	
    	//Toast.makeText(this, "delay = " + my_delay, Toast.LENGTH_LONG).show();
    	
    	// set delay
    	if(my_delay.equals("999999"))
    	{
    		// use touch 
    		autoSwitch = 0;
    	}
    	else
    	{
    		autoSwitch = 1;
    		int my_delay_int = Integer.parseInt(my_delay);
    		delay = my_delay_int;
    	}
    	
 		int found = -1;
     	for (int x = 0; x < imageEnabled.length; x++)
 		{
 			if( imageEnabled[x] == 1 && found == -1){
 				found = x;
 				imagePosition = found;
 			}
 		}
    	

    }

	
    @Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//return super.onTouchEvent(event);
		
    	final int action = event.getAction();
    	
    	if(action == MotionEvent.ACTION_UP)
    	{
			if(autoSwitch == 0)
			{
				
				//touched
				switchImage();
				
				return true;
			}
    	}
		return false;
	}
    
    public void switchImage()
    {
    	int found = 0;
    	
		while(found == 0)
		{
			imagePosition++;
			if(imagePosition > imageIDs.length-1)
			{
				imagePosition = 0;
			}
			
			if( imageEnabled[imagePosition] == 1 )
			{
				found = 1;
			}
		}
		//imagePosition++;
		imageSwitcher.setImageResource(imageIDs[imagePosition]);
    }

	public View makeView()
    {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundColor(0xFF000000);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new
                ImageSwitcher.LayoutParams(
                        LayoutParams.FILL_PARENT,
                        LayoutParams.FILL_PARENT));
        return imageView;
    }
    
    public void switchImageThread()
    {

		imageSwitcher.postDelayed(new Runnable() {
            
            public void run() {
            	
//            	Log.d("CMG_SlideShowActivity","imagePostion = " + imagePosition);
//            	Log.d("CMG_SlideShowActivity","array length = " + imageIDs.length);
            	
            	if (autoSwitch == 1)
            	{
            		switchImage();
            	}
                imageSwitcher.postDelayed(this, delay);
            }
        }, delay);
    }

}
