package com.example.trackmate;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class Tracker extends FragmentActivity{
	
//	  private LocationManager locationManager;
	  private Location loc;
	  private String provider = LocationManager.GPS_PROVIDER;
	  int lat;
	  int lng;
	  LatLng prevLoc;
	  LatLng curLoc;
	  Location lastKnownLocation;
	  Location oldKnownLocation;
	  private long startTime = 0;
	  private long stopTime = 0;
	  private int deltaTime = 0;
	  double deltadistance = 0;
	  private GoogleMap mMap;
	  int active = 0;
	  private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 0; // in Meters
	  private static final long MINIMUM_TIME_BETWEEN_UPDATES = 10000;
	  LocationManager locationManager;
  	  LocationListener locationListener = null;


	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
//	        getMenuInflater().inflate(R.menu.activity_tracker, menu);
	    	menu.add("End Jog");
	        return true;
	    }
	    
        @Override
        public boolean onOptionsItemSelected(MenuItem item)
        {
        	locationManager.removeUpdates(locationListener);
        	active = 0;
        	stopTime = System.currentTimeMillis();
        	deltaTime = (int)((stopTime - startTime)/1000);
        	
        	Intent intent = new Intent(this, CalCalcJog.class);
        	Bundle dataBundle = new Bundle();
        	dataBundle.putInt("time", deltaTime);
        	dataBundle.putDouble("distance", deltadistance);
        	intent.putExtras(dataBundle);
        	
            startActivity(intent);
        	return true;
        }

	   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Fragments
        setContentView(R.layout.activity_tracker);
        
    	// Acquire a reference to the system Location Manager
  	  	locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
  	  
  	  	startTime = System.currentTimeMillis();

        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
      	lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()), 14.0f));

        curLoc = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
        

        
        
        
        
  	  // Define a listener that responds to location updates
        if (locationListener == null){
  	  locationListener = new LocationListener() {
  	      public void onLocationChanged(Location location) {
  	        // Called when a new location is found by the network location provider.
  	    	  

  			prevLoc = curLoc;
  	        oldKnownLocation = lastKnownLocation;
  	      	lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
  	        curLoc = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
  	        
  	        Polyline line = mMap.addPolyline(new PolylineOptions()
            .add(curLoc, prevLoc)
            .width(5)
            .color(Color.RED));
  	        
  	      deltadistance += getDistance(oldKnownLocation.getLatitude(), oldKnownLocation.getLongitude(), lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
  	      String sdistance = Double.toString(deltadistance);
//  	      Toast.makeText(getApplicationContext(), "New Location. Distance: " +sdistance, Toast.LENGTH_LONG).show();
  	      
  	  
  	      }

  	      public void onStatusChanged(String provider, int status, Bundle extras) {}

  	      public void onProviderEnabled(String provider) {}

  	      public void onProviderDisabled(String provider) {}
  	    };
        }
        SupportMapFragment fragment = new SupportMapFragment();
        getSupportFragmentManager().beginTransaction().add(android.R.id.content, fragment).commit();
        
        

        
        

 //       mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(121.45356, 46.51119), 12.0f));
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()), 14.0f));
        


        
  	  // Register the listener with the Location Manager to receive location updates
        if (active == 0){
  	  locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINIMUM_TIME_BETWEEN_UPDATES, MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
  	  active = 1;
        }
  	  
  	  
/*		FragmentManager manager = getSupportFragmentManager(); 
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(R.id.map, SupportMapFragment.newInstance());
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.commit();
	}

	  // Request updates at startup
	  @Override
	    protected void onResume() {
		  super.onResume();
		  try {
			    MapsInitializer.initialize(this);
			} catch (GooglePlayServicesNotAvailableException e) {
			    e.printStackTrace();
			}
		  
		  Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.map);
		  SupportMapFragment mapFragment = (SupportMapFragment)fragment;

		  //Fragments
//		  FragmentManager manager = getSupportFragmentManager(); 
//		  FragmentTransaction transaction = manager.beginTransaction();
//		  transaction.add(R.id.map, mapFragment);
//		  transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//		  transaction.commit();

		  mMap = mapFragment.getMap();
	    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_tracker, menu);
		return true;
	}
	
	  // Remove the locationlistener updates when Activity is paused 
	  @Override
	  protected void onPause() {
	    super.onPause();
//	    locationManager.removeUpdates(this);
	  }
	  


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
*/	}
	
	//input 2 coordinates, get back their distance in miles
	public double getDistance(double lat1, double lon1, double lat2, double lon2) {
	    double latA = Math.toRadians(lat1);
	    double lonA = Math.toRadians(lon1);
	    double latB = Math.toRadians(lat2);
	    double lonB = Math.toRadians(lon2);
	    double cosAng = (Math.cos(latA) * Math.cos(latB) * Math.cos(lonB-lonA)) +
	                    (Math.sin(latA) * Math.sin(latB));
	    double ang = Math.acos(cosAng);
	    double dist = ang *6371 * 0.621371;
	    return dist;
	}

}


