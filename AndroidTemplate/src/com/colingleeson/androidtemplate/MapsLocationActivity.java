package com.colingleeson.androidtemplate;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.maps.GeoPoint;

/*
 *  
 *  MD5:  86:AB:37:00:01:1A:A9:13:9F:69:AA:C6:C4:0B:FE:3D
 *	SHA1: 19:53:2F:15:68:00:10:EC:95:D2:27:22:F8:76:59:70:BB:8B:E7:E5
 * */

public class MapsLocationActivity extends FragmentActivity implements OnMapClickListener  {

	private GoogleMap mMap;
	private SupportMapFragment mMapFragment;
	private LocationManager locationManager;
	private GeoPoint point;
	private String locationProvider;
	private Marker destinationMarker;
	private double distanceTravelled;
	private Location previousLocation;
	private Location lastKnownLocation;
	
	private Polyline pline;
	private PolylineOptions plineOptions;
	
	private int startZoomLevel = 15;
	private Boolean sentInitialMessage = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps_location);
		
		//remove focus from edittext
		Button btnSatellite = (Button)findViewById(R.id.satelliteButton);
		btnSatellite.setFocusableInTouchMode(true);
		btnSatellite.requestFocus();
		
		mMapFragment = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map));
		mMap = mMapFragment.getMap();
		
		// Setup map - Location
		mMap.setMyLocationEnabled(true);
		mMap.setLocationSource(new CurrentLocationProvider(this));
		
		
		// Setup map - Touch
		mMap.setOnMapClickListener(this);
		
		// Set defaults
		distanceTravelled = 0.0;
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maps_location, menu);
		return true;
	}

	
	// toggle satellite
	public void onClickSatellite (View view) {
		
		if(mMap.getMapType() == GoogleMap.MAP_TYPE_SATELLITE){
			mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		}else{
			mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		}
		
	}
	
	// add destination
	public void onClickTrack (View view)
	{
		// Remove marker if it exists
		if (destinationMarker != null)
		{
			destinationMarker.remove();
		}
		
		 //---geo-coding---
        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        
        Toast.makeText(getBaseContext(),
                geoCoder.toString() , 
                Toast.LENGTH_SHORT).show();
        
        EditText editDestination = (EditText)findViewById(R.id.editTextDestination);
        String destination = editDestination.getText().toString();

        
        
        try {
            List<Address> addresses = geoCoder.getFromLocationName(
            		destination, 1);
            
            if (addresses.size() > 0) 
            {
                // add marker
            	destinationMarker = mMap.addMarker(new MarkerOptions()
						                .position(new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude()))
						                .title(destination)
						                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
	}

	
	//Map click/touch listener
	@Override
	public void onMapClick(LatLng point) 
	{
		// Remove marker if it exists
		if (destinationMarker != null)
		{
			destinationMarker.remove();
		}
		
        // add marker
    	destinationMarker = mMap.addMarker(new MarkerOptions()
				                .position(point)
				                //.title(destination)
				                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
    	
        TextView txtDistanceRemaining = (TextView)findViewById(R.id.txtDistanceRemainingValue);
        Location markerLocation = new Location("marker");
        markerLocation.setLatitude(destinationMarker.getPosition().latitude) ;
        markerLocation.setLongitude(destinationMarker.getPosition().longitude) ;
        
        float[] dist = new float[1];
        if (lastKnownLocation != null)
        {
	    	Location.distanceBetween(	
	    			markerLocation.getLatitude(), 
	    			markerLocation.getLongitude(), 
	    			lastKnownLocation.getLatitude(), 
	    			lastKnownLocation.getLongitude(), 
					dist);
        }
        else
        {
        	dist[0] = 0;
        }
    	txtDistanceRemaining.setText(String.format("%.2f",dist[0]));
    	

    	Toast.makeText(this, "Map touched", Toast.LENGTH_SHORT).show();
		
	}



	// Location listener class
	public class CurrentLocationProvider implements LocationSource, LocationListener
	{
	    private OnLocationChangedListener listener;
	    private LocationManager locationManager;

	    public CurrentLocationProvider(Context context)
	    {
	        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
	    }

	    @Override
	    public void activate(OnLocationChangedListener listener)
	    {
	        this.listener = listener;
	        String locationProviderString = null;
	        
	        LocationProvider gpsProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
	        if(gpsProvider != null)
	        {
	        	locationProviderString = LocationManager.GPS_PROVIDER;
	            locationManager.requestLocationUpdates(gpsProvider.getName(), 0, 10, this);
	        }

	        LocationProvider networkProvider = locationManager.getProvider(LocationManager.NETWORK_PROVIDER);;
	        if(networkProvider != null) 
	        {
	        	locationProviderString = LocationManager.NETWORK_PROVIDER;
	            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 60 * 5, 0, this);
	        }
	        
	        
	        // go to current location
	        if(locationProviderString != null)
	        {
			    Location myLocation  =  locationManager.getLastKnownLocation(locationProviderString); 
			    lastKnownLocation = myLocation;
			    if(myLocation!=null)
			    {
			        double dLatitude = myLocation.getLatitude();
			        double dLongitude = myLocation.getLongitude();
//			        Log.d("CMG DEBUG"," : "+dLatitude);
//			        Log.d("CMG DEBUG"," : "+dLongitude);
			        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(dLatitude, dLongitude), startZoomLevel));
	
			        // start polyline
	            	LatLng ltlg = new LatLng(dLatitude,dLongitude);
	            	
	            	// initialize polylineoptions
	            	plineOptions = new PolylineOptions()
		            	.add(ltlg)
		            	.width(3)
	            		.color(Color.RED);
			    }
	        }
	        
	    }

	    @Override
	    public void deactivate()
	    {
	        locationManager.removeUpdates(this);
	    }

	    @Override
	    public void onLocationChanged(Location location)
	    {
	        if(listener != null)
	        {
	            listener.onLocationChanged(location);
	            
	            
	            // add to poly line for tracking
	            if(location != null)
	            {	
	            	lastKnownLocation = location;
	            	
	            	LatLng ltlg = new LatLng(location.getLatitude(),location.getLongitude());
		            plineOptions.add(ltlg);
		            plineOptions.color(Color.RED);
		            plineOptions.width(3);
		            
		            pline = mMap.addPolyline(plineOptions);
		            
		            // Set the text value fields
		            // Make sure there is a destination point
		            if(destinationMarker != null)
		            {
			            TextView txtDistanceRemaining = (TextView)findViewById(R.id.txtDistanceRemainingValue);
			            Location markerLocation = new Location("marker");
			            markerLocation.setLatitude(destinationMarker.getPosition().latitude) ;
			            markerLocation.setLongitude(destinationMarker.getPosition().longitude) ;
			            
		            	float[] dist = new float[1];
		            	Location.distanceBetween(	
		            			markerLocation.getLatitude(), 
		            			markerLocation.getLongitude(), 
								location.getLatitude(), 
								location.getLongitude(), 
								dist);

		            	txtDistanceRemaining.setText(String.format("%.2f",dist[0]));
		            }
		            


		            // Determine distance travelled
		            TextView txtDistanceTravelled = (TextView)findViewById(R.id.txtDistanceTravelledValue);
		            if(previousLocation != null)
		            {
		            	float[] dist = new float[1];
		            	Location.distanceBetween(	
		            			previousLocation.getLatitude(), 
		            			previousLocation.getLongitude(), 
								location.getLatitude(), 
								location.getLongitude(), 
								dist);
		            	
		            	distanceTravelled += dist[0];
		            	
		            	txtDistanceTravelled.setText(String.format("%.2f",distanceTravelled));
		            	
		            	// sms test
//		            	if(distanceTravelled > 0 && sentInitialMessage)
//		            	{
//		            		SmsManager sms = SmsManager.getDefault();
//		            		String msg = "Android Assignment #3: You've started travelling. Good luck!";
//		            		sms.sendTextMessage("+4163164219", null, msg , null, null);
//		            		sentInitialMessage = true;
//		            	}
		            }
		            previousLocation = location;        
	            }
	            
	        }
	    }

	    @Override
	    public void onProviderDisabled(String provider)
	    {
	        // TODO Auto-generated method stub

	    }

	    @Override
	    public void onProviderEnabled(String provider)
	    {
	        // TODO Auto-generated method stub

	    }

	    @Override
	    public void onStatusChanged(String provider, int status, Bundle extras)
	    {
	        // TODO Auto-generated method stub

	    }
	}
	
}
