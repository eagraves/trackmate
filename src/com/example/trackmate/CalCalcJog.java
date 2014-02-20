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

public class CalCalcJog extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cal_calc_jog);
		// Show the Up button in the action bar.
//		getActionBar().setDisplayHomeAsUpEnabled(true);

	}
	

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_cal_calc_jog, menu);
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
	
	
	private boolean isEmpty(EditText etText) {
	    if (etText.getText().toString().trim().length() > 0) {
	        return false;
	    } else {
	        return true;
	    }
	}
	
	
	
	//Do all of the calculations here
    public void showR(View view) {
    	int weight;
    	double miles;
    	int time;
    	int miletimeSec = 0;
    	int miletime = 0;
    	double speed = 0;
    	
    	
    	
//    	Intent intent = new Intent(this, Results.class);
		Bundle extras = getIntent().getExtras();
		time = extras.getInt("time"); //in seconds
		miles = extras.getDouble("distance");
		
    	
    	   	
    	EditText weighttxt = (EditText) findViewById(R.id.weighttxt1);
    	if (!isEmpty(weighttxt))
    	{
        	weight = Integer.parseInt(weighttxt.getText().toString());
    	}
    	else
    		weight = 0;
    	
    
    	//CALCULATIONS
    	//formula from: http://www.livestrong.com/article/451258-how-do-i-calculate-the-calories-burned-with-timing-running-distance/
    	double caloriesburneddb;
    	int caloriesburned = 1;
    	

        caloriesburneddb = 0.75 * weight * miles;
        caloriesburned = (int)caloriesburneddb;
    	
    	
    	if(time != 0 && miles != 0)
    	{
    		speed = (double)miles * 60.0 * 60.0 / (double)time;
    	}
    	
    	if(time != 0 && miles != 0)
    	{
    		miletime = (int)(((double)time / 60.0) / ((double)miles));
        	miletimeSec = time % 60;
    	}
    	

    	if(weight == 0)
    	{
    		Toast.makeText(getApplicationContext(), "Please fill in weight.", Toast.LENGTH_LONG).show();
    		return;
    	}

		TextView textView4 = (TextView) findViewById(R.id.TextView04);
		textView4.setText(String.format("Distance ran: %.2f miles", miles));
	    textView4.setVisibility(View.VISIBLE);

	    TextView textView1 = (TextView) findViewById(R.id.textView1);
	    textView1.setVisibility(View.VISIBLE);
    	
	    TextView textView01 = (TextView) findViewById(R.id.TextView01);
	    textView01.setText(String.format("Calories burned: %d", caloriesburned));
	    textView01.setVisibility(View.VISIBLE);
	    
	    
		TextView textView2 = (TextView) findViewById(R.id.TextView02);
		textView2.setText(String.format("Average speed: %.2f mph", speed));
	    textView2.setVisibility(View.VISIBLE);

	    
	    
		TextView textView3 = (TextView) findViewById(R.id.TextView03);
		textView3.setText(String.format("Miletime: %d minutes and %d seconds", miletime, miletimeSec));
	    textView3.setVisibility(View.VISIBLE);
	    
	    Toast.makeText(getApplicationContext(), "Run time " +time +" seconds", Toast.LENGTH_LONG).show();

    }

}
