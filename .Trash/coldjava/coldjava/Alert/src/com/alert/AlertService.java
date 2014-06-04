package com.alert;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;


public class AlertService extends Service implements SensorEventListener, LocationListener
{
	private static volatile boolean senseShake = true;
	private static boolean incomingCall = false;
	private SensorManager sensorMgr;
	private Sensor mAccelerometer;
	private TelephonyManager telephonyManager;
	private CallStatus phoneListener;
    private long lastUpdate = -1;
    private static float x, y, z;
    private static float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;
    private static String num;
    
    protected LocationManager locationManager;
	protected LocationListener locationListener;
	private List<Address> addresses;
	
    
    private static String[] callNumber = new String[2];
	private static String[] smsNumber = new String[1];
 
	private String LOG_TAG = AlertActivity.LOG_TAG;
	
	private static int indx;
	
	@Override
	public void onCreate()
	{
		Log.d(LOG_TAG, "Registering sensors");
		sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMgr.registerListener(this,mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        
        senseShake = true;
        
        Log.d(LOG_TAG, "Registering phone state listener");
    	phoneListener = new CallStatus();
	    telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
	    telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);
	    
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Log.d(LOG_TAG, "onStartCommand called");
		
		callNumber[0] = intent.getStringExtra("MESSAGE1");
		callNumber[1] = intent.getStringExtra("MESSAGE2");
		smsNumber[0] = intent.getStringExtra("MESSAGE3");
			
		return START_REDELIVER_INTENT; 
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) 
	{
			
	}
	
	@Override
	public void onDestroy()
	{
		senseShake = false;
		if(locationManager!=null)
		{
			Log.d(LOG_TAG, "onDestroy from Service is called and Location updates are stopped");
			locationManager.removeUpdates(this);
		}
		
		Log.d(LOG_TAG, "onDestroy from Service is called and Phone Listener is unregistered");
		telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_NONE);
		
		Log.d(LOG_TAG, "onDestroy from Service is called and Sensor is unregistered");
		sensorMgr.unregisterListener(this);
	}

	@Override
	public void onSensorChanged(SensorEvent event) 
	{
    	long curTime = System.currentTimeMillis();
        
    	if ((curTime - lastUpdate) > 250) 
    	{
    		long diffTime = (curTime - lastUpdate);	
    		lastUpdate = curTime;
    		
    		x = event.values[0];
    		y = event.values[1];
    		z = event.values[2];    		
    		
    		float speed = Math.abs(x+y+z - last_x - last_y - last_z)/ diffTime * 10000;
    		
    		if (speed > SHAKE_THRESHOLD && senseShake == true) 
    		{  		
    			Log.i(LOG_TAG, "Sensor is now turned off");
    			senseShake = false;
    			
    			Log.i(LOG_TAG, "Speed of Shake : "+Float.toString(speed));
    			
    		    indx = 0;									// indx should be equal to 0 everytime shake occurs
    		    num = callNumber[indx];
    			call(num);
    		}
    		
    		last_x = x;
    		last_y = y;
    		last_z = z;	
    	}	
	}
	
	public void call(String number) 
	{
	    try 
	    {  	
	        Log.i(LOG_TAG, "Calling 1st number : "+number);
	        Intent callIntent = new Intent(Intent.ACTION_CALL);
	        callIntent.setData(Uri.parse("tel:"+number));
	        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        startActivity(callIntent);
	    }
	    catch (ActivityNotFoundException activityException)
	    {
	    	Log.e("", "Call failed", activityException);
	    }
	}
		
	private void sendSMS()
    {        
		Log.i(LOG_TAG, "Sending text message");
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);	
    }    
	
	public class CallStatus extends PhoneStateListener 
	{
		private boolean isPhoneConnected = false;
		
		@Override
		public void onCallStateChanged(int state, String incomingNumber) 
		{
			if (TelephonyManager.CALL_STATE_RINGING == state) 
			{
				Log.d(LOG_TAG, "Incoming call from " + incomingNumber);
				
				if(senseShake == false) // if senseShake is off means an outgoing call from this app is trigger and after an outgoing call is trigger then only check for incoming call
				{
					Log.d(LOG_TAG, "Incoming call received and breaking the loop");
					incomingCall = true;
					
					Log.i(LOG_TAG, "Sensor is now turned on");
					senseShake = true;
				}
			}
	 
			if (TelephonyManager.CALL_STATE_OFFHOOK == state) 
			{
				Log.d(LOG_TAG, "OffHook");
				isPhoneConnected = true;
			}
			
			if (TelephonyManager.CALL_STATE_IDLE== state)
			{
				Log.i(LOG_TAG, "Phone is in Idle state");
				if (isPhoneConnected == true && incomingCall == false) 
 				{
					if(indx == 0)
					{
						try 
						{
							Log.i(LOG_TAG, "Sleeping for 5 secs before calling 2nd number...");
							Thread.sleep(5000);
						} 
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
						num=callNumber[++indx];

						Log.i(LOG_TAG, "Calling 2nd number : "+num);
						Intent i = new Intent(Intent.ACTION_CALL);
				        i.setData(Uri.parse("tel:"+num));
				        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				        startActivity(i);
				        
						isPhoneConnected = false;
					}
					
					else if(indx == 1)
					{					
						indx++;
						isPhoneConnected = false;
						try 
						{
							Log.i(LOG_TAG, "Sleeping for 7 secs before sending text...");
							Thread.sleep(7000);
						} 
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
						
						for(int j=0;j<smsNumber.length;j++)
						{
							Log.i(LOG_TAG, "Sending Message module called for "+smsNumber[j]);
							sendSMS();
						}
						senseShake = true;
						Log.i(LOG_TAG, "Sensor is now turned on");
					}
 				}
				incomingCall = false; 			// resetting incomingCall flag to false
			}
		}
	}
	
	@Override
	public void onLocationChanged(Location location) 
	{		
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
			 String state = addresses.get(0).getAdminArea();
			 
			 String message="I am at location: "+streetNumber+" "+street+" "+city+" "+" "+state+" "+postalCode;
			 SmsManager smsManager = SmsManager.getDefault();
			 smsManager.sendTextMessage(smsNumber[0], null, message, null, null);
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
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
