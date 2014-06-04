package com.alert;

import com.alert.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UserInput extends Activity 
{
	public static String LOG_TAG = "Alert";
	
	public final static String callPhoneNumber1Message = "MESSAGE1";
	public final static String callPhoneNumber2Message = "MESSAGE2";
	public final static String smsPhoneNumberMessage = "MESSAGE3";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_input);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.user_input, menu);
		return true;
	}
	
	public void onSubmit(View view)
	{
		Toast toast = Toast.makeText(getApplicationContext(), "Phone numbers Submitted", Toast.LENGTH_SHORT);
		toast.show();
		
		Log.d(LOG_TAG, "Submit button clicked");
		EditText callPhoneNumber1 = (EditText)findViewById(R.id.editText_number1);
		EditText callPhoneNumber2 = (EditText)findViewById(R.id.editText_number2);
		EditText smsPhoneNumber = (EditText)findViewById(R.id.editText_number);
		
		Log.i(LOG_TAG, "Call Phone number 1 : "+callPhoneNumber1.getText().toString());
		Log.i(LOG_TAG, "Call Phone number 2 : "+callPhoneNumber2.getText().toString());
		Log.i(LOG_TAG, "SMS Phone number : "+smsPhoneNumber.getText().toString());
		
		Intent intent = new Intent(this, AlertActivity.class);
		intent.putExtra(callPhoneNumber1Message, callPhoneNumber1.getText().toString());
		intent.putExtra(callPhoneNumber2Message, callPhoneNumber2.getText().toString());
		intent.putExtra(smsPhoneNumberMessage, smsPhoneNumber.getText().toString());
		startActivity(intent);
	}

	public void onReset(View view)
	{
		EditText callPhoneNumber1 = (EditText)findViewById(R.id.editText_number1);
		callPhoneNumber1.setText("");
		EditText callPhoneNumber2 = (EditText)findViewById(R.id.editText_number2);
		callPhoneNumber2.setText("");
		
		EditText smsPhoneNumber = (EditText)findViewById(R.id.editText_number);
		smsPhoneNumber.setText("");
	}
}
