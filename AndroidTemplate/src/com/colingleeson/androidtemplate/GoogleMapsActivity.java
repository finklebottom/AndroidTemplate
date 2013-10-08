package com.colingleeson.androidtemplate;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class GoogleMapsActivity extends MapActivity {

	/*
	 * MD5: 		4E:CD:4D:BE:FA:23:0B:E7:C1:4C:5D:0C:41:E9:90:F9
	 * API KEY: 	0IB8f4nNMCk-HWJsF7PNCi_m6iNnT9YQYl3wWFQ
	 * */
	
	private MapView mapView;
	private MapController mapController;
	private GeoPoint point;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);
		
		
		// Default start location
		String[] coordinates = {"1.34256607","103.78921587"};
		double lat = Double.parseDouble(coordinates[0]);
		double lon = Double.parseDouble(coordinates[1]);	
		point = new GeoPoint((int)(lat * 1E6), (int)(lon * 1E6));
		
						
		mapView = (MapView)this.findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(true);
		mapView.setStreetView(true);
		//mapView.setTraffic(true);
		
		
		mapController = mapView.getController();
		mapController.animateTo(point);
		mapController.setZoom(13);
		

        //---geo-coding---
        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        
        Toast.makeText(getBaseContext(),
                geoCoder.toString() , 
                Toast.LENGTH_SHORT).show();
        
        try {
            List<Address> addresses = geoCoder.getFromLocationName(
                "empire state building", 1);
            
            if (addresses.size() > 0) {
                point = new GeoPoint(
                        (int) (addresses.get(0).getLatitude() * 1E6),
                        (int) (addresses.get(0).getLongitude() * 1E6));
                mapController.animateTo(point);
                mapController.setZoom(20);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        //---Add a location marker---
        MapOverlay mapOverlay = new MapOverlay(point);
        List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.clear();
        listOfOverlays.add(mapOverlay);
        
		
		mapView.invalidate(); // force a repaint of view
		
	}
	
	// toggle satellite
	public void onClickSatellite (View view) {
		
		if(mapView.isSatellite()){
			mapView.setSatellite(false);
		}else{
			mapView.setSatellite(true);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_maps, menu);
		return true;
	}
	
	
    
    @Override
    protected boolean isRouteDisplayed() {
        // TODO Auto-generated method stub
        return false;
    }
	
	
    
    
    private class MapOverlay extends com.google.android.maps.Overlay
    {
    	private GeoPoint p;
    	
    	public MapOverlay(GeoPoint p){
    		this.p = p;
    	}
    	
        @Override
        public boolean draw(Canvas canvas, MapView mapView,
        boolean shadow, long when)
        {
            super.draw(canvas, mapView, shadow);

            //---translate the GeoPoint to screen pixels---
            Point screenPts = new Point();
            mapView.getProjection().toPixels(p, screenPts); 

            //---add the marker---
            Bitmap bmp = BitmapFactory.decodeResource(
                getResources(), R.drawable.pushpin);
            canvas.drawBitmap(bmp, screenPts.x, screenPts.y - bmp.getHeight(), null);
            return true;
        }
        
        @Override
        public boolean onTouchEvent(MotionEvent event, MapView mapView)
        {
        	//---when user lifts his finger---
        	if (event.getAction() == MotionEvent.ACTION_UP) {
        		GeoPoint p = mapView.getProjection().fromPixels(
        				(int) event.getX(),
        				(int) event.getY());

        		/*
                    Toast.makeText(getBaseContext(),
                        "Location: "+
                        p.getLatitudeE6() / 1E6 + "," +
                        p.getLongitudeE6() /1E6 , 
                        Toast.LENGTH_SHORT).show();
        		 */

        		Geocoder geoCoder = new Geocoder(
        				getBaseContext(), Locale.getDefault());
        		try {
        			List<Address> addresses = geoCoder.getFromLocation(
        					p.getLatitudeE6()  / 1E6,
        					p.getLongitudeE6() / 1E6, 1);

        			String add = "";
        			if (addresses.size() > 0) 
        			{
        				for (int i=0; i<addresses.get(0).getMaxAddressLineIndex();
        						i++)
        					add += addresses.get(0).getAddressLine(i) + "\n";
        			}
        			Toast.makeText(getBaseContext(), add, Toast.LENGTH_SHORT).show();
        		}
        		catch (IOException e) {
        			e.printStackTrace();
        		}   
        		return true;

        	}
        	return false;
        }
    }  

}
