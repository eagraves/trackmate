package com.example.trackmate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CalorieCalculator extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calorie_calculator);
		// Show the Up button in the action bar.
//		getActionBar().setDisplayHomeAsUpEnabled(true);
		Toast.makeText(getApplicationContext(), "Recomended data: heartrate, weight, age, time", Toast.LENGTH_LONG).show();
	}
	

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_calorie_calculator, menu);
		

		
		
		return true;
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
	}
	
	
	public void onRadioButtonClicked(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    // Check which radio button was clicked
//	    switch(view.getId()) {
//	        case R.id.male:
//	            if (checked)
	                // Pirates are the best
//	            break;
//	        case R.id.female:
//	            if (checked)
//	                // Ninjas rule
//	            break;
//	    }
	}
	
	
	private boolean isEmpty(EditText etText) {
	    if (etText.getText().toString().trim().length() > 0) {
	        return false;
	    } else {
	        return true;
	    }
	}
	
	
	
	//Do all of the calculations here
    public void showR(View view) {
    	int heartrate;
    	int choice;
    	int weight;
    	int miles;
    	int time;
    	int age;
    	double miletime = 0;
    	double speed = 0;
    	
    	
    	
    	Intent intent = new Intent(this, Results.class);
    	
    	//choice will store whether male or female choice
    	choice = ((RadioGroup)findViewById( R.id.radioGroup1)).getCheckedRadioButtonId();
    	
    	
		EditText heartratetxt = (EditText) findViewById(R.id.heartratetxt);
    	if (!isEmpty(heartratetxt))
    	{
    		heartrate = Integer.parseInt(heartratetxt.getText().toString());
    	}
    	else
    		heartrate = 0;
    		
    	
    	EditText weighttxt = (EditText) findViewById(R.id.weighttxt);
    	if (!isEmpty(weighttxt))
    	{
        	weight = Integer.parseInt(weighttxt.getText().toString());
    	}
    	else
    		weight = 0;
    	
    	
    	EditText milestxt = (EditText) findViewById(R.id.milestxt);
    	if (!isEmpty(milestxt))
    	{
        	miles = Integer.parseInt(milestxt.getText().toString());
    	}
    	else
    		miles = 0;
    	
    	
    	EditText timetxt = (EditText) findViewById(R.id.timetxt);
    	if (!isEmpty(timetxt))
    	{
        	time = Integer.parseInt(timetxt.getText().toString());
    	}
    	else
    		time = 0;
    	
    	EditText agetxt = (EditText) findViewById(R.id.agetxt);
    	if (!isEmpty(agetxt))
    	{
        	age = Integer.parseInt(agetxt.getText().toString());
    	}
    	else
    		age = 0;
    	
    
    	//CALCULATIONS
    	//formula from: http://www.livestrong.com/article/451258-how-do-i-calculate-the-calories-burned-with-timing-running-distance/
    	double caloriesburneddb;
    	int caloriesburned;
    	
    	if (choice == R.id.female)
    	{
        	caloriesburneddb = (-20.4022 + (.4472 * heartrate) + (.278 * weight) + (.074 * age)) / 4.184; //per minute
        	caloriesburneddb = caloriesburneddb * time;
        	caloriesburned = (int)caloriesburneddb;
    	}
    	
    	else //male
    	{
    		caloriesburneddb = (-55.0969 + (.6309 * heartrate) + (.438 * weight) + (.2017 * age)) / 4.184; //per minute
        	caloriesburneddb = caloriesburneddb * time;
        	caloriesburned = (int)caloriesburneddb;
    	}
    	
    	
    	if(time != 0 && miles != 0)
    	{
    		speed = (double)miles * 60.0 / (double)time;
    	}
    	
    	if(time != 0 && miles != 0)
    	{
    		miletime = ((double)time) / ((double)miles);
    	}
    	

    	if((heartrate == 0) || (weight == 0) || (age == 0) || (time == 0))
    	{
    		Toast.makeText(getApplicationContext(), "Please fill in all boxes. Distance is optional.", Toast.LENGTH_LONG).show();
    		return;
    	}
    	

    	Bundle dataBundle = new Bundle();
    	dataBundle.putDouble("speed", speed);
    	dataBundle.putInt("calories", caloriesburned);
    	dataBundle.putDouble("miletime", miletime);
    	
    	intent.putExtras(dataBundle);
    	
        startActivity(intent);
    }

}
