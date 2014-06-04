package com.alert;

import com.alert.R;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

public class AlertActivity extends Activity 
{
	public static String LOG_TAG = UserInput.LOG_TAG;
	
	private String callPhoneNumber1;
	private String callPhoneNumber2;
	private String smsPhoneNumber;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		Log.d(LOG_TAG, "EmergencyActivity called");
		
		Intent intent = getIntent();
		callPhoneNumber1 = intent.getStringExtra(UserInput.callPhoneNumber1Message);
		callPhoneNumber2 = intent.getStringExtra(UserInput.callPhoneNumber2Message);
		smsPhoneNumber = intent.getStringExtra(UserInput.smsPhoneNumberMessage);
				
		setContentView(R.layout.sensor_invoker);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		Button stopSense = (Button)findViewById(R.id.stop);
		stopSense.setVisibility(View.INVISIBLE);
		Button startSense = (Button)findViewById(R.id.start);
		startSense.setVisibility(View.VISIBLE);
		
		return true;
	}
	
	public void startService(View view)
	{
		Toast toast = Toast.makeText(getApplicationContext(), "Shake sensor activated", Toast.LENGTH_SHORT);
		toast.show();
		
		Button startSense = (Button)findViewById(R.id.start);
		startSense.setVisibility(View.INVISIBLE);
		Button stopSense = (Button)findViewById(R.id.stop);
		stopSense.setVisibility(View.VISIBLE);
		
		Log.d(LOG_TAG, "Start service button clicked. Starting service from Main activity");
		startService(new Intent(this, AlertService.class).putExtra(UserInput.callPhoneNumber1Message,callPhoneNumber1).putExtra(UserInput.callPhoneNumber2Message, callPhoneNumber2).putExtra(UserInput.smsPhoneNumberMessage, smsPhoneNumber));
	}
	
	public void stopService(View view)
	{
		Toast toast = Toast.makeText(getApplicationContext(), "Shake sensor deactivated", Toast.LENGTH_SHORT);
		toast.show();
		
		Log.d(LOG_TAG, "Stop service button clicked. Stopping service from Main activity");
		stopService(new Intent(this, AlertService.class));
		
		Button startSense = (Button)findViewById(R.id.start);
		startSense.setVisibility(View.VISIBLE);	
		Button stopSense = (Button)findViewById(R.id.stop);
		stopSense.setVisibility(View.INVISIBLE);
			
	}
}
