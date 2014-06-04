package com.example.geolocation;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.TextView;
 
import android.util.Log;
 
public class MainActivity extends Activity implements LocationListener
{
	protected LocationManager locationManager;
	protected LocationListener locationListener;
	protected Context context;
	TextView txtLat;
	String lat;
	String provider;
	protected String latitude,longitude; 
	protected boolean gps_enabled,network_enabled;
	private List<Address> addresses;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txtLat = (TextView) findViewById(R.id.textview1);
		 
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
	}
	
	@Override
	public void onLocationChanged(Location location) 
	{
		txtLat = (TextView) findViewById(R.id.textview1);
		//txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
		
		 Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
		 try
		 {
             addresses = gcd.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
         } 
		 catch (IOException e) 
         {
             e.printStackTrace();
         }
		 if(addresses!=null)
		 {
			 String streetNumber = addresses.get(0).getSubThoroughfare();
			 String street = addresses.get(0).getThoroughfare();
			 String city = addresses.get(0).getLocality();
			 String postalCode = addresses.get(0).getPostalCode();
			 txtLat.setText("I am at location: "+streetNumber+" "+street+" "+city+" "+postalCode);
		 }
	}
	 
	@Override
	public void onProviderDisabled(String provider) 
	{
		Log.d("Latitude","disable");
	}
	 
	@Override
	public void onProviderEnabled(String provider) 
	{
		Log.d("Latitude","enable");
	}
	 
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) 
	{
		Log.d("Latitude","status");
	}
}